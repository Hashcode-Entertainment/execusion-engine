package cloud.hashcodeentertainment.executionengineservice.git.ports;

public interface DirectoryManager {

    void createDir(String name);

    void deleteDir(String name);
}
