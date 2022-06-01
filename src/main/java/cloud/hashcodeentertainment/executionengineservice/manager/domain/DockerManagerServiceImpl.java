package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import com.github.dockerjava.api.DockerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.ADDRESS_EXISTS;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.ONLY_ONE_LOCAL_INSTANCE;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus.OFFLINE;

public class DockerManagerServiceImpl implements DockerManagerService {

    private final String LOCAL_NODE_NAME = "local";

    private final List<DockerNode> dockerNodes = new ArrayList<>();

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

        var dockerNode = DockerNode.builder()
                .name(nodeRequest.getName())
                .address(nodeRequest.getAddress())
                .port(nodeRequest.getPort())
                .client(dockerClient)
                .status(OFFLINE)
                .build();

        //TODO save to db any not local node
        dockerNodes.add(dockerNode);
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
}
