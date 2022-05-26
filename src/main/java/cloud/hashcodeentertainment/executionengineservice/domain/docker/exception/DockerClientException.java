package cloud.hashcodeentertainment.executionengineservice.domain.docker.exception;

import java.util.Collections;
import java.util.List;

public class DockerClientException extends RuntimeException {

    public List<String> errors;

    public DockerClientException(List<String> errors){
        this.errors = errors;
    }

    public DockerClientException(String errors) {
        this.errors = Collections.singletonList(errors);
    }
}