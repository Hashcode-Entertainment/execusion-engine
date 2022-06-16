package cloud.hashcodeentertainment.executionengineservice.manager.domain.exception;

public class DockerClientException extends RuntimeException {
    public DockerClientException(String message) {
        super(message);
    }
}
