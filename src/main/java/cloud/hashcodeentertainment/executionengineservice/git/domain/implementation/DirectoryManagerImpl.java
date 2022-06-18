package cloud.hashcodeentertainment.executionengineservice.git.domain.implementation;

import cloud.hashcodeentertainment.executionengineservice.git.domain.exception.GitClientException;
import cloud.hashcodeentertainment.executionengineservice.git.ports.DirectoryManager;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryManagerImpl implements DirectoryManager {

    private final String TASK_BASE_DIR = "tasks_space";

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
            FileUtils.forceDelete(new File(TASK_BASE_DIR, name));
        } catch (Exception e) {
            throw new GitClientException("Directory not found, cant delete \"" + name + "\"");
        }
    }

    @Override
    public void addInitialTaskDirectory(String name) {
        if (!Files.exists(Paths.get(name))) {
            createDir(name);
        }
    }
}
