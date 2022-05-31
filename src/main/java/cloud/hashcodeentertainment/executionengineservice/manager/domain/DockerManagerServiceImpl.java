package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DockerManagerServiceImpl implements DockerManagerService {

    private final List<DockerNode> dockerNodes;



    @Override
    public List<DockerNode> getAllNodes() {
        return null;
    }
}
