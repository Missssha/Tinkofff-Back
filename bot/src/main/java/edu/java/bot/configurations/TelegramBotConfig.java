package edu.java.bot.configurations;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.processors.CommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {
    @Bean
    TelegramBot telegramBot(ApplicationConfig appConfig, CommandHandler commandHandler) {
        var bot = new TelegramBot(appConfig.telegramToken());

        bot.execute(createMenuCommand(commandHandler));
        return bot;
    }

    private SetMyCommands createMenuCommand(CommandHandler commandHandler) {
        return new SetMyCommands(commandHandler.commandMap().values().stream().map(command -> new BotCommand(
            command.command(),
            command.description()
        )).toArray(BotCommand[]::new));
    }
}
