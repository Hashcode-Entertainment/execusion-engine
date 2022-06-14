package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;
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

    TaskEntity toEntity(Task task);
}
