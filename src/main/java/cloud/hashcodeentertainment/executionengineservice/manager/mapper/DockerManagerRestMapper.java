package cloud.hashcodeentertainment.executionengineservice.manager.mapper;

import cloud.hashcodeentertainment.executionengineservice.manager.request.DockerNodeCreateRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.response.DockerNodeResponse;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerStartOption;
import cloud.hashcodeentertainment.executionengineservice.manager.model.ContainerUnit;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerImage;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.request.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerOption;
import cloud.hashcodeentertainment.executionengineservice.manager.response.ContainerUnitResponse;
import cloud.hashcodeentertainment.executionengineservice.manager.response.DockerContainerResponse;
import cloud.hashcodeentertainment.executionengineservice.manager.response.DockerImageResponse;
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
