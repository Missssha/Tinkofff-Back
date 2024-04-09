package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import request.LinkUpdateRequest;

public class BotRestApiServiceTest {
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
        restApiService = new BotRestApiService(telegramBot, userService);
    }

    @Test()
    public void testSendNotificationInvalidUri() throws Exception {
        Long chatId = 12345L;
        String invalidUri = "invalid_uri_string";
        String description = "Test notification description";
        List<Long> tgChatIds = new ArrayList<>();
        tgChatIds.add(1L);
        tgChatIds.add(2L);
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(chatId, URI.create(invalidUri), description, tgChatIds);

        restApiService.sendNotification(linkUpdateRequest);
    }
}
