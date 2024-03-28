package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.SessionState;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.net.URI;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestApiService {
    private final TelegramBot telegramBot;
    private final UserService userRepository;

    public RestApiService(TelegramBot telegramBot, UserService userRepository) {
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
    }

    public void sendNotification(List<Long> tgIds, URI url, String description) {
        for (Long id : tgIds) {
            try {
                User user = userRepository.findUserById(id).get();
                user.setState(SessionState.WAITING_FOR_NOTIFICATION);
                userRepository.saveUser(user);
                telegramBot.execute(new SendMessage(
                    id,
                    "New update from link " + url.toString() + " message: " + description
                ));
            } catch (Exception ex) {
                return;
            }

        }
    }
}
