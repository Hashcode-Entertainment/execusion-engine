package cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in;

public interface DockerService {

    void ping();

    String pullImage(String name, String tag);

    void runContainer(String image, String tag);
}
