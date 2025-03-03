package ru.nsu.abramenko.pizzeria;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PizzeriaTest {
    @Test
    @DisplayName("INIT TEST")
    void configInitTest() {
        try {
            Pizzeria pizzeria = new Pizzeria();
            assertNotNull(pizzeria);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("INIT TEST")
    void baseInitTest() {
        Pizzeria pizzeria = new Pizzeria(10, 2, 10);
        assertNotNull(pizzeria);
    }

    @Test
    @DisplayName("WorkTest")
    void workTest() {
        Pizzeria pizzeria = new Pizzeria(3, 2, 10);
        pizzeria.work();
        for (int i = 0; i < 40; i++) {
            Order order = new Order();
            try {
                pizzeria.add(order);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            pizzeria.waitAllCompleted();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        pizzeria.close();
    }

    @Test
    @DisplayName("mainTest")
    void mainTest() {
        Pizzeria.main(null);
    }
}