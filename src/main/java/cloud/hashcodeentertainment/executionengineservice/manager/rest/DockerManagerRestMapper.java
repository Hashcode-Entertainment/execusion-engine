package cloud.hashcodeentertainment.executionengineservice.manager.rest;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.ContainerUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerImage;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerOption;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = DockerNodePortMapper.class)
public interface DockerManagerRestMapper {

    DockerNodeResponse toRest(DockerNode dockerNode);

    @IterableMapping(elementTargetType = DockerNodeResponse.class)
    List<DockerNodeResponse> toRest(List<DockerNode> dockerNodes);

    DockerNodeRequest toDomain(DockerNodeCreateRequest nodeCreateRequest);

    DockerImageResponse toRestDockerImageResponse(DockerImage dockerImage);

    @IterableMapping(elementTargetType = DockerImageResponse.class)
    List<DockerImageResponse> toRestDockerImageResponse(List<DockerImage> images);

    default DockerContainerResponse toRestDockerContainerResponse(String containerId) {
        return DockerContainerResponse.builder().containerId(containerId).build();
    }

    @Mapping(target = "entryPoint", ignore = true)
    @Mapping(target = "containerId", ignore = true)
    @Mapping(target = "commands", ignore = true)
    @Mapping(target = "command", ignore = true)
    DockerOption toDomainDockerOption(DockerStartOption dockerStartOption);

    ContainerUnitResponse toRestContainerUnit(ContainerUnit containerUnit);
}
