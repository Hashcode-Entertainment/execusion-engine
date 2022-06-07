package cloud.hashcodeentertainment.executionengineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExecutionEngine {

    public static void main(String[] args) {
        SpringApplication.run(ExecutionEngine.class, args);
    }
}
