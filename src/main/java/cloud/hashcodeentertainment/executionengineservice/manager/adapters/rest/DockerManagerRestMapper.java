package cloud.hashcodeentertainment.executionengineservice.manager.adapters.rest;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerImage;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNodeRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

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


}
