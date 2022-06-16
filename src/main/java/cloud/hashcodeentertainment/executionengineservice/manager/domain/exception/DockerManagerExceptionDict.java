package cloud.hashcodeentertainment.executionengineservice.manager.domain.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DockerManagerExceptionDict {
    public static final String ONLY_ONE_LOCAL_INSTANCE = "Only one local instance is allowed";
    public static final String ADDRESS_EXISTS = "Address already in use, cant create node on existing address";
    public static final String NODE_NAME_EXISTS = "Node name already in use, cant create node with not unique name";
    public static final String NODE_NAME_NOT_FOUND = "Node with provided name not found";
}
