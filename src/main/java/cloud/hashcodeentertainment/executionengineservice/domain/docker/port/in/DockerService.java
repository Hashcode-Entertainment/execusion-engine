package cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in;

public interface DockerService {

    void ping();

    String pullImage(String name, String tag);
}
