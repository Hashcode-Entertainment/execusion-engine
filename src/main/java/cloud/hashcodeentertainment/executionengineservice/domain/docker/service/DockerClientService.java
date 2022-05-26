package cloud.hashcodeentertainment.executionengineservice.domain.docker.service;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType;
import com.github.dockerjava.api.DockerClient;

public interface DockerClientService {

    DockerClient getClient();

    DockerClient getClient(DockerClientType type);

    DockerClient getClient(DockerClientType type, String address, int port);
}
