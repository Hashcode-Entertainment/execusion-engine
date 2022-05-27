package cloud.hashcodeentertainment.executionengineservice.domain.docker.adapter;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.FrameCallback;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.Output;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.exception.DockerException;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.port.in.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.api.model.ResponseItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class DockerServiceAdapter implements DockerService {

    private final DockerClient dockerClient = new DockerClientFactoryImpl().getClient();

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

    @Override
    public String startContainer(DockerOption option) {
        CreateContainerResponse container = dockerClient
                .createContainerCmd(option.getImage())
                .withEntrypoint(option.getCommands())
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        return container.getId();
    }

    @SneakyThrows
    @Override
    public void waitContainer(String containerId, int timeoutInSeconds, Consumer<Output> onLog) {
        var maxWaitTime = Instant.now().plus(timeoutInSeconds, ChronoUnit.SECONDS);

        if (onLog != null) {
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new FrameCallback(onLog));

            while (true) {
                var state = dockerClient.inspectContainerCmd(containerId).exec().getState();
                if (state.getRunning() == null || !state.getRunning()) {
                    return;
                }

                if (Instant.now().isAfter(maxWaitTime)) {
                    throw new DockerException("The maximum timeout has been exceeded");
                }

                TimeUnit.SECONDS.sleep(2);
            }
        }
    }

    @Override
    public void stopContainer(String containerId) {
        var isRunning = dockerClient.inspectContainerCmd(containerId).exec().getState().getRunning();

        if (isRunning != null && isRunning) {
            dockerClient.stopContainerCmd(containerId);
        }
    }
}
