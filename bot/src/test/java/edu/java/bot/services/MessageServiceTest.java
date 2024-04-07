package edu.java.bot.services;

import edu.java.bot.models.SessionState;
import edu.java.bot.processors.CommandHandler;
import edu.java.bot.processors.UrlProcessor;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


public class MessageServiceTest {

    @Mock
    private CommandHandler commandHandler;
    @Mock
    private UserService userService;
    @Mock
    private UrlProcessor urlProcessor;
    @Mock
    private User user;
    @Mock
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(commandHandler, userService, urlProcessor);
    }


    @Test
    public void testProcessNonCommandMessageEmptyMessage() {
        Long chatId = 0L;
        String message = "";

        String response = messageService.processNonCommandMessage(chatId, message);

        assertNotEquals(MessageService.INVALID_COMMAND_MESSAGE, response);
    }

    @Test
    public void testProcessNonCommandMessageUserNotFound() {
        Long chatId = 789L;
        String message = "Hello";

        when(userService.findUserById(chatId)).thenReturn(Optional.empty());

        String response = messageService.processNonCommandMessage(chatId, message);

        assertEquals(MessageService.DO_REGISTRATION_MESSAGE, response);
    }
    @Test
    public void testProcessStateUserMessageForWaitTracking() throws Exception {
        user = new User(SessionState.WAIT_URI_FOR_TRACKING);

        URI validUri = URI.create("https://www.example.com");
        String response = messageService.processStateUserMessage(user, validUri);

        assertEquals("Отслеживание ресурса с этого сайта не поддерживается.", response);
    }
    @Test
    public void testProcessStateUserMessageForWaitUnTracking() throws Exception {
        user = new User(SessionState.WAIT_URI_FOR_TRACKING);
        URI validUri = URI.create("https://www.example.com");
        String response = messageService.processStateUserMessage(user, validUri);

        assertEquals("Отслеживание ресурса с этого сайта не поддерживается.", response);
    }
    @Test
    public void testProcessStateUserMessageForOtherState() throws Exception {
        user = new User(SessionState.BASE_STATE);
        URI uri = URI.create("https://www.example.com");
        String response = messageService.processStateUserMessage(user, uri);

        assertEquals(MessageService.INVALID_COMMAND_MESSAGE, response);
    }

    @Test
    public void deleteTrackingSites_SuccessfullyRemovesUri_Test() {
        User mockUser = Mockito.mock(User.class);
        URI uriToRemove = Mockito.mock(URI.class);
        List<URI> originalTrackSites = Arrays.asList(uriToRemove, Mockito.mock(URI.class));
        List<URI> expectedTrackSites = originalTrackSites.subList(1, 2); // Remove first URI

        UserService mockUserService = Mockito.mock(UserService.class);

        Mockito.when(mockUser.getSites()).thenReturn(originalTrackSites);

        MessageService messageService = new MessageService(null, mockUserService, null);

        boolean result = messageService.deleteTrackingSites(mockUser, uriToRemove);

        Mockito.verify(mockUser, Mockito.times(1)).getSites();
        Mockito.verify(mockUser, Mockito.times(1)).setSites(expectedTrackSites);
        Mockito.verify(mockUserService, Mockito.times(1)).saveUser(mockUser);
        assertTrue(result);
    }

    @Test
    public void updateTrackSitesAndCommit_Test() throws URISyntaxException {
        User user = new User();
        userService = new UserService();
        List<URI> trackSites = new ArrayList<>();
        trackSites.add(new URI("https://www.example.com"));
        SessionState expectedState = SessionState.BASE_STATE;
        MessageService messageService = new MessageService(null, userService, null);
        messageService.updateTrackSitesAndCommit(user, trackSites);

        assertEquals(trackSites, user.getSites());
        assertEquals(expectedState, user.getState());
    }
}
