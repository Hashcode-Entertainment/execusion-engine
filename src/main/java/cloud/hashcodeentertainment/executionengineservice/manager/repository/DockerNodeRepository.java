package cloud.hashcodeentertainment.executionengineservice.manager.repository;

import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerNode;

import java.util.List;
import java.util.Optional;

public interface DockerNodeRepository {

    DockerNode save(DockerNode dockerNode);

    Optional<DockerNode> getByName(String name);

    Optional<DockerNode> getByAddress(String address);

    void deleteNode(String name);

    List<DockerNode> getAllNodes();
}
