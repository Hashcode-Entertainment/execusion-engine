package cloud.hashcodeentertainment.executionengineservice.manager;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.implementation.DockerManagerServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DockerManagerConfiguration {

    private final DockerNodeRepository nodeRepository;

    @Bean
    public DockerManagerService createDockerManagerService() {
        return new DockerManagerServiceImpl(nodeRepository);
    }
}
