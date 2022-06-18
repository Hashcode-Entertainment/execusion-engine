package cloud.hashcodeentertainment.executionengineservice.task.mapper;

import cloud.hashcodeentertainment.executionengineservice.task.model.Task;
import cloud.hashcodeentertainment.executionengineservice.task.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskRepositoryMapper {

    default Task toDomainWithoutResultsInfo(TaskEntity taskEntity) {
        return Task.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getName())
                .language(taskEntity.getLanguage())
                .languageVersion(taskEntity.getLanguageVersion())
                .repoAddress(taskEntity.getRepoAddress())
                .build();
    }

    default Task toDomainWithResultsInfo(TaskEntity taskEntity) {
        return Task.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getName())
                .language(taskEntity.getLanguage())
                .languageVersion(taskEntity.getLanguageVersion())
                .repoAddress(taskEntity.getRepoAddress())
                .runResults(taskEntity.getRunResults())
                .build();
    }

    TaskEntity toEntity(Task task);

//    default TaskEntity toEntityFromTaskResult(TaskResult taskResult) {
//
//    }
}
