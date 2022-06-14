package cloud.hashcodeentertainment.executionengineservice.taks.ports;

import cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest.TaskRunResponse;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskCreate;

public interface TaskService {

    Long createTask(TaskCreate taskCreate);

    TaskRunResponse runTask(Long taskId);

    void getExecutionResult();

    void getTaksOutput();

    void getTaskHistory();
}
