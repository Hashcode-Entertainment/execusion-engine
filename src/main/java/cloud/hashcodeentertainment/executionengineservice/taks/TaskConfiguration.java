package cloud.hashcodeentertainment.executionengineservice.taks;

import cloud.hashcodeentertainment.executionengineservice.git.domain.DirectoryManagerImpl;
import cloud.hashcodeentertainment.executionengineservice.git.domain.GitClientImpl;
import cloud.hashcodeentertainment.executionengineservice.git.ports.DirectoryManager;
import cloud.hashcodeentertainment.executionengineservice.git.ports.GitClient;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskService;
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
