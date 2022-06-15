package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskException;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskResult;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskExceptionDict.TASK_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskRepositoryMapper mapper;
    private final TaskJpaRepository taskJpaRepository;

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        var taskEntity = taskJpaRepository.findById(taskId);
        return taskEntity.map(mapper::toDomainWithoutResultsInfo);
    }

    @Override
    public Task saveTask(Task task) {
        var taskToSave = mapper.toEntity(task);
        var savedTask = taskJpaRepository.save(taskToSave);

        return mapper.toDomainWithoutResultsInfo(savedTask);
    }

    @Override
    public Long saveRunResult(TaskResult taskResult) {
        var taskToUpdate = taskJpaRepository.findById(taskResult.getId());

        if (taskToUpdate.isPresent()) {
            var task = taskToUpdate.get();

            var logs = taskResult.getLogs().stream()
                    .map(taskResultLog -> new LogEntity(taskResultLog.getBody()))
                    .toList();

            var result = new ResultEntity();
            result.setTimestamp(taskResult.getTimestamp());
            result.setRunStatus(taskResult.getRunStatus());
            result.setExitCode(taskResult.getExitCode());
            result.setLogs(logs);

            task.addResult(result);
            TaskEntity taskEntity = taskJpaRepository.save(task);

            return taskEntity.getRunResults().stream()
                    .map(ResultEntity::getId)
                    .mapToLong(v -> v)
                    .max().orElseThrow();

        } else {
            throw new TaskException(TASK_NOT_FOUND);
        }
    }
}
