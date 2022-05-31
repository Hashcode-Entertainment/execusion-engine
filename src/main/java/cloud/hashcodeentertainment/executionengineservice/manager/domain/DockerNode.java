package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import lombok.Data;

@Data
public class DockerNode {

    private String name;
    private String address;
    private int port;
    private DockerNodeStatus status;
}
