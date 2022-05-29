package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.NotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class DockerServiceAdapterTest {

    private DockerClient dockerClient;

    @Autowired
    private DockerService service;

    @Autowired
    private DockerClientFactoryImpl dockerClientFactory;

    @Test
    void shouldThrowHttpHostConnectExceptionWhenNoDockerRunningUnderProvidedAddress() {
        dockerClient = dockerClientFactory.getClient(NETWORK, "192.168.5.1", 5000);

        assertThatThrownBy(() -> service.ping())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldNotThrowAnyExceptionWhenDockerInstanceIsRunning() {
        dockerClient =dockerClientFactory.getClient();
        service.ping();
    }

    @Test
    void shouldReturnStatusContainingStringDownloadedOrUpToData() {
        dockerClient = dockerClientFactory.getClient();

        assertThat(service.pullImage("openjdk", "19-jdk"))
                .isInstanceOf(String.class)
                .containsAnyOf("Status: Image is up to date", "Downloaded");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenImageIsNotAvailable() {
        dockerClient = dockerClientFactory.getClient();


        assertThatThrownBy(() -> service.pullImage("openjdk", "19-jd"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldCreateStartAndDeleteContainer() {

        DockerOption dockerOption = DockerOption.builder()
                .name("openjdk")
                .tag("19-jdk")
                .command("/bin/bash")
                .command("-c")
                .command("java --version")
                .build();

        String containerId = service.startContainer(dockerOption);

        service.waitContainer(containerId, 60, output -> System.out.print("\033[0;34m" + new String(output.getData()) + "\033[0m"));

        service.deleteContainer(containerId);
    }
}
