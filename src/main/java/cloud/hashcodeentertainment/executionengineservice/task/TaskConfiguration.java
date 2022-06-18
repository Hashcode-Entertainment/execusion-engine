package cloud.hashcodeentertainment.executionengineservice.task;

import cloud.hashcodeentertainment.executionengineservice.git.service.implementation.DirectoryManagerImpl;
import cloud.hashcodeentertainment.executionengineservice.git.service.implementation.GitClientImpl;
import cloud.hashcodeentertainment.executionengineservice.git.service.DirectoryManager;
import cloud.hashcodeentertainment.executionengineservice.git.service.GitClient;
import cloud.hashcodeentertainment.executionengineservice.manager.service.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.task.service.implementattion.TaskServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.task.repository.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TaskConfiguration {

    private final DirectoryManager directoryManager = new DirectoryManagerImpl();
    private final GitClient gitClient = new GitClientImpl();


    @Bean
    public TaskService createTaskService(TaskRepository taskRepository, DockerManagerService dockerManagerService) {
        return new TaskServiceImpl(taskRepository, dockerManagerService, directoryManager, gitClient);
    }
}
