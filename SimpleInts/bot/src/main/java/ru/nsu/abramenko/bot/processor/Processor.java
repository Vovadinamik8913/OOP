package ru.nsu.abramenko.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.nsu.abramenko.bot.command.Command;

import java.util.List;

public interface Processor {
    List<? extends Command> commands();

    SendMessage process(Update update);
}
