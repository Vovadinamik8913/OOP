package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void mainCheck() {
        try {
            Main.main(null);
            assertTrue(true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}