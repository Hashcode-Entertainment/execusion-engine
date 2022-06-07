package cloud.hashcodeentertainment.executionengineservice.commons.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationUri {

    private static final String SEPARATOR = "/";

    public static URI getUri(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(SEPARATOR + id)
                .build()
                .toUri();
    }
}
