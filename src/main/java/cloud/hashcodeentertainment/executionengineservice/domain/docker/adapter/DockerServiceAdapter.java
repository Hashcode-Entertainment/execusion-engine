package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.service.implementation.DockerClientServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.validations.DockerClientAddressValidator;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.validations.DockerClientPortValidator;
import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DockerServiceAdapter implements DockerService {

    @Autowired
    private final DockerClientAddressValidator addressValidator;

    @Autowired
    private final DockerClientPortValidator portValidator;

    private final DockerClient dockerClient = new DockerClientServiceImpl(addressValidator, portValidator).getClient();

    @Override
    public void ping() {
        dockerClient.pingCmd().exec();
    }
}