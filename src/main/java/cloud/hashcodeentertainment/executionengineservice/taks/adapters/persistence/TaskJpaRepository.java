package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}
