package cloud.hashcodeentertainment.executionengineservice.git.domain.implementation;

import cloud.hashcodeentertainment.executionengineservice.git.domain.exception.GitClientException;
import cloud.hashcodeentertainment.executionengineservice.git.ports.GitClient;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class GitClientImpl implements GitClient {

    @Override
    public void cloneRepo(String repoAddress, Long taskId) {
        String baseDir = "tasks_space";

        try {
            Git.cloneRepository()
                    .setURI(repoAddress)
                    .setDirectory(new File(baseDir + "/" + taskId))
                    .call().close();
        } catch (GitAPIException e) {
            throw new GitClientException(e.getMessage());
        }
    }
}
