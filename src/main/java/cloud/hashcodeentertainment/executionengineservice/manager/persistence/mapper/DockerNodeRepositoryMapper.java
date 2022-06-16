package cloud.hashcodeentertainment.executionengineservice.manager.persistence.mapper;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.persistence.entity.DockerNodeEntity;
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
