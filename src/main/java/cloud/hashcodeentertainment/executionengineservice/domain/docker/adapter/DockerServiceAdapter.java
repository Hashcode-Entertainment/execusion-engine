package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.api.model.ResponseItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DockerServiceAdapter implements DockerService {

    @Autowired
    private final DockerClientAddressValidator addressValidator;

    @Autowired
    private final DockerClientPortValidator portValidator;

    private final DockerClient dockerClient = new DockerClientServiceImpl(addressValidator, portValidator).getClient();

    @Override
    public void ping() {
        dockerClient.pingCmd().exec();
    }

    @SneakyThrows
    @Override
    public String pullImage(String name, String tag) {
        List<PullResponseItem> responseItemList = new ArrayList<>();

        dockerClient.pullImageCmd(name + ":" + tag).exec(new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                responseItemList.add(item);
            }
        }).awaitCompletion();

        return responseItemList.stream()
                .map(ResponseItem::getStatus).filter(Objects::nonNull)
                .filter(status -> status.matches("Status:.*"))
                .findFirst().orElseThrow();
    }
}
