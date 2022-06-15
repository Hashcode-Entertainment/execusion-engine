package cloud.hashcodeentertainment.executionengineservice.taks.ports;

import cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest.TaskRunResponse;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskCreate;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskResult;

public interface TaskService {

    Long createTask(TaskCreate taskCreate);

    TaskRunResponse runTask(Long taskId);

    TaskResult getExecutionResult(Long taskId, Long resultId);

    void getTaskOutput();

    void getTaskHistory();
}
