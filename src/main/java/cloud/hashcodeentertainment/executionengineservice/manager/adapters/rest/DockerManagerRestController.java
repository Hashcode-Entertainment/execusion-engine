package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.commons.rest.LocationUri;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cloud.hashcodeentertainment.executionengineservice.commons.rest.LocationUri.getUri;
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
}
