package cloud.hashcodeentertainment.executionengineservice.domain.docker.utils;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {

    public static <T> List<T> combine (List<T>... errorsLists){
        List<T> result = new ArrayList<>();
        for (var errorList : errorsLists){
            result.addAll(errorList);
        }
        return result;
    }
}
