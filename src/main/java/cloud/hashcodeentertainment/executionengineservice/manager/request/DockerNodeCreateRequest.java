package cloud.hashcodeentertainment.executionengineservice.manager.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DockerNodeCreateRequest {

    @NotNull
    @Size(min = 5)
    private String name;

    @NotEmpty
    @Size(min = 5)
    private String address;

    @NotNull
    private int port;
}
