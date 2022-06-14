package cloud.hashcodeentertainment.executionengineservice.taks.ports;

public interface TaskService {

    void createTask();

    void runTask();

    void getExecutionResult();

    void getTaksOutput();

    void getTaskHistory();
}
