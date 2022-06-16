package cloud.hashcodeentertainment.executionengineservice.manager.domain.utils;

import lombok.Data;

@Data
public class DockerNodeRequest {

    private String name;
    private String address;
    private int port;
}
