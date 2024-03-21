package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.models.SessionState;
import edu.java.bot.users.User;
import edu.java.bot.processors.CommandHandler;
import edu.java.bot.processors.UrlProcessor;
import edu.java.bot.repository.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MessageServiceTest {

    @Mock
    private CommandHandler commandHandler;
    @Mock
    private UserService userService;
    @Mock
    private UrlProcessor urlProcessor;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private User user;
    @Mock
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(telegramBot, commandHandler, userService, urlProcessor);
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
}
