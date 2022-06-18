package cloud.hashcodeentertainment.executionengineservice.manager.adapter;

import cloud.hashcodeentertainment.executionengineservice.manager.mapper.DockerNodeRepositoryMapper;
import cloud.hashcodeentertainment.executionengineservice.manager.model.DockerNode;
import cloud.hashcodeentertainment.executionengineservice.manager.repository.DockerNodeRepository;
import cloud.hashcodeentertainment.executionengineservice.manager.repository.DockerNodeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DockerNodeRepositoryAdapter implements DockerNodeRepository {

    private final DockerNodeJpaRepository nodeJpaRepository;
    private final DockerNodeRepositoryMapper nodeRepositoryMapper;

    @Override
    public Optional<DockerNode> getByName(String name) {
        var nodeByName = nodeJpaRepository.findByName(name);

        return nodeByName.map(nodeRepositoryMapper::toDomain);
    }

    @Override
    public Optional<DockerNode> getByAddress(String address) {
        var nodeByName = nodeJpaRepository.findByAddress(address);

        return nodeByName.map(nodeRepositoryMapper::toDomain);
    }

    @Override
    public DockerNode save(DockerNode dockerNode) {
        var nodeEntity = nodeRepositoryMapper.toEntity(dockerNode);
        var savedEntity = nodeJpaRepository.save(nodeEntity);

        return nodeRepositoryMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteNode(String name) {
        nodeJpaRepository.deleteByName(name);
    }

    @Override
    public List<DockerNode> getAllNodes() {
        return nodeRepositoryMapper.toDomain(nodeJpaRepository.findAll());
    }
}
