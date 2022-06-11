package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import lombok.Data;

@Data
public class ContainerUnitResponse {
    private String id;
    private String status;
    private Long exitCode;
    private boolean isRunning;
}
