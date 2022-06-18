package cloud.hashcodeentertainment.executionengineservice.task.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TaskCreateRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String language;

    @NotEmpty
    private String languageVersion;

    @NotEmpty
    private String repoAddress;
}
