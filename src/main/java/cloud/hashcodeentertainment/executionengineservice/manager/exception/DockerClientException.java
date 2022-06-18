package cloud.hashcodeentertainment.executionengineservice.manager.exception;

public class DockerClientException extends RuntimeException {
    public DockerClientException(String message) {
        super(message);
    }
}
