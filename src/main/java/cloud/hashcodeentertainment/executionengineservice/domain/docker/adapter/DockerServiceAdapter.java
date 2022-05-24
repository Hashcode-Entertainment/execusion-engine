package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DockerServiceAdapter implements DockerService {

    private final DockerClient dockerClient = new DockerClientFactoryImpl().getClient();

    @Override
    public void ping() {
        dockerClient.pingCmd().exec();
    }
}
