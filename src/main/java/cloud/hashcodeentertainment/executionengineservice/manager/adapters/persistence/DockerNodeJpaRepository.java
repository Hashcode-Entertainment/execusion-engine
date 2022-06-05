package cloud.hashcodeentertainment.executionengineservice.manager.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DockerNodeJpaRepository extends JpaRepository<DockerNodeEntity, Long> {

    Optional<DockerNodeEntity> findByName(String name);

    Optional<DockerNodeEntity> findByAddress(String address);

    void deleteByName(String name);
}
