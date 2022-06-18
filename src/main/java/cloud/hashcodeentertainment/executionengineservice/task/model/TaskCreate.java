package cloud.hashcodeentertainment.executionengineservice.task.model;

import lombok.Data;

@Data
public class TaskCreate {

    private String name;
    private String language;
    private String languageVersion;
    private String repoAddress;
}
