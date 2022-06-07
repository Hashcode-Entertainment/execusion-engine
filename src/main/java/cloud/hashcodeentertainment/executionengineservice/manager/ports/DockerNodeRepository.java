package cloud.hashcodeentertainment.executionengineservice.manager.ports;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;

import java.util.List;
import java.util.Optional;

public interface DockerNodeRepository {

    DockerNode save(DockerNode dockerNode);

    Optional<DockerNode> getByName(String name);

    Optional<DockerNode> getByAddress(String address);

    void deleteNode(String name);

    List<DockerNode> getAllNodes();
}
