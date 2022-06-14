package cloud.hashcodeentertainment.executionengineservice.git.ports;

public interface GitClient {

    void cloneRepo(String repoAddress, Long taskId);
}
