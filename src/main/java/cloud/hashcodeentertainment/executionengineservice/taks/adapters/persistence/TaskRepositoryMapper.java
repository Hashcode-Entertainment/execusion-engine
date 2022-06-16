package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskResult;
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
