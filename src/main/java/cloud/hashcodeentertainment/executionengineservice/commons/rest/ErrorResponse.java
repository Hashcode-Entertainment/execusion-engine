package cloud.hashcodeentertainment.executionengineservice.commons.rest;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {
    LocalDateTime timestamp = LocalDateTime.now();
    String message;
}
