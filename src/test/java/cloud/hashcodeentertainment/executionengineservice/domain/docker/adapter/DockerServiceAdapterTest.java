package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.NotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
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
    @Disabled(value = "This platform dependant")
    void shouldNotThrowAnyExceptionWhenDockerInstanceIsRunning() {
        dockerClient = new DockerClientFactoryImpl().getClient();
        service = new DockerServiceAdapter();
        service.ping();
    }

    @Test
    @Disabled(value = "This platform dependant")
    void shouldReturnStatusContainingStringDownloadedOrUpToData() {
        dockerClient = new DockerClientFactoryImpl().getClient();
        service = new DockerServiceAdapter();

        assertThat(service.pullImage("openjdk", "19-jdk"))
                .isInstanceOf(String.class)
                .containsAnyOf("Status: Image is up to date", "Downloaded");
    }

    @Test
    @Disabled(value = "This platform dependant")
    void shouldThrowNotFoundExceptionWhenImageIsNotAvailable() {
        dockerClient = new DockerClientFactoryImpl().getClient();
        service = new DockerServiceAdapter();

        assertThatThrownBy(() -> service.pullImage("openjdk", "19-jd"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldCreateStartAndDeleteContainer() {
        service = new DockerServiceAdapter();

        DockerOption dockerOption = DockerOption.builder()
                .name("openjdk")
                .tag("19-jdk")
                .command("/bin/bash")
                .command("-c")
                .command("java --version")
                .build();

        String containerId = service.startContainer(dockerOption);
    }
}
