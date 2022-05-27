package cloud.hashcodeentertainment.executionengineservice.domain.docker;

import com.github.dockerjava.api.model.Frame;
import lombok.Getter;

@Getter
public class Output {

    private final byte[] data;

    public Output(Frame frame) {
        this.data = frame.getPayload();
    }
}
