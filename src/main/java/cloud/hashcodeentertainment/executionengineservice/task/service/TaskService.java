package cloud.hashcodeentertainment.executionengineservice.task.service;

import cloud.hashcodeentertainment.executionengineservice.task.response.TaskRunResponse;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskCreate;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskHistory;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskResult;

import java.util.List;

public interface TaskService {

    Long createTask(TaskCreate taskCreate);

    TaskRunResponse runTask(Long taskId);

    TaskResult getExecutionResult(Long taskId, Long resultId);

    void getTaskOutput();

    List<TaskHistory> getTaskHistory(Long taskId);
}
