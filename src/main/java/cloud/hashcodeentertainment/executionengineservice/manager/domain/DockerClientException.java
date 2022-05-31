package cloud.hashcodeentertainment.executionengineservice.manager.domain;

public class DockerClientException extends RuntimeException {
    public DockerClientException(String message) {
        super(message);
    }
}
