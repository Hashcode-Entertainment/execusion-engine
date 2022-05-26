package cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.DockerOption;

public interface DockerService {

    void ping();

    String pullImage(String name, String tag);

    String startContainer(DockerOption option);
}
