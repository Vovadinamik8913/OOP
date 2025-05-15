package ru.nsu.abramenko.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;

public class HelpCommand implements Command {

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "list of available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String desc = """
            Available commands:
            /help - show this help message
            [n1,n2,...] - check if all numbers in sequence are prime
            Example: [2,3,5,7] -> true
            """;
        return new SendMessage(chatId, desc);
    }
}
