package cloud.hashcodeentertainment.executionengineservice.manager;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class DockerManagerInit {

    private final DockerManagerService managerService;

    @EventListener(ApplicationReadyEvent.class)
    public void createLocalDockerNode() {
        var dockerNodeRequest = new DockerNodeRequest();
        dockerNodeRequest.setName("local");

        managerService.addNode(dockerNodeRequest);
        managerService.restorePersistedNodes();
    }
}
