package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerNodeRepository;
import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.ADDRESS_EXISTS;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.NODE_NAME_EXISTS;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.NODE_NAME_NOT_FOUND;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerExceptionDict.ONLY_ONE_LOCAL_INSTANCE;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus.OFFLINE;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus.ONLINE;

@RequiredArgsConstructor
@Transactional
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
