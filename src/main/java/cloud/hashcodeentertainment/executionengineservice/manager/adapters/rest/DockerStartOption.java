package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DockerStartOption {

    @NotNull
    private List<String> entryPoints;

    @NotNull
    private String name;

    @NotNull
    private String tag;
}
