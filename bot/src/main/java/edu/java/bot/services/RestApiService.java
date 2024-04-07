package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.SessionState;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import org.springframework.stereotype.Service;
import request.LinkUpdateRequest;

@Service
public class RestApiService implements RestApiServiceInterface {
    private final TelegramBot telegramBot;
    private final UserService userRepository;

    public RestApiService(TelegramBot telegramBot, UserService userRepository) {
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
    }

    public void sendNotification(LinkUpdateRequest linkUpdateRequest) {
        for (Long id : linkUpdateRequest.getTgChatIds()) {
            try {
                User user = userRepository.findUserById(id).get();
                user.setState(SessionState.WAITING_FOR_NOTIFICATION);
                userRepository.saveUser(user);
                telegramBot.execute(new SendMessage(
                    id,
                    "New update from link " + linkUpdateRequest.getUrl().toString()
                        + " message: " + linkUpdateRequest.getDescription()
                ));
            } catch (Exception ex) {
                return;
            }

        }
    }
}
