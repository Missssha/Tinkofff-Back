package edu.java.scrapper.clients;

import edu.java.dto.GitHubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GitHubRepositoryTest {

    @Mock
    private GitHubRepository gitHubRepository;

    @Test
    public void testGettersAndSetters() {
        gitHubRepository = new GitHubRepository();
        gitHubRepository.setId(123L);
        gitHubRepository.setName("test-repo");
        gitHubRepository.setDefaultBranch("test-branch");

        assertEquals(Optional.ofNullable(123L), Optional.ofNullable(gitHubRepository.getId()));
        assertEquals("test-repo", gitHubRepository.getName());
        assertEquals("test-branch", gitHubRepository.getDefaultBranch());
    }

    @Test
    public void testConstructorWithNullArgs() {
        gitHubRepository = new GitHubRepository();
        assertThrows(NullPointerException.class, () -> new GitHubRepository());
    }//TODO переделать тест

    @Test
    public void testConstructorWithValidArgs() {
        new GitHubRepository(123L, "test-repo", "test-branch");
    }


}
