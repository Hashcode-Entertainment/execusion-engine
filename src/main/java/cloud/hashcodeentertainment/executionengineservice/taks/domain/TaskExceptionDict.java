package cloud.hashcodeentertainment.executionengineservice.taks.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskExceptionDict {
    public final static String TASK_ID_EXISTS = "Task already exists";
    public final static String TASK_NOT_FOUND = "Task not found in database";
    public final static String UNSUPPORTED_LANGUAGE = "Language not supported";
    public final static String UNSUPPORTED_VERSION = "Language version not supported";
    public final static String RESULT_NOT_FOUND = "Result not found";
}
