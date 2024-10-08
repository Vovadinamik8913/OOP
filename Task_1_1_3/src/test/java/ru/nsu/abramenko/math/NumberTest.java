package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberTest {
    @Test
    void numberTest() {
        Expression e = new Number(123);
        assertEquals(e.eval(""), 123);
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Number(123);
        e = e.derivative("x");
        assertEquals(e.eval(""), 0);
    }

    @Test
    @DisplayName("SimplifyTest")
    void simplifyTest() {
        Expression e = new Number(123);
        Expression e2 = e.simplify();
        assertEquals(e.eval(""), e2.eval(""));
    }
}