package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.SessionState;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import request.LinkUpdateRequest;

@Service
public class BotRestApiService implements RestApiService {
    private final TelegramBot telegramBot;
    private final UserService userRepository;
    private final MeterRegistry meterRegistry;

    public BotRestApiService(TelegramBot telegramBot, UserService userRepository, MeterRegistry meterRegistry) {
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
        this.meterRegistry = meterRegistry;
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
                increaseMessageMetric();
            } catch (Exception ex) {
                return;
            }

        }
    }
    private void increaseMessageMetric() {
        Counter counter = Counter
            .builder("messages.proceeded")
            .tag("application", "bot")
            .register(meterRegistry);
        counter.increment();
    }
}
