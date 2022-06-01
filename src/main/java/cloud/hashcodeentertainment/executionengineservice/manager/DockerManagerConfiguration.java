package cloud.hashcodeentertainment.executionengineservice.manager;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerManagerServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DockerManagerConfiguration {

    @Bean
    public DockerManagerService createDockerManagerService() {
        return new DockerManagerServiceImpl();
    }
}
