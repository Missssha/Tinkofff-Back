package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.processors.CommandHandler;
import edu.java.bot.processors.UrlProcessor;
import edu.java.bot.repository.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
}
