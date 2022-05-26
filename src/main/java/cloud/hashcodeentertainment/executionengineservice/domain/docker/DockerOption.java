package cloud.hashcodeentertainment.executionengineservice.domain.docker;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class DockerOption {

    private String containerId;
    @Singular
    private List<String> commands;
    private String name;
    private String tag;

    public String getImage() {
        return name + ":" + tag;
    }
}
