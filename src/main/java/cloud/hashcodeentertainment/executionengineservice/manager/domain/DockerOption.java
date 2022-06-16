package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class DockerOption {

    private String containerId;
    private Long taskId;

    @Singular
    private List<String> commands;

    @Singular
    private List<String> entryPoints;

    private String name;
    private String tag;

    public String getImage() {
        return name + ":" + tag;
    }
}
