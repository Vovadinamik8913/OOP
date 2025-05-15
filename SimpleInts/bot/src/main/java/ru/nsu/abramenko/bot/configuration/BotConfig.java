package ru.nsu.abramenko.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.abramenko.bot.command.Command;
import ru.nsu.abramenko.bot.command.HelpCommand;
import ru.nsu.abramenko.bot.command.NumberCommand;
import ru.nsu.abramenko.bot.processor.Processor;
import ru.nsu.abramenko.bot.processor.UserMessageProcessor;
import ru.nsu.abramenko.bot.service.client.Client;
import ru.nsu.abramenko.bot.service.StateService;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BotConfig {
    private final List<Command> commands;

    public BotConfig(StateService stateService, Client client) {
        commands = new ArrayList<>();
        commands.add(new HelpCommand());
        commands.add(new NumberCommand(stateService, client));
    }

    @Bean
    public Processor userMessageProcessor() {
        return new UserMessageProcessor(commands);
    }
}
