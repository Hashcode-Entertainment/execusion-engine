package cloud.hashcodeentertainment.executionengineservice.task.mapper;

import cloud.hashcodeentertainment.executionengineservice.task.response.TaskResultResponse;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskCreate;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskHistory;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskResult;
import cloud.hashcodeentertainment.executionengineservice.task.model.TaskResultLog;
import cloud.hashcodeentertainment.executionengineservice.task.request.TaskCreateRequest;
import cloud.hashcodeentertainment.executionengineservice.task.response.TaskCreateResponse;
import cloud.hashcodeentertainment.executionengineservice.task.response.TaskHistoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskRestMapper {

    TaskCreate toDomainTaskCreate(TaskCreateRequest taskCreateRequest);

    default TaskCreateResponse toRestTaskCreateResponse(Long taskId) {
        return TaskCreateResponse.builder()
                .id(taskId)
                .build();
    }

    default TaskResultResponse toRestTaskResultResponse(TaskResult taskResult) {
        var logs = taskResult.getLogs().stream()
                .map(TaskResultLog::getBody)
                .toList();

        return TaskResultResponse.builder()
                .id(taskResult.getId())
                .timestamp(taskResult.getTimestamp())
                .runStatus(taskResult.getRunStatus())
                .exitCode(taskResult.getExitCode())
                .logs(logs)
                .build();
    }

    default List<TaskHistoryResponse> toRestTaskHistoryResponse(List<TaskHistory> taskHistories) {
        return taskHistories.stream()
                .map(taskHistory -> TaskHistoryResponse.builder()
                        .id(taskHistory.getId())
                        .timestamp(taskHistory.getTimestamp())
                        .runStatus(taskHistory.getRunStatus())
                        .exitCode(taskHistory.getExitCode())
                        .build())
                .toList();
    }
}
