package cloud.hashcodeentertainment.executionengineservice.manager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerImage {
    private String id;
    private String name;
    private String tag;
}
