package cloud.hashcodeentertainment.executionengineservice.git.service;

public interface DirectoryManager {

    void createDir(String name);

    void deleteDir(String name);

    void addInitialTaskDirectory(String name);
}
