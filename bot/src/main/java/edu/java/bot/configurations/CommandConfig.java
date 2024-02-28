package edu.java.bot.configurations;

import edu.java.bot.commands.CommandHelp;
import edu.java.bot.commands.CommandList;
import edu.java.bot.commands.CommandStart;
import edu.java.bot.commands.CommandTrack;
import edu.java.bot.commands.CommandUntrack;
import edu.java.bot.processors.CommandHandler;
import edu.java.bot.repository.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class CommandConfig {
    @Bean
    CommandHandler commandHandler(UserService userService) {
        var start = new CommandStart(userService);
        var list = new CommandList(userService);
        var track = new CommandTrack(userService);
        var unTrack = new CommandUntrack(userService);

        var help = new CommandHelp(List.of(start, list, track, unTrack));

        return new CommandHandler(
            Map.of(
                "/start", start,
                "/help", help,
                "/list", list,
                "/track", track,
                "/untrack", unTrack
            )
        );
    }
}
