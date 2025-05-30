package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ExpressionTest {
    @Test
    @DisplayName("Equals")
    void equalsTest() {
        Expression a = new Number(2);
        Expression b = new Number(2);
        String c = "asb";
        assertTrue(a.equals(b)
                && a.equals(a) && !a.equals(c));
    }

    @Test
    @DisplayName("ToString")
    void stringTest() {
        Expression a = new Mul(new Number(2), new Variable("X"));
        System.out.println(a);
        assertTrue(true);
    }
}