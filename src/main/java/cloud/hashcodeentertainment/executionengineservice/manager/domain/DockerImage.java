package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerImage {
    private String id;
    private String name;
    private String tag;
}
