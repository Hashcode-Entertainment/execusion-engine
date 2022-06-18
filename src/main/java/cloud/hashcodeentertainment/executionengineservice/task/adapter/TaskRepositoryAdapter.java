package cloud.hashcodeentertainment.executionengineservice.task.adapter;

import cloud.hashcodeentertainment.executionengineservice.task.entity.LogEntity;
import cloud.hashcodeentertainment.executionengineservice.task.entity.ResultEntity;
import cloud.hashcodeentertainment.executionengineservice.task.entity.TaskEntity;
import cloud.hashcodeentertainment.executionengineservice.task.exceptions.TaskException;
import cloud.hashcodeentertainment.executionengineservice.task.mapper.TaskRepositoryMapper;
import cloud.hashcodeentertainment.executionengineservice.task.model.Task;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskResult;
import cloud.hashcodeentertainment.executionengineservice.task.repository.TaskJpaRepository;
import cloud.hashcodeentertainment.executionengineservice.task.repository.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Optional<Task> getTaskByIdWithResults(Long taskId) {
        var taskEntity = taskJpaRepository.findById(taskId);
        return taskEntity.map(mapper::toDomainWithResultsInfo);
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
            throw new TaskException(MessageUtils.getMessage("task.not.found"));
        }
    }
}
