package ru.nsu.abramenko.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.abramenko.bot.model.ChatState;
import ru.nsu.abramenko.bot.model.State;
import ru.nsu.abramenko.bot.repository.ChatStateRepository;

@Service
public class StateService {
    private final ChatStateRepository chatStateRepository;

    @Autowired
    public StateService(ChatStateRepository chatStateRepository) {
        this.chatStateRepository = chatStateRepository;
    }

    public ChatState saveState(Long chatId, State state, String data) {
        ChatState chatState = new ChatState();
        chatState.setChatId(chatId);
        chatState.setState(state);
        chatState.setData(data);
        return chatStateRepository.save(chatState);
    }

    public void updateState(String id, State state) {
        ChatState chatState = chatStateRepository.findById(id).orElse(null);
        if (chatState != null) {
            chatState.setState(state);
            chatStateRepository.save(chatState);
        }
    }

    public ChatState getState(Long chatId) {
        return chatStateRepository.findByChatId(chatId)
                .orElse(null);
    }

    public void clearState(Long chatId) {
        chatStateRepository.deleteByChatId(chatId);
    }
}
