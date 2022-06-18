package cloud.hashcodeentertainment.executionengineservice.git.domain;

import cloud.hashcodeentertainment.executionengineservice.git.exception.GitClientException;
import cloud.hashcodeentertainment.executionengineservice.git.service.GitClient;
import cloud.hashcodeentertainment.executionengineservice.git.service.implementation.GitClientImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class GitClientImplTest {

    private final GitClient gitClient = new GitClientImpl();

    @Test
    void shouldDownloadExistingRepo() {
        gitClient.cloneRepo("https://github.com/magikabdul/github-actions-lab.git", 1L);
    }

    @Test
    void shouldThrowGitClientException_WhenDownloadNotExistingRepo() {

        assertThatThrownBy(
                () -> gitClient.cloneRepo("https://github.com/magikabdul/github-actions.git", 1L)
        )
                .isInstanceOf(GitClientException.class);
    }
}
