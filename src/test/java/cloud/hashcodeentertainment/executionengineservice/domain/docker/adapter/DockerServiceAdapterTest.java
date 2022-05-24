package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DockerServiceAdapterTest {

    private DockerClient dockerClient;

    private DockerService service;

    @Test
    @Disabled(value = "it takes long time before throwing this exception")
    void shouldThrowHttpHostConnectExceptionWhenNoDockerRunningUnderProvidedAddress() {
        dockerClient = new DockerClientFactoryImpl().getClient(NETWORK, "192.168.5.1", 5000);
        service = new DockerServiceAdapter();

        assertThatThrownBy(() -> service.ping())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldNotThrowAnyExceptionWhenDockerInstanceIsRunning() {
        dockerClient = new DockerClientFactoryImpl().getClient();
        service = new DockerServiceAdapter();
        service.ping();
    }
}
