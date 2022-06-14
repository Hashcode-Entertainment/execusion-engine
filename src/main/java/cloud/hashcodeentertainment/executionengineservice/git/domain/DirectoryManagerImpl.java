package cloud.hashcodeentertainment.executionengineservice.git.domain;

import cloud.hashcodeentertainment.executionengineservice.git.ports.DirectoryManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryManagerImpl implements DirectoryManager {
    @Override
    public void createDir(String name) {
        try {
            Files.createDirectory(Paths.get(name));
        } catch (IOException e) {
            throw new GitClientException("Directory already exists, cant create \"" + name + "\"");
        }
    }

    @Override
    public void deleteDir(String name) {
        try {
            FileUtils.cleanDirectory(new File(name));
            Files.delete(Paths.get(name));
        } catch (Exception e) {
            throw new GitClientException("Directory not found, cant delete \"" + name + "\"");
        }
    }
}
