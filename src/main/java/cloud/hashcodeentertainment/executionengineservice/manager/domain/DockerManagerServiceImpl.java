package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;

import java.util.ArrayList;
import java.util.List;

public class DockerManagerServiceImpl implements DockerManagerService {

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
    public void addNode(DockerNode dockerNode) {
        dockerNodes.add(dockerNode);
    }
}
