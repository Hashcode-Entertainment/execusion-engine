package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerContainerResponse {

    private String containerId;
}
