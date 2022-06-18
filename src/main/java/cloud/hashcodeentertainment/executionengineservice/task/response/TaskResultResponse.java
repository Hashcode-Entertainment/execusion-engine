package cloud.hashcodeentertainment.executionengineservice.task.response;

import cloud.hashcodeentertainment.executionengineservice.task.model.TaskRunStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class TaskResultResponse {
    private Long id;
    private LocalDateTime timestamp;
    private TaskRunStatus runStatus;
    private Long exitCode;
    private List<String> logs;
}
