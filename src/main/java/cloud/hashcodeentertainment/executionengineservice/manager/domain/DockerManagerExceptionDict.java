package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DockerManagerExceptionDict {
    public static final String ONLY_ONE_LOCAL_INSTANCE = "Only one local instance is allowed";
    public static final String ADDRESS_EXISTS = "Address already in use, cant create node on existing address";
    public static final String NODE_NAME_EXISTS = "NOde name already in use, cant create node with not unique name";
}
