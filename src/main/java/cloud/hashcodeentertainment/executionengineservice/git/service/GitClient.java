package cloud.hashcodeentertainment.executionengineservice.git.service;

public interface GitClient {

    void cloneRepo(String repoAddress, Long taskId);
}
