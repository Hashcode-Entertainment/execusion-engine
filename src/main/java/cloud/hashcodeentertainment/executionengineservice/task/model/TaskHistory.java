package cloud.hashcodeentertainment.executionengineservice.task.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskHistory {
    private Long id;
    private LocalDateTime timestamp;
    private TaskRunStatus runStatus;
    private Long exitCode;
}
