package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    @DisplayName("MulTest")
    void mulDerivativeTest() {
        Expression e = new Mul(new Number(3), new Div(new Number(2),
                new Variable("x")));
        e.print();
        Expression de = e.derivative("x");
        de.print();
        assertEquals(12.0, e.eval("x = 0.5"));
    }

    @Test
    @DisplayName("MulZero")
    void mulZeroTest() {
        Expression e = new Mul(new Variable("x"), new Sub(new Number(3), new Number(3)));
        Expression de = e.simplify();
        de.print();
        e.print();
        assertTrue(true);
    }

    @Test
    void mulTest() {
        Expression e = new Mul(
                new Number(2), new Number(6));
        assertEquals(e.eval(""), 12);
    }

}