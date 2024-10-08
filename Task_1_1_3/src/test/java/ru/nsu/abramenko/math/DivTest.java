package ru.nsu.abramenko.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DivTest {
    @Test
    @DisplayName("DivZero")
    void divZeroTest() {
        assertThrows(ArithmeticException.class, () -> {
            Expression e = new Div(new Variable("x"), new Sub(new Number(3), new Number(3)));
            Expression de = e.simplify();
            e.print();
            de.print();
        });
    }

    @Test
    void divTest() {
        Expression e = new Div(
                new Number(12), new Number(6));
        assertEquals(e.eval(""), 2);
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Div(
                new Number(2), new Variable("x"));
        e = e.derivative("x");
        e.print();
        assertEquals(e.eval("x=1"), -2);
    }

    @Test
    @DisplayName("SimplifyTest")
    void simplifyTest() {
        Expression e = new Div(
                new Number(12), new Number(6));
        Expression e2 = e.simplify();
        assertEquals(e2.eval(""), 2);
    }
}