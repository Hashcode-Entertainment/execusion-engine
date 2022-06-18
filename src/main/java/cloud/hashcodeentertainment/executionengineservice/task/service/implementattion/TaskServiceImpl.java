package cloud.hashcodeentertainment.executionengineservice.task.service.implementattion;

import cloud.hashcodeentertainment.executionengineservice.git.service.DirectoryManager;
import cloud.hashcodeentertainment.executionengineservice.git.service.GitClient;
import cloud.hashcodeentertainment.executionengineservice.manager.model.ContainerUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.manager.service.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.task.exceptions.TaskException;
import cloud.hashcodeentertainment.executionengineservice.task.model.*;
import cloud.hashcodeentertainment.executionengineservice.task.repository.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.task.response.TaskRunResponse;
import cloud.hashcodeentertainment.executionengineservice.task.service.TaskService;
import cloud.hashcodeentertainment.executionengineservice.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static cloud.hashcodeentertainment.executionengineservice.task.model.TaskRunStatus.FAILED;
import static cloud.hashcodeentertainment.executionengineservice.task.model.TaskRunStatus.SUCCESS;

@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;
    private final DockerManagerService dockerManagerService;
    private final DirectoryManager directoryManager;
    private final GitClient gitClient;

    @Override
    public Long createTask(TaskCreate taskCreate) {
        var newTask = Task.builder()
                .name(taskCreate.getName())
                .language(verifyAndGetLanguage(taskCreate.getLanguage()))
                .languageVersion(verifyAndGetLanguageVersion(taskCreate.getLanguage(), taskCreate.getLanguageVersion()))
                .repoAddress(taskCreate.getRepoAddress())
                .build();

        Task saveTask = taskRepository.saveTask(newTask);
        return saveTask.getId();
    }

    @SneakyThrows
    @Override
    public TaskRunResponse runTask(Long taskId) {
        var task = taskRepository.getTaskById(taskId);

        if (task.isPresent()) {
            gitClient.cloneRepo(task.get().getRepoAddress(), taskId);
            var version = task.get().getLanguageVersion();

            dockerManagerService.pullImage("maven", "3-eclipse-temurin-" + version + "-alpine", 200);

            var dockerOption = DockerOption.builder()
                    .taskId(taskId)
                    .name("maven")
                    .tag("3-eclipse-temurin-" + version + "-alpine")
                    .entryPoint("/bin/bash")
                    .entryPoint("-c")
                    .entryPoint("mvn test\nmvn clean")
                    .build();

            String containerId = dockerManagerService.startContainer(dockerOption);
            List<String> strings = dockerManagerService.waitContainer(containerId);

            var logs = strings.stream()
                    .filter(s -> s.startsWith("["))
                    .map(TaskResultLog::new)
                    .toList();

//            strings.forEach(System.out::println);

            ContainerUnit inspectContainer = dockerManagerService.inspectContainer(containerId);

            var isSuccess = strings.stream().noneMatch(s -> s.startsWith("[ERROR]"));

            var result = TaskResult.builder()
                    .id(taskId)
                    .timestamp(LocalDateTime.now())
                    .runStatus(isSuccess ? SUCCESS : FAILED)
                    .exitCode(inspectContainer.getExitCode())
                    .logs(logs)
                    .build();

            directoryManager.deleteDir(taskId.toString());
            dockerManagerService.deleteContainer(containerId);
            Long runResult = taskRepository.saveRunResult(result);

            return TaskRunResponse.builder()
                    .id(runResult)
                    .status(result.getRunStatus())
                    .build();
        } else {
            throw new TaskException(MessageUtils.getMessage("task.not.found"));
        }
    }

    @Override
    public TaskResult getExecutionResult(Long taskId, Long resultId) {
        var task = taskRepository.getTaskByIdWithResults(taskId);

        if (task.isPresent()) {
            var validTask = task.get();
            var result = validTask.getRunResults().stream()
                    .filter(resultEntity -> Objects.equals(resultEntity.getId(), resultId))
                    .findFirst().orElseThrow(() -> new TaskException(MessageUtils.getMessage("task.result.not.found")));

            var logs = result.getLogs().stream()
                    .map(logEntity -> new TaskResultLog(logEntity.getBody()))
                    .toList();

            return TaskResult.builder()
                    .id(result.getId())
                    .timestamp(result.getTimestamp())
                    .runStatus(result.getRunStatus())
                    .exitCode(result.getExitCode())
                    .logs(logs)
                    .build();

        } else {
            throw new TaskException(MessageUtils.getMessage("task.not.found"));
        }
    }

    @Override
    public void getTaskOutput() {

    }

    @Override
    public List<TaskHistory> getTaskHistory(Long taskId) {
        Optional<Task> taskByIdWithResults = taskRepository.getTaskByIdWithResults(taskId);

        if (taskByIdWithResults.isPresent()) {
            return taskByIdWithResults.get().getRunResults().stream()
                    .map(resultEntity -> TaskHistory.builder()
                            .id(resultEntity.getId())
                            .timestamp(resultEntity.getTimestamp())
                            .runStatus(resultEntity.getRunStatus())
                            .exitCode(resultEntity.getExitCode())
                            .build()
                    ).distinct().sorted(Comparator.comparing(TaskHistory::getId)).toList();
        } else {
            throw new TaskException(MessageUtils.getMessage("task.not.found"));
        }
    }

    private String verifyAndGetLanguage(String language) {
        if (language.equalsIgnoreCase("java")) {
            return "java";
        } else {
            throw new TaskException(MessageUtils.getMessage("task.unsupported.language"));
        }
    }

    private String verifyAndGetLanguageVersion(String language, String version) {
        verifyAndGetLanguage(language);

        List<String> supportedVersions = List.of("8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19");

        if (supportedVersions.contains(version)) {
            return version;
        } else {
            throw new TaskException(MessageUtils.getMessage("task.unsupported.version"));
        }
    }
}
