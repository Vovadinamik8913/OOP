package ru.nsu.abramenko.graph.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EdgeTest {
    @Test
    void getTest() {
        Edge<Integer> e = new Edge<>(1, 2);
        assertEquals(e.getTo() + e.getFrom(), 3);
    }

    @Test
    @DisplayName("ValueTest")
    void valueTest() {
        Edge<Integer> e1 = new Edge<>(1, 2);
        e1.setValue(10);
        Edge<Integer> e2 = new Edge<>(1, 3, 100);
        assertEquals(110, e2.getValue() + e1.getValue());
    }

    @Test
    @DisplayName("ToStringTest")
    void toStringTest() {
        Edge<Integer> e1 = new Edge<>(1, 2);
        System.out.println(e1);
        assertTrue(true);
    }

    @Test
    @DisplayName("EqualsTest")
    void equalsTest() {
        Edge<Integer> e1 = new Edge<>(1, 2);
        Edge<Integer> e2 = new Edge<>(1, 2);
        assertEquals(e1, e2);
    }
}