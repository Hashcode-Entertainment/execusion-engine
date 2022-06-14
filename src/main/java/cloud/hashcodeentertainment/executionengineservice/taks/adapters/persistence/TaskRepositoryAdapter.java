package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest.TaskRestMapper;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskRepository;
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
        return taskEntity.map(mapper::toDomain);
    }

    @Override
    public Task saveTask(Task task) {
        var taskToSave = mapper.toEntity(task);
        var savedTask = taskJpaRepository.save(taskToSave);

        return mapper.toDomain(savedTask);
    }
}
