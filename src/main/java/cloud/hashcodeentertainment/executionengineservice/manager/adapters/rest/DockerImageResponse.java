package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import lombok.Data;

@Data
public class DockerImageResponse {
    private String id;
    private String name;
    private String tag;
}
