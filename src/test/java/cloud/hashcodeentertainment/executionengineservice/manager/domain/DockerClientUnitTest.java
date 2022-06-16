package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.exception.DockerClientException;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerClientUnit;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerClientDictionary.INVALID_CHARACTER;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerClientDictionary.INVALID_PORT_NUMBER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class DockerClientUnitTest {

    private final DockerClientUnit dockerClientUnit = new DockerClientUnit();

    @Test
    void shouldGetLocalClient() {
        dockerClientUnit.getLocalClient().pingCmd().exec();
    }

    @Test
    void shouldNotThrowAnyExceptionWhenClientExists() {
        String address = "192.168.1.245";
        int port = 2375;

        dockerClientUnit.getRemoteClient(address, port).pingCmd().exec();
    }

    @Test
    void shouldThrowHttpHostConnectExceptionWhenClientNotExists() {
        String fakeAddress = "192.167.1.200";
        int port = 2375;

        assertThatThrownBy(() -> dockerClientUnit.getRemoteClient(fakeAddress, port).pingCmd().exec())
                .hasCauseInstanceOf(HttpHostConnectException.class)
                .hasMessageMatching("^.*\\b(Connetion|)\\b.*$");
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortIsZero() {
        String address = "192.168.1.245";
        int port = 0;

        assertThatThrownBy(() -> dockerClientUnit.getRemoteClient(address, port).pingCmd().exec())
                .isInstanceOf(DockerClientException.class)
                .hasMessage(INVALID_PORT_NUMBER);
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortIs65536() {
        String address = "192.168.1.245";
        int port = 65536;

        assertThatThrownBy(() -> dockerClientUnit.getRemoteClient(address, port).pingCmd().exec())
                .isInstanceOf(DockerClientException.class)
                .hasMessage(INVALID_PORT_NUMBER);
    }

    @Test
    void shouldThrowDockerClientExceptionWhenAddressContainsInvalidChar() {
        String address = "192.168.1,245";
        int port = 2375;

        assertThatThrownBy(() -> dockerClientUnit.getRemoteClient(address, port).pingCmd().exec())
                .isInstanceOf(DockerClientException.class)
                .hasMessage(INVALID_CHARACTER);
    }
}
