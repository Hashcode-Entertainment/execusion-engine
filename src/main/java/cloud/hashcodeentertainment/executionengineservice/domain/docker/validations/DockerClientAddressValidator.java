package cloud.hashcodeentertainment.executionengineservice.domain.docker.validations;

import cloud.hashcodeentertainment.executionengineservice.domain.docker.utils.MessageUtils;
import cloud.hashcodeentertainment.executionengineservice.domain.docker.utils.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DockerClientAddressValidator {

    public void validate(String address){
        List<String> errors = ValidationUtils.combine(validateAddressInDockerClient(address));
    }

    private List<String> validateAddressInDockerClient(String address){
        List<String> errors = new ArrayList<>();
        if (address.contains(",")) errors.add(MessageUtils.getMessage("validation.docker.client.address"));
        return errors;
    }
}