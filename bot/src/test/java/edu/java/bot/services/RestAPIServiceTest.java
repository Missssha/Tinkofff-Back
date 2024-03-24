package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.SessionState;
import edu.java.bot.users.User;
import edu.java.bot.repository.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

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
    public void testSendNotification_InvalidUri() throws Exception {
        Long chatId = 12345L;
        String invalidUri = "invalid_uri_string";
        String description = "Test notification description";

        restApiService.sendNotification(List.of(chatId), new URI(invalidUri), description);
    }
}
