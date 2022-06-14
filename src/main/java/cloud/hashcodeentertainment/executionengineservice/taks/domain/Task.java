package cloud.hashcodeentertainment.executionengineservice.taks.domain;

import cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence.ResultEntity;
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
