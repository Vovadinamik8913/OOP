package ru.nsu.abramenko.bot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nsu.abramenko.bot.model.ChatState;

import java.util.Optional;

public interface ChatStateRepository extends MongoRepository<ChatState, String> {
    Optional<ChatState> findByChatId(Long chatId);
    void deleteById(String id);

}
