package cloud.hashcodeentertainment.executionengineservice.taks.domain;

import lombok.Data;

@Data
public class TaskCreate {

    private String name;
    private String language;
    private String languageVersion;
    private String repoAddress;
}
