package cloud.hashcodeentertainment.executionengineservice.task.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreateResponse {

    private Long id;
    private final String message = "Task created successfully";
}
