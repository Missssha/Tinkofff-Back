package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class RestAPIServiceTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private UserService userService = new UserService();
    @Mock
    private RestApiService restApiService;
    @Mock
    private User mockUser;

    @BeforeEach
    public void setUp() {
        restApiService = new RestApiService(telegramBot, userService);
    }

    @Test()
    public void testSendNotificationInvalidUri() throws Exception {
        Long chatId = 12345L;
        String invalidUri = "invalid_uri_string";
        String description = "Test notification description";

        restApiService.sendNotification(List.of(chatId), new URI(invalidUri), description);
    }
}
