package cloud.hashcodeentertainment.executionengineservice.manager.response;

import lombok.Data;

@Data
public class DockerImageResponse {
    private String id;
    private String name;
    private String tag;
}
