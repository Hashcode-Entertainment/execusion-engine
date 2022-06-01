package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class DockerNodeResponse {

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String port;

    private DockerNodeStatus status;

    private int numberOfRunningTasks;
}
