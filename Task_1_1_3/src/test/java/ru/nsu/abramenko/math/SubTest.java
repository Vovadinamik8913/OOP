package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubTest {
    @Test
    @DisplayName("SubEquals")
    void subEqualsTest() {
        Expression e = new Sub(new Add(new Number(2),
                new Variable("x")), new Add(new Number(2), new Variable("x")));
        Expression de = e.simplify();
        de.print();
        e.print();
        assertTrue(true);
    }

    @Test
    void subTest() {
        Expression e = new Sub(
                new Number(12), new Number(6));
        assertEquals(e.eval(""), 6);
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Sub(
                new Number(2), new Variable("x"));
        e = e.derivative("x");
        e.print();
        assertEquals(e.eval("x=1"), -1);
    }

}