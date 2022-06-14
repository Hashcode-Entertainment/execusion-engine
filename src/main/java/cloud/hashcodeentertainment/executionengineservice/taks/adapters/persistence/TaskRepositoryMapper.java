package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskRepositoryMapper {

    Task toDomain(TaskEntity taskEntity);

    TaskEntity toEntity(Task task);
}
