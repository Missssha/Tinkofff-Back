package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.models.Request.AddLinkRequest;
import edu.java.bot.models.ScrapperClient;
import edu.java.bot.models.SessionState;
import edu.java.bot.models.exception.ApiException;
import edu.java.bot.processors.CommandHandler;
import edu.java.bot.processors.UrlProcessor;
import edu.java.bot.repository.UserService;
import edu.java.bot.users.User;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.util.retry.RetryBackoffSpec;

@Service
public class MessageService {
    public static final String DO_REGISTRATION_MESSAGE = "Необходимо зарегистрироваться.";
    public static final String INVALID_URI_MESSAGE = "Неверно указан URI.";
    public static final String INVALID_COMMAND_MESSAGE = "Команда введена некорректно.";
    public static final String SUCCESS_TRACK_SITE_MESSAGE = "Сайт успешно добавлен в отслеживаемые.";
    public static final String DUPLICATE_TRACKING_MESSAGE = "Этот сайт уже отслеживается.";
    public static final String INVALID_FOR_TRACK_SITE_MESSAGE = "Отслеживание ресурса с этого сайта не поддерживается.";

    private final CommandHandler commandHandler;
    private final UserService userRepository;
    private final UrlProcessor urlProcessor;
    private RetryBackoffSpec retryBackoffSpec;

    public MessageService(
        CommandHandler commandHandler,
        UserService userRepository,
        UrlProcessor urlProcessor
    ) {
        this.commandHandler = commandHandler;
        this.userRepository = userRepository;
        this.urlProcessor = urlProcessor;
    }

    public String prepareResponseMessage(Update update) {
        var chatId = update.message().chat().id();
        var textMessage = update.message().text();
        var botCommand = commandHandler.getCommand(textMessage);
        return botCommand.map(command -> command.handle(update))
            .orElseGet(() -> processNonCommandMessage(chatId, textMessage));
    }

    public String processNonCommandMessage(Long chatId, String text) {
        var userOptional = userRepository.findUserById(chatId);
        if (userOptional.isEmpty()) {
            return DO_REGISTRATION_MESSAGE;
        }

        var user = userOptional.get();

        try {
            if (!user.getState().equals(SessionState.BASE_STATE)) {
                URI uri = new URI(text);
                String host = uri.getHost();
                if (host == null) {
                    throw new URISyntaxException(text, INVALID_URI_MESSAGE);
                }
                if (urlProcessor.isValidUrl(uri)) {
                    return processStateUserMessage(user, uri);
                }
                throw new URISyntaxException(text, INVALID_FOR_TRACK_SITE_MESSAGE);
            } else {
                return INVALID_COMMAND_MESSAGE;
            }
        } catch (URISyntaxException e) {
            return e.getReason();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String processStateUserMessage(User user, URI uri) throws MalformedURLException {
        if (user.getState().equals(SessionState.WAIT_URI_FOR_TRACKING)) {
            return prepareWaitTrackingMessage(user, uri);
        }
        if (user.getState().equals(SessionState.WAIT_URI_FOR_UNTRACKING)) {
            return prepareWaitUnTrackingMessage(user, uri);
        }
        return INVALID_COMMAND_MESSAGE;
    }

    private String prepareWaitTrackingMessage(User user, URI uri) {
        if (urlProcessor.isValidUrl(uri)) {
            return (updateUserTrackingSites(user, uri)) ? SUCCESS_TRACK_SITE_MESSAGE
                : DUPLICATE_TRACKING_MESSAGE;
        }
        return INVALID_FOR_TRACK_SITE_MESSAGE;
    }

    private String prepareWaitUnTrackingMessage(User user, URI uri) {
        if (urlProcessor.isValidUrl(uri)) {
            return (deleteTrackingSites(user, uri)) ? "Ресурс более не отслеживается."
                : "Вы не отслеживали данный ресурс.";
        }
        return INVALID_FOR_TRACK_SITE_MESSAGE;
    }

    public boolean updateUserTrackingSites(User user, URI uri) {
        List<URI> trackSites = new ArrayList<>(user.getSites());
        new ScrapperClient(retryBackoffSpec);
        trackSites.add(uri);
        updateTrackSitesAndCommit(user, trackSites);
        return true;
    }

    public boolean deleteTrackingSites(User user, URI uri) {
        List<URI> trackSites = new ArrayList<>(user.getSites());
        if (!trackSites.contains(uri)) {
            return false;
        }

        trackSites.remove(uri);
        updateTrackSitesAndCommit(user, trackSites);
        return true;
    }

    public void updateTrackSitesAndCommit(User user, List<URI> trackSites) {
        user.setSites(trackSites);
        user.setState(SessionState.BASE_STATE);
        userRepository.saveUser(user);
    }
}
