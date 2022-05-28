package cloud.hashcodeentertainment.executionengineservice.domain.docker;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Frame;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

public class FrameCallback implements ResultCallback<Frame> {

    private final Consumer<Output> onLog;

    public FrameCallback(Consumer<Output> onLog) {
        this.onLog = onLog;
    }

    @Override
    public void onStart(Closeable closeable) {

    }

    @Override
    public void onNext(Frame frame) {
        if (onLog != null) {
            onLog.accept(new Output(frame));
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void close() throws IOException {

    }
}
