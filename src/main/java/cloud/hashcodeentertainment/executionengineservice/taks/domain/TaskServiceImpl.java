package cloud.hashcodeentertainment.executionengineservice.taks.domain;

import cloud.hashcodeentertainment.executionengineservice.git.ports.DirectoryManager;
import cloud.hashcodeentertainment.executionengineservice.git.ports.GitClient;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.ContainerUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest.TaskRunResponse;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskExceptionDict.TASK_NOT_FOUND;
import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskExceptionDict.UNSUPPORTED_LANGUAGE;
import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskExceptionDict.UNSUPPORTED_VERSION;
import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskRunStatus.FAILED;
import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskRunStatus.SUCCESS;

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
//            directoryManager.createDir(taskId.toString());
            gitClient.cloneRepo(task.get().getRepoAddress(), taskId);

            dockerManagerService.pullImage("maven", "3-eclipse-temurin-17-alpine", 200);

            var dockerOption = DockerOption.builder()
                    .name("maven")
                    .tag("3-eclipse-temurin-17-alpine")
                    .entryPoint("bin/bash")
                    .entryPoint("-c")
                    .entryPoint("mvn --version")
                    .build();

            String containerId = dockerManagerService.startContainer(dockerOption);
            List<String> strings = dockerManagerService.waitContainer(containerId);

            var logs = strings.stream().map(TaskResultLog::new).toList();
            strings.forEach(System.out::println);

            ContainerUnit inspectContainer = dockerManagerService.inspectContainer(containerId);

            var result = TaskResult.builder()
                    .timestamp(LocalDateTime.now())
                    .runStatus(inspectContainer.getExitCode() == 0 ? SUCCESS : FAILED)
                    .exitCode(inspectContainer.getExitCode())
                    .logs(logs)
                    .build();

            directoryManager.deleteDir(taskId.toString());
            dockerManagerService.deleteContainer(containerId);

            return TaskRunResponse.builder().status(result.getRunStatus()).build();
        } else {
            throw new TaskException(TASK_NOT_FOUND);
        }
    }

    @Override
    public void getExecutionResult() {

    }

    @Override
    public void getTaksOutput() {

    }

    @Override
    public void getTaskHistory() {

    }

    private String verifyAndGetLanguage(String language) {
        if (language.equalsIgnoreCase("java")) {
            return "java";
        } else {
            throw new TaskException(UNSUPPORTED_LANGUAGE);
        }
    }

    private String verifyAndGetLanguageVersion(String language, String version) {
        verifyAndGetLanguage(language);

        List<String> supportedVersions = List.of("8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19");

        if (supportedVersions.contains(version)) {
            return version;
        } else {
            throw new TaskException(UNSUPPORTED_VERSION);
        }
    }
}
