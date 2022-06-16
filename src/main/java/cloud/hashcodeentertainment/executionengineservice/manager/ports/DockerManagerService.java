package cloud.hashcodeentertainment.executionengineservice.manager.ports;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.ContainerUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerImage;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerOption;

import java.util.List;

public interface DockerManagerService {

    List<DockerNode> getAllNodesOnlyNamesAndStatuses();

    List<DockerNode> getAllNodesFullInfo();

    void addNode(DockerNodeRequest nodeRequest);

    void removeNode(String name);

    void restorePersistedNodes();

    void updateDockerClientsStatuses();

    List<DockerImage> getAllImages();

    String pullImage(String name, String tag, int timeoutInSeconds);

    void deleteImage(String id);

    String startContainer(DockerOption dockerOption);

    void stopContainer(String containerId);

    ContainerUnit inspectContainer(String containerId);

    List<String> waitContainer(String containerId);

    void deleteContainer(String containerId);
}
