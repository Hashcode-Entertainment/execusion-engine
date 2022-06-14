package cloud.hashcodeentertainment.executionengineservice.git.domain;

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
                    .call();
        } catch (GitAPIException e) {
            throw new GitClientException(e.getMessage());
        }
    }
}
