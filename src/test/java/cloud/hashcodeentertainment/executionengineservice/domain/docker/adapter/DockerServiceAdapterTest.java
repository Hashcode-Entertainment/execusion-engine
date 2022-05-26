package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.service.implementation.DockerClientServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.validations.DockerClientAddressValidator;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.validations.DockerClientPortValidator;
import com.github.dockerjava.api.DockerClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DockerServiceAdapterTest {

    private DockerClient dockerClient;

    private DockerService service;

    @Autowired
    private DockerClientAddressValidator addressValidator;

    @Autowired
    private DockerClientPortValidator portValidator;

    @Test
    @Disabled(value = "it takes long time before throwing this exception")
    void shouldThrowHttpHostConnectExceptionWhenNoDockerRunningUnderProvidedAddress() {
        dockerClient = new DockerClientServiceImpl(addressValidator, portValidator).getClient(NETWORK, "192.168.5.1", 5000);
        service = new DockerServiceAdapter(addressValidator, portValidator);

        assertThatThrownBy(() -> service.ping())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldNotThrowAnyExceptionWhenDockerInstanceIsRunning() {
        dockerClient = new DockerClientServiceImpl(addressValidator, portValidator).getClient();
        service = new DockerServiceAdapter(addressValidator, portValidator);
        service.ping();
    }
}
