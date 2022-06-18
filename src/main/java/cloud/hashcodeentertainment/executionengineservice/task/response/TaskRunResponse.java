package cloud.hashcodeentertainment.executionengineservice.task.response;

import cloud.hashcodeentertainment.executionengineservice.task.model.TaskRunStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRunResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private TaskRunStatus status;
}
