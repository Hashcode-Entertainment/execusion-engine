package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
