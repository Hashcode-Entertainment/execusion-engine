package cloud.hashcodeentertainment.executionengineservice.manager.service;

import cloud.hashcodeentertainment.executionengineservice.manager.model.ContainerUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerImage;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.request.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerOption;

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
