package cloud.hashcodeentertainment.executionengineservice.manager.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.DockerNode;
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
