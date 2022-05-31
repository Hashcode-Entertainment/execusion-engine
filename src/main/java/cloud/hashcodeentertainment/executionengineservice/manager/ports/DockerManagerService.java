package cloud.hashcodeentertainment.executionengineservice.manager.ports;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;

import java.util.List;

public interface DockerManagerService {

    List<DockerNode> getAllNodes();
}
