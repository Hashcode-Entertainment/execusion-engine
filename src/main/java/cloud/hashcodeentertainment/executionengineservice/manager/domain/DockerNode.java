package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import com.github.dockerjava.api.DockerClient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerNode {

    private DockerClient client;
    private String name;
    private String address;
    private int port;
    private DockerNodeStatus status;
    private int numberOfRunningTasks;
}
