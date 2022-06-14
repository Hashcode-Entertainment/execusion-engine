package cloud.hashcodeentertainment.executionengineservice.taks.domain;

import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskExceptionDict.UNSUPPORTED_LANGUAGE;
import static cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskExceptionDict.UNSUPPORTED_VERSION;

@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

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

    @Override
    public void runTask() {

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
