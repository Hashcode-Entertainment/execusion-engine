package cloud.hashcodeentertainment.executionengineservice.domain.docker.exception;

public class DockerClientException extends RuntimeException {
    public DockerClientException(String message) {
        super(message);
    }
}
