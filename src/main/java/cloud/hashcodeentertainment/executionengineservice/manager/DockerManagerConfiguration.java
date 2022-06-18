package cloud.hashcodeentertainment.executionengineservice.manager;

import cloud.hashcodeentertainment.executionengineservice.manager.service.implementation.DockerManagerServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.manager.service.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.manager.repository.DockerNodeRepository;
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
