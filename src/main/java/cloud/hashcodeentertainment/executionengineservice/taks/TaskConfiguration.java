package cloud.hashcodeentertainment.executionengineservice.taks;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskRepository;
import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TaskConfiguration {

    @Bean
    public TaskService createTaskService(TaskRepository taskRepository) {
        return new TaskServiceImpl(taskRepository);
    }
}
