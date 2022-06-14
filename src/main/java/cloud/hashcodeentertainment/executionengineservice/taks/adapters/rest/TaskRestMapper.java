package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskCreate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskRestMapper {

    TaskCreate toDomainTaskCreate(TaskCreateRequest taskCreateRequest);

    default TaskCreateResponse toRestTaskCreateResponse(Long taskId) {
        return TaskCreateResponse.builder()
                .id(taskId)
                .build();
    }
}
