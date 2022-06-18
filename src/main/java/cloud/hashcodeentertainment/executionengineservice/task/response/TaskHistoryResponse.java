package cloud.hashcodeentertainment.executionengineservice.task.response;

import cloud.hashcodeentertainment.executionengineservice.task.model.TaskRunStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskHistoryResponse {
    private Long id;
    private LocalDateTime timestamp;
    private TaskRunStatus runStatus;
    private Long exitCode;
}
