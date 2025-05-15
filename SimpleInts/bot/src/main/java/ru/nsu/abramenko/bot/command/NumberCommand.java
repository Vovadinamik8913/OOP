package ru.nsu.abramenko.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.nsu.abramenko.bot.model.ChatState;
import ru.nsu.abramenko.bot.model.State;
import ru.nsu.abramenko.bot.service.StateService;
import ru.nsu.abramenko.bot.service.client.Client;


@RequiredArgsConstructor
public class NumberCommand implements Command {
    private final StateService stateService;
    private final Client client;

    @Override
    public String command() {
        return "[";
    }

    @Override
    public String description() {
        return "check if numbers are prime";
    }

    @Override
    public boolean supports(Update update) {
        if (update.message() == null || update.message().text() == null) {
            return false;
        }
        String text = update.message().text().trim();

        if (!text.startsWith("[") || !text.endsWith("]")) {
            return false;
        }

        String content = text.substring(1, text.length() - 1).trim();
        String[] parts = content.split("\\s*,\\s*");
        for (String part : parts) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SendMessage handle(Update update) {
        String text = update.message().text();
        try {
            ChatState chatState = stateService.saveState(
                    update.message().chat().id(),
                    State.NOTSTARTED,
                    text
            );
            ResponseEntity<String> result = client.post();
            if (result.getStatusCode() == HttpStatus.OK) {
                stateService.updateState(chatState.getId(), State.PROCESSING);
            }
            return new SendMessage(update.message().chat().id(), result.getBody());
        } catch (Exception e) {
            return new SendMessage(update.message().chat().id(), "Invalid input format. Use: [1,2,3,...]");
        }
    }
}
