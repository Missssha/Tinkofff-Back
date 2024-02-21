package edu.java.scrapper.clients;

import edu.java.clients.GitHubClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GitHubClientTest {

    @Mock
    private GitHubClient gitHubClient;

    @Test
    public void testGettersAndSetters() {
        gitHubClient = new GitHubClient();
        gitHubClient.setId(123L);
        gitHubClient.setName("test-repo");
        gitHubClient.setFullname("owner/test-repo");
        gitHubClient.setDiscription("This is a test repository.");
        gitHubClient.setLanguage("Java");
        gitHubClient.setVisibility("public");

        assertEquals(Optional.ofNullable(123L), Optional.ofNullable(gitHubClient.getId()));
        assertEquals("test-repo", gitHubClient.getName());
        assertEquals("owner/test-repo", gitHubClient.getFullname());
        assertEquals("This is a test repository.", gitHubClient.getDiscription());
        assertEquals("Java", gitHubClient.getLanguage());
        assertEquals("public", gitHubClient.getVisibility());
    }

    @Test
    public void testConstructorWithNullArgs() {
        gitHubClient = new GitHubClient();
        assertThrows(NullPointerException.class, () -> new GitHubClient());
    }//TODO переделать тест

    @Test
    public void testConstructorWithValidArgs() {
        new GitHubClient(123L, "test-repo", "owner/test-repo", "This is a test repository.", "Java", "public");
    }


}
