package cloud.hashcodeentertainment.executionengineservice.domain.docker.service.implementation;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.service.DockerClientService;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.validations.DockerClientAddressValidator;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.validations.DockerClientPortValidator;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.stereotype.Service;

import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.NETWORK;
import static cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerClientType.UNIX;

@Service
public class DockerClientServiceImpl implements DockerClientService {

    private String hostUrl = "localhost";
    private String hostPort = "2375";
    private final DockerClientAddressValidator addressValidator;
    private final DockerClientPortValidator portValidator;

    public DockerClientServiceImpl(DockerClientAddressValidator addressValidator, DockerClientPortValidator portValidator) {
        this.addressValidator = addressValidator;
        this.portValidator = portValidator;
    }

    private String getDockerHost(DockerClientType type) {
        String HOST_PREFIX = "tcp://";
        String DEFAULT_UNIX_DOCKER_HOST = "unix:///var/run/docker.sock";

        return type.equals(UNIX) ? DEFAULT_UNIX_DOCKER_HOST : (HOST_PREFIX + hostUrl + ":" + hostPort);
    }

    private DockerClientConfig getDockerClientConfig(DockerClientType type) {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(getDockerHost(type))
                .build();
    }

    private DockerHttpClient getDockerHttpClient(DockerClientType type) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(getDockerClientConfig(type).getDockerHost())
                .build();
    }

    @Override
    public DockerClient getClient() {
        return DockerClientImpl.getInstance(
                getDockerClientConfig(NETWORK),
                getDockerHttpClient(NETWORK)
        );
    }

    @Override
    public DockerClient getClient(DockerClientType type) {
        if (type.equals(NETWORK)) {
            return getClient();
        } else {
            return DockerClientImpl.getInstance(
                    getDockerClientConfig(UNIX),
                    getDockerHttpClient(UNIX));
        }
    }

    @Override
    public DockerClient getClient(DockerClientType type, String address, int port) {
        addressValidator.validate(address);
        portValidator.validate(port);

        return getClient(type);
    }

}
