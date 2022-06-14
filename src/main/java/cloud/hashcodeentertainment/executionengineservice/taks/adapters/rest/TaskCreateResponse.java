package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreateResponse {

    private Long id;
    private final String message = "Task created successfully";
}
