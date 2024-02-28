package edu.java.bot.processors;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {

    @Mock
    Command testCommand = new Command() {
        @Override
        public String command() {return null;}
        @Override
        public String description() {return null;}
        @Override
        public String handle(Update update) {return null;}
    };
    @Test
    void getCommand_shouldReturnCommand_whenCommandExists() {

        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("test", testCommand);

        CommandHandler handler = new CommandHandler(commandMap);

        Optional<Command> retrievedCommand = handler.getCommand("test");
        assertTrue(retrievedCommand.isPresent());
        assertSame(testCommand, retrievedCommand.get());
    }

    @Test
    void getCommand_shouldReturnEmptyOptional_whenCommandDoesNotExist() {

        Map<String, Command> commandMap = new HashMap<>();

        CommandHandler handler = new CommandHandler(commandMap);

        Optional<Command> retrievedCommand = handler.getCommand("unknown");
        assertTrue(retrievedCommand.isEmpty());
    }
}
