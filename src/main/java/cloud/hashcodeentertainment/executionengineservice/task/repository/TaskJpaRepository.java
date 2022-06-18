package cloud.hashcodeentertainment.executionengineservice.task.repository;

import cloud.hashcodeentertainment.executionengineservice.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {


}
