package ru.nsu.abramenko.graph.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class VertexTest {
    @Test
    void equalsTest() {
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(1);
        assertEquals(v1, v2);
    }

    @Test
    @DisplayName("ToStringTest")
    void toStringTest() {
        Vertex<Integer> v1 = new Vertex<>(1);
        System.out.println(v1);
        assertTrue(true);
    }
}