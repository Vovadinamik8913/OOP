package ru.nsu.abramenko.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.nsu.abramenko.bot.command.Command;

import java.util.List;


public class UserMessageProcessor implements Processor {
    private final List<Command> commands;

    public UserMessageProcessor(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        if (update.message() == null) {
            return null;
        }
        return new SendMessage(update.message().chat().id(), "Неизвестная команда! Используйте /help");
    }
}
