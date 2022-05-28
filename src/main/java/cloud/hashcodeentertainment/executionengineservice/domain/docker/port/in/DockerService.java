package cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.Output;

import java.util.function.Consumer;

public interface DockerService {

    void ping();

    String pullImage(String name, String tag);

    String startContainer(DockerOption option);

    void waitContainer(String containerId, int timeoutInSeconds, Consumer<Output> onLog);

    void stopContainer(String containerId);

    void deleteContainer(String containerId);
}
