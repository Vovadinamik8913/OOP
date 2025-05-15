package ru.nsu.abramenko.bot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class ChatState {
    @Id
    private String id;
    private Long chatId;
    private String data;
    private State state;
}
