package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskRunStatus;
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
