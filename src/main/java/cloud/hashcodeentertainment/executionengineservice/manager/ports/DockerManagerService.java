package cloud.hashcodeentertainment.executionengineservice.manager.ports;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeRequest;

import java.util.List;

public interface DockerManagerService {

    List<DockerNode> getAllNodesOnlyNamesAndStatuses();

    List<DockerNode> getAllNodesFullInfo();

    void addNode(DockerNodeRequest nodeRequest);

    void removeNode(String name);

    void restorePersistedNodes();
}
