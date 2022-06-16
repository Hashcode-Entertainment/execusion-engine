package cloud.hashcodeentertainment.executionengineservice.manager.domain;

import cloud.hashcodeentertainment.executionengineservice.manager.domain.implementation.DockerManagerServiceImpl;
import cloud.hashcodeentertainment.executionengineservice.manager.domain.utils.DockerNodeRequest;
import cloud.hashcodeentertainment.executionengineservice.manager.persistence.DockerNodeRepositoryAdapter;
import cloud.hashcodeentertainment.executionengineservice.manager.ports.DockerManagerService;
import com.github.dockerjava.api.exception.NotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@Disabled
class DockerManagerServiceImplTest {

    @Mock
    private DockerNodeRepositoryAdapter repository = mock(DockerNodeRepositoryAdapter.class);

    private final DockerManagerService managerService = new DockerManagerServiceImpl(repository);

    public DockerManagerServiceImplTest() {
        var dockerNodeRequest = new DockerNodeRequest();
        dockerNodeRequest.setName("local");

        managerService.addNode(dockerNodeRequest);
        managerService.restorePersistedNodes();
    }

    @Test
    void shouldPullImage() {
        assertThat(managerService.pullImage("openjdk", "16-jdk", 60))
                .isInstanceOf(String.class)
                .containsAnyOf("Image is up to date for openjdk:16-jdk", "Downloaded newer image for openjdk:16-jdk");
    }

    @Test
    void shouldPullImageThrowWhenPullingInvalidImage() {
        assertThatThrownBy(() -> managerService.pullImage("openjdk", "16-jd", 60))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Status 404");
    }

    @Test
    void shouldDeleteImage() {
        String validId = "d7e4c5a73ccd";

        managerService.deleteImage(validId);
    }

    @Test
    void shouldDeleteImageThrowWhenDeleteImageWithWrongId() {
        String invalidId = "e09ae0c7e68b963a43ab482571340a0211dfe3224e6ed987c0f00375970d609";

        assertThatThrownBy(() -> managerService.deleteImage(invalidId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Status 404:");
    }
}
