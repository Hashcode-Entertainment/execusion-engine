package cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in;

import com.github.dockerjava.api.DockerClient;

public interface DockerClientFactory {

    DockerClient getClient();

    DockerClient getClient(DockerClientType type);

    DockerClient getClient(DockerClientType type, String address, int port);
}
