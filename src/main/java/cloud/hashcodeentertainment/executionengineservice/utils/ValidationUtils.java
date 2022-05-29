package cloud.hashcodeentertainment.executionengineservice.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationUtils {

    public static <T> List<T> combine (List<T>... errorsLists){
        List<T> result = new ArrayList<>();
        for (var errorList : errorsLists){
            result.addAll(errorList);
        }
        return result;
    }
}