package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.exception.DockerClientException;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.exception.DockerClientDictionary.INVALID_CHARACTER;
import static cloud.hashcodeentertainment.executionengineservice.domain.docker.exception.DockerClientDictionary.INVALID_PORT_NUMBER;
import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.UNIX;

public class DockerServiceAdapter implements DockerService {

    private String hostUrl = "localhost";
    private String hostPort = "2375";

    private String getDockerHost(DockerClientType type) {
        String HOST_PREFIX = "tcp://";
        String DEFAULT_UNIX_DOCKER_HOST = "unix:///var/run/docker.sock";

        return type.equals(UNIX) ? DEFAULT_UNIX_DOCKER_HOST : (HOST_PREFIX + hostUrl + hostPort);
    }

    private DockerClientConfig getDockerClientConfig(DockerClientType type) {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(getDockerHost(type))
                .build();
    }

    private DockerHttpClient getDockerHttpClient(DockerClientType type) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(getDockerClientConfig(type).getDockerHost())
                .build();
    }

    @Override
    public DockerClient getClient() {
        return DockerClientImpl.getInstance(
                getDockerClientConfig(NETWORK),
                getDockerHttpClient(NETWORK)
        );
    }

    @Override
    public DockerClient getClient(DockerClientType type) {
        if (type.equals(NETWORK)) {
            return getClient();
        } else {
            return DockerClientImpl.getInstance(
                    getDockerClientConfig(UNIX),
                    getDockerHttpClient(UNIX));
        }
    }

    @Override
    public DockerClient getClient(DockerClientType type, String address, int port) {
        hostUrl = validateAddress(address);
        hostPort = validatePort(port);

        return getClient(type);
    }

    private String validateAddress(String address) {
        if (address.contains(",")) {
            throw new DockerClientException(INVALID_CHARACTER);
        } else {
            return address;
        }
    }

    private String validatePort(int port) {
        if (port <= 0 || port >= 65536) {
            throw new DockerClientException(INVALID_PORT_NUMBER);
        } else {
            return String.valueOf(port);
        }
    }
}
