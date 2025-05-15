package ru.nsu.abramenko.bot.service.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface Client {
    <T> ResponseEntity<T> post(
            String url,
            HttpEntity<?> request,
            Class<T> clazz
    );
}
