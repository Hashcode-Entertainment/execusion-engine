package cloud.hashcodeentertainment.executionengineservice.taks.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskResult {

    private Long id;
    private LocalDateTime timestamp;
    private TaskRunStatus runStatus;
    private Long exitCode;
    @Singular
    private List<TaskResultLog> logs;
}
