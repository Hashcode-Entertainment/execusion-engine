package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("manager")
@RequiredArgsConstructor
public class DockerManagerRestController {

    private final DockerManagerService managerService;
    private final DockerManagerRestMapper restMapper;

    @GetMapping("nodes/info")
    public ResponseEntity<List<DockerNodeResponse>> getAllNodesInfo() {
        var nodes = managerService.getAllNodesOnlyNamesAndStatuses();
        var nodeResponses = restMapper.toRest(nodes);

        return ResponseEntity.ok(nodeResponses);
    }

    @GetMapping("nodes/info/detailed")
    public ResponseEntity<List<DockerNodeResponse>> getAllNodesDetailedInfo() {
        var nodes = managerService.getAllNodesFullInfo();
        var nodeResponses = restMapper.toRest(nodes);

        return ResponseEntity.ok(nodeResponses);
    }

    @PostMapping("nodes")
    public ResponseEntity<Void> createNode(@Valid @RequestBody DockerNodeCreateRequest nodeCreateRequest) {
        var nodeRequest = restMapper.toDomain(nodeCreateRequest);
        managerService.addNode(nodeRequest);

        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("nodes/{name}")
    public ResponseEntity<Void> removeNode(@PathVariable String name) {
        managerService.removeNode(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("docker/images/show")
    public ResponseEntity<List<DockerImageResponse>> getAllImages() {
        return ResponseEntity.ok(restMapper.toRestDockerImageResponse(managerService.getAllImages()));
    }

    @GetMapping("docker/images")
    public ResponseEntity<String> pullImage(@RequestParam String name, @RequestParam String tag) {
        return ResponseEntity.ok(managerService.pullImage(name, tag, 60));
    }

    @DeleteMapping("docker/images/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        return null;
    }
}
