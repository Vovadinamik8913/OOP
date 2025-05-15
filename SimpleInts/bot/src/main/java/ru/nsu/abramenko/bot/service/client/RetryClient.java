package ru.nsu.abramenko.bot.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RetryClient implements Client {
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    @Override
    public <T> ResponseEntity<T> post(
            String url,
            HttpEntity<?> request,
            Class<T> clazz
    ) {
        return retryTemplate.execute(context ->
        {
            try {
                return restTemplate.postForEntity(url, request, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}