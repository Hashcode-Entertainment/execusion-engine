package cloud.hashcodeentertainment.executionengineservice.taks.ports;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;

import java.util.Optional;

public interface TaskRepository {

    Optional<Task> getTaskById(Long taskId);

    Task saveTask(Task task);
}
