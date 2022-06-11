package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerNodeRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.api.model.ResponseItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.ADDRESS_EXISTS;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.NODE_NAME_EXISTS;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.NODE_NAME_NOT_FOUND;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.ONLY_ONE_LOCAL_INSTANCE;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus.OFFLINE;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus.ONLINE;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class DockerManagerServiceImpl implements DockerManagerService {

    private final String LOCAL_NODE_NAME = "local";

    private final List<DockerNode> dockerNodes = new ArrayList<>();

    private final DockerNodeRepository nodeRepository;

    @Override
    public List<DockerNode> getAllNodesFullInfo() {
        return dockerNodes;
    }

    @Override
    public List<DockerNode> getAllNodesOnlyNamesAndStatuses() {
        return dockerNodes.stream()
                .peek(dockerNode -> {
                    dockerNode.setAddress(null);
                    dockerNode.setPort(0);
                }).toList();
    }

    @Override
    public void addNode(DockerNodeRequest nodeRequest) {
        var dockerClient = getClient(nodeRequest.getAddress(), nodeRequest.getPort());

        if (existsDockerNodeName(nodeRequest.getName())) {
            throw new DockerManagerException(NODE_NAME_EXISTS);
        }

        if (existsDockerNodeAddress(nodeRequest.getAddress())) {
            throw new DockerManagerException(ADDRESS_EXISTS);
        }

        var dockerNode = DockerNode.builder()
                .name(nodeRequest.getName())
                .address(nodeRequest.getAddress())
                .port(nodeRequest.getPort())
                .client(dockerClient)
                .status(OFFLINE)
                .build();

        dockerNodes.add(dockerNode);

        if (!nodeRequest.getName().equals(LOCAL_NODE_NAME)) {
            nodeRepository.save(dockerNode);
        }
    }

    @Override
    public void removeNode(String name) {
        if (!existsDockerNodeName(name)) {
            throw new DockerManagerException(NODE_NAME_NOT_FOUND);
        }

        var node = dockerNodes.stream()
                .filter(dockerNode -> dockerNode.getName().equals(name))
                .findFirst();

        node.ifPresent(dockerNodes::remove);

        nodeRepository.deleteNode(name);
    }

    @Override
    public void restorePersistedNodes() {
        var persistedNodes = nodeRepository.getAllNodes();

        persistedNodes.stream()
                .filter(node -> !node.getName().equals(LOCAL_NODE_NAME))
                .forEach(dockerNode -> {
                    var dockerClient = getClient(dockerNode.getAddress(), dockerNode.getPort());

                    var restoredNode = DockerNode.builder()
                            .name(dockerNode.getName())
                            .address(dockerNode.getAddress())
                            .port(dockerNode.getPort())
                            .client(dockerClient)
                            .status(OFFLINE)
                            .build();

                    dockerNodes.add(restoredNode);
                });
    }

    @Override
    public List<DockerImage> getAllImages() {
        var dockerClient = getDockerClient();

        List<Image> images = dockerClient.listImagesCmd().exec();
        return images.stream()
                .map(image -> DockerImage.builder()
                        .id(image.getId().substring(7))
                        .name(parseRepoTag(image.getRepoTags(), true))
                        .tag(parseRepoTag(image.getRepoTags(), false))
                        .build()
                )
                .toList();
    }

    private String parseRepoTag(String[] repoTags, boolean getName) {
        String repoTag = Arrays.stream(repoTags).findAny().orElse(":");
        int i = repoTag.indexOf(":");
        if (getName) {
            return repoTag.substring(0, i);
        } else {
            return repoTag.substring(i + 1);
        }
    }

    @Override
    @SneakyThrows
    public String pullImage(String name, String tag, int timeoutInSeconds) {
        var dockerClient = getDockerClient();

        List<PullResponseItem> responseItems = new ArrayList<>();

        dockerClient
                .pullImageCmd(name + ":" + tag)
                .exec(new PullImageResultCallback() {
                    @Override
                    public void onNext(PullResponseItem item) {
                        responseItems.add(item);
                        super.onNext(item);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                }).awaitCompletion(timeoutInSeconds, TimeUnit.SECONDS);

        return responseItems.stream().map(ResponseItem::getStatus)
                .filter(s -> s.startsWith("Status:"))
                .findFirst()
                .orElseThrow()
                .replace("Status:", "");
    }

    @Override
    public void deleteImage(String id) {
        var dockerClient = getDockerClient();

        dockerClient.removeImageCmd(id).exec();
    }


    @Override
    public String startContainer(DockerOption dockerOption) {
        var dockerClient = getDockerClient();

        var containerResponse = dockerClient.createContainerCmd(dockerOption.getImage())
                .withEntrypoint(dockerOption.getEntryPoints())
                .exec();

        dockerClient.startContainerCmd(containerResponse.getId()).exec();

        return containerResponse.getId();
    }

    @Override
    public void stopContainer() {
        var dockerClient = getDockerClient();
    }

    @Override
    public ContainerUnit inspectContainer(String containerId) {
        var dockerClient = getDockerClient();

        var inspectResponse = dockerClient.inspectContainerCmd(containerId).exec();

        return ContainerUnit.builder()
                .id(inspectResponse.getId())
                .status(inspectResponse.getState().getStatus())
                .exitCode(inspectResponse.getState().getExitCodeLong())
                .isRunning(inspectResponse.getState().getRunning())
                .build();
    }

    @SneakyThrows
    @Override
    public List<String> waitContainer(String containerId) {
        List<String> logs = new ArrayList<>();

        var dockerClient = getDockerClient();

        dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .exec(new ResultCallback<Frame>() {
                    @Override
                    public void onStart(Closeable closeable) {

                    }

                    @Override
                    public void onNext(Frame frame) {
                        logs.add(new String(frame.getPayload()).replace("\n", ""));
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void close() throws IOException {

                    }
                });

        while (inspectContainer(containerId).isRunning() && inspectContainer(containerId) != null) {
            TimeUnit.SECONDS.sleep(2);
        }

        TimeUnit.MILLISECONDS.sleep(100);
        return logs;
    }

    @Override
    public void deleteContainer(String containerId) {
        var dockerClient = getDockerClient();

        dockerClient.removeContainerCmd(containerId)
                .withForce(true)
                .exec();
    }






    private DockerClient getDockerClient() {
        //TODO logic selection of docker
        return dockerNodes.get(0).getClient();
    }

    private boolean existsDockerNodeName(String name) {
        return nodeRepository.getByName(name).isPresent();
    }

    private boolean existsDockerNodeAddress(String address) {
        return nodeRepository.getByAddress(address).isPresent();
    }

    private DockerClient getClient(String address, int port) {
        if (address != null && port > 0) {
            if (isAddressUnique(address)) {
                return new DockerClientUnit().getRemoteClient(address, port);
            }
            throw new DockerManagerException(ADDRESS_EXISTS);
        }

        if (findLocalNodeInstance().isPresent()) {
            throw new DockerManagerException(ONLY_ONE_LOCAL_INSTANCE);
        }
        return new DockerClientUnit().getLocalClient();
    }

    private Optional<DockerNode> findLocalNodeInstance() {
        return dockerNodes.stream()
                .filter(dockerNode -> dockerNode.getName().equals(LOCAL_NODE_NAME))
                .findAny();
    }

    private boolean isAddressUnique(String address) {
        return dockerNodes.stream()
                .filter(dockerNode -> !dockerNode.getName().equals(LOCAL_NODE_NAME))
                .filter(dockerNode -> dockerNode.getAddress().equals(address))
                .findAny()
                .isEmpty();
    }

    @Override
    public void updateDockerClientsStatuses() {
        var numberOfNodes = dockerNodes.size();

        for (int i = 0; i < numberOfNodes; i++) {
            try {
                dockerNodes.get(i).getClient().pingCmd().exec();
                dockerNodes.get(i).setStatus(ONLINE);
            } catch (Exception ex) {
                dockerNodes.get(i).setStatus(OFFLINE);
            }
        }
    }
}
