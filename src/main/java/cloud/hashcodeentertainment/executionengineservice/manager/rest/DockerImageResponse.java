package cloud.hashcodeentertainment.executionengineservice.manager.rest;

import lombok.Data;

@Data
public class DockerImageResponse {
    private String id;
    private String name;
    private String tag;
}
