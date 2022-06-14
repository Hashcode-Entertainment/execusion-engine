package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import lombok.Data;

@Data
public class TaskCreateRequest {

    private String name;
    private String language;
    private String languageVersion;
    private String repoAddress;
}
