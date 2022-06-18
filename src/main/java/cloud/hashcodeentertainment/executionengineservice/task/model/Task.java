package cloud.hashcodeentertainment.executionengineservice.task.model;

import cloud.hashcodeentertainment.executionengineservice.task.entity.ResultEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Task {

    private Long id;
    private String name;
    private String language;
    private String languageVersion;
    private String repoAddress;
    private List<ResultEntity> runResults;
}
