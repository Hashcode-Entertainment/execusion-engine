package cloud.hashcodeentertainment.executionengineservice.taks.ports;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskResult;

import java.util.Optional;

public interface TaskRepository {

    Optional<Task> getTaskById(Long taskId);

    Task saveTask(Task task);

    void saveRunResult(TaskResult taskResult);
}
