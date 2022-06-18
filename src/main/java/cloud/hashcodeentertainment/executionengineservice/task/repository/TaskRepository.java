package cloud.hashcodeentertainment.executionengineservice.task.repository;

import cloud.hashcodeentertainment.executionengineservice.task.model.Task;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskResult;

import java.util.Optional;

public interface TaskRepository {

    Optional<Task> getTaskById(Long taskId);

    Optional<Task> getTaskByIdWithResults(Long taskId);

    Task saveTask(Task task);

    Long saveRunResult(TaskResult taskResult);
}
