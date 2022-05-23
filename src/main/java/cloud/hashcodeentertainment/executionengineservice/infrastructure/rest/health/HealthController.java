package cloud.hashcodeentertainment.executionengineservice.infrastructure.rest.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("Execution Engine Service is ALIVE");
    }
}
