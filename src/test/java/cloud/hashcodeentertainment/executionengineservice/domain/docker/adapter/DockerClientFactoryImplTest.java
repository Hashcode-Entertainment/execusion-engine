package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.exception.DockerClientException;
import cloud.hashcodeentertainment.executionengineservice.utils.MessageUtils;
import com.github.dockerjava.api.DockerClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.UNIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DockerClientFactoryImplTest {

    private final String VALID_ADDRESS = "example.domain.com";
    private final int VALID_PORT = 2375;

    @Autowired
    private DockerClientFactoryImpl service;

    @Test
    void shouldThrowDockerClientExceptionWhenAddressContainsComma() {
        String address = "192.168,5.1";

        assertThatThrownBy(() -> service.getClient(NETWORK, address, VALID_PORT))
                .isInstanceOf(DockerClientException.class)
                .hasMessage(MessageUtils.getMessage("validation.docker.client.address"));
    }

    @Test
    void shouldReturnValidAddressWhenAddressIsCorrect() {
        assertThat(service.getClient(NETWORK, VALID_ADDRESS, VALID_PORT))
                .isInstanceOf(DockerClient.class);
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortIsLessThan0() {
        int port = -1000;

        assertThatThrownBy(() -> service.getClient(NETWORK, VALID_ADDRESS, port))
                .isInstanceOf(DockerClientException.class)
                .hasMessage(MessageUtils.getMessage("validation.docker.client.port"));
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortIsEqual0() {
        int port = 0;

        assertThatThrownBy(() -> service.getClient(NETWORK, VALID_ADDRESS, port))
                .isInstanceOf(DockerClientException.class)
                .hasMessage(MessageUtils.getMessage("validation.docker.client.port"));
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortLargerThan65536() {
        int port = (int) Math.pow(2,16);

        assertThatThrownBy(() -> service.getClient(NETWORK, VALID_ADDRESS, port))
                .isInstanceOf(DockerClientException.class)
                .hasMessage(MessageUtils.getMessage("validation.docker.client.port"));
    }

    @Test
    void shouldReturnDockerClientWhenNoDetailsProvided() {
        assertThat(service.getClient())
                .isInstanceOf(DockerClient.class);
    }

    @Test
    void shouldReturnDockerClientWhenProvidedDockerClientTypeAsNetwork() {
        assertThat(service.getClient(NETWORK))
                .isInstanceOf(DockerClient.class);
    }

    @Test
    @Disabled(value = "this test is platform dependant (unix), and requires local Docker installation")
    void shouldReturnDockerClientWhenProvidedDockerClientTypeAsUnix() {
        assertThat(service.getClient(UNIX))
                .isInstanceOf(DockerClient.class);
    }
}
