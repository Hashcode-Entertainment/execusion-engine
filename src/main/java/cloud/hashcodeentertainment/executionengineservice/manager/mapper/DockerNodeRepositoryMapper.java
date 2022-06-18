package cloud.hashcodeentertainment.executionengineservice.manager.mapper;

import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.entity.DockerNodeEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DockerNodeRepositoryMapper {

    @Mapping(target = "id", ignore = true)
    DockerNodeEntity toEntity(DockerNode dockerNode);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "numberOfRunningTasks", ignore = true)
    @Mapping(target = "client", ignore = true)
    DockerNode toDomain(DockerNodeEntity dockerNodeEntity);

    @IterableMapping(elementTargetType = DockerNode.class)
    List<DockerNode> toDomain(List<DockerNodeEntity> dockerNodeEntities);
}
