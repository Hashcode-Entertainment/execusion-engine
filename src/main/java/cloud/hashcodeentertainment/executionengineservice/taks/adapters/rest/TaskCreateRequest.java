package cloud.hashcodeentertainment.executionengineservice.taks.adapters.rest;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private String language;

    @NotNull
    private String languageVersion;

    @NotNull
    private String repoAddress;
}
