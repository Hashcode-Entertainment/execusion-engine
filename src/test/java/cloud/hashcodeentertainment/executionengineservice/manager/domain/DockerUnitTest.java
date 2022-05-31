package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import org.apache.hc.client5.http.HttpHostConnectException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerClientDictionary.INVALID_CHARACTER;
import static cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerClientDictionary.INVALID_PORT_NUMBER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class DockerUnitTest {

    private final DockerUnit dockerUnit = new DockerUnit();

    @Test
    void shouldGetLocalClient() {
        dockerUnit.getLocalClient().pingCmd().exec();
    }

    @Test
    void shouldNotThrowAnyExceptionWhenClientExists() {
        String address = "192.168.1.245";
        int port = 2375;

        dockerUnit.getRemoteClient(address, port).pingCmd().exec();
    }

    @Test
    void shouldThrowHttpHostConnectExceptionWhenClientNotExists() {
        String fakeAddress = "192.167.1.200";
        int port = 2375;

        assertThatThrownBy(() -> dockerUnit.getRemoteClient(fakeAddress, port).pingCmd().exec())
                .hasCauseInstanceOf(HttpHostConnectException.class)
                .hasMessageMatching("^.*\\b(Connetion|)\\b.*$");
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortIsZero() {
        String address = "192.168.1.245";
        int port = 0;

        assertThatThrownBy(() -> dockerUnit.getRemoteClient(address, port).pingCmd().exec())
                .isInstanceOf(DockerClientException.class)
                .hasMessage(INVALID_PORT_NUMBER);
    }

    @Test
    void shouldThrowDockerClientExceptionWhenPortIs65536() {
        String address = "192.168.1.245";
        int port = 65536;

        assertThatThrownBy(() -> dockerUnit.getRemoteClient(address, port).pingCmd().exec())
                .isInstanceOf(DockerClientException.class)
                .hasMessage(INVALID_PORT_NUMBER);
    }

    @Test
    void shouldThrowDockerClientExceptionWhenAddressContainsInvalidChar() {
        String address = "192.168.1,245";
        int port = 2375;

        assertThatThrownBy(() -> dockerUnit.getRemoteClient(address, port).pingCmd().exec())
                .isInstanceOf(DockerClientException.class)
                .hasMessage(INVALID_CHARACTER);
    }
}
