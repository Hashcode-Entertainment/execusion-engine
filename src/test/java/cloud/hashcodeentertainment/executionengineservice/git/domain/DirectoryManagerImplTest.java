package cloud.hashcodeentertainment.executionengineservice.git.domain;

import cloud.hashcodeentertainment.executionengineservice.git.exception.GitClientException;
import cloud.hashcodeentertainment.executionengineservice.git.service.DirectoryManager;
import cloud.hashcodeentertainment.executionengineservice.git.service.implementation.DirectoryManagerImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class DirectoryManagerImplTest {

    private final DirectoryManager directoryManager = new DirectoryManagerImpl();

    @Test
    void shouldCreateAndDeleteDirectory() {
        String dirName = "blah";

        directoryManager.createDir(dirName);
        directoryManager.deleteDir(dirName);
    }

    @Test
    void shouldThrowGitClientException_WhenCreatingDirectoryWhichExists() {
        String dirName = "blah";

        directoryManager.createDir(dirName);

        assertThatThrownBy(() -> directoryManager.createDir(dirName))
                .isInstanceOf(GitClientException.class)
                .hasMessageContaining("Directory already exists");

        directoryManager.deleteDir(dirName);
    }

    @Test
    void shouldThrowGitClientException_WhenDeletingNotExistingDirectory() {
        String dirName = "blahhhh";

        assertThatThrownBy(() -> directoryManager.deleteDir(dirName))
                .isInstanceOf(GitClientException.class)
                .hasMessageContaining("Directory not found");

        directoryManager.addInitialTaskDirectory("tasks_space");
    }
}
