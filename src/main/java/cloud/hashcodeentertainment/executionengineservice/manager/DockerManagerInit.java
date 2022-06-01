package cloud.hashcodeentertainment.executionengineservice.manager;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerClientUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus.OFFLINE;

@Configuration
@RequiredArgsConstructor
public class DockerManagerInit {

    private final DockerManagerService managerService;

    @EventListener(ApplicationReadyEvent.class)
    public void createLocalDockerNode() {

        var localDockerNode = DockerNode.builder()
                .client(new DockerClientUnit().getLocalClient())
                .name("local")
                .status(OFFLINE)
                .build();

        managerService.addNode(localDockerNode);
    }
}
