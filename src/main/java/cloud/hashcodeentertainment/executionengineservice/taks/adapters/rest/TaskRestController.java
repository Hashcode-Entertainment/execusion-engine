package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.taks.ports.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskRestMapper mapper;
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskCreateResponse> addTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        var request = mapper.toDomainTaskCreate(taskCreateRequest);
        var taskId = taskService.createTask(request);

        return ResponseEntity.ok(mapper.toRestTaskCreateResponse(taskId));
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
        return null;
    }

    @PutMapping("{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable String taskId) {
        return null;
    }

    @GetMapping("run/{taskId}")
    public ResponseEntity<?> runTask(@PathVariable String taskId) {
        return null;
    }

    @GetMapping("results/{taskId}")
    public ResponseEntity<?> getExecutionResults(@PathVariable String taskId) {
        return null;
    }

    @GetMapping("output/{taskId}")
    public ResponseEntity<?> getTaskOutput(@PathVariable String taskId) {
        return null;
    }

    @GetMapping("history/{taskId}")
    public ResponseEntity<?> getTaksHistory(@PathVariable String taskId) {
        return null;
    }
}
