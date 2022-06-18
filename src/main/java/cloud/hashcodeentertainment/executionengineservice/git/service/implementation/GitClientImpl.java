package cloud.hashcodeentertainment.executionengineservice.git.service.implementation;

import cloud.hashcodeentertainment.executionengineservice.git.exception.GitClientException;
import cloud.hashcodeentertainment.executionengineservice.git.service.GitClient;
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
