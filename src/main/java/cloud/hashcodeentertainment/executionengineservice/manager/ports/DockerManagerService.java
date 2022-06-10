package cloud.hashcodeentertainment.executionengineservice.manager.ports;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerImage;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeRequest;

import java.util.List;
import java.util.function.Consumer;

public interface DockerManagerService {

    List<DockerNode> getAllNodesOnlyNamesAndStatuses();

    List<DockerNode> getAllNodesFullInfo();

    void addNode(DockerNodeRequest nodeRequest);

    void removeNode(String name);

    void restorePersistedNodes();

    void updateDockerClientsStatuses();

    List<DockerImage> getAllImages();

    String pullImage(String name, String tag, int timeoutInSeconds);
}
