package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    @DisplayName("AddTest")
    void addDerivativeTest() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        assertEquals(23.0, e.eval("x = 10; y = 13"));
    }

    @Test
    void addTest() {
        Expression e = new Add(
                new Number(12), new Number(6));
        assertEquals(e.eval(null), 18);
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Add(
                new Number(2), new Variable("x"));
        e = e.derivative("x");
        e.print();
        assertEquals(e.eval("x=1"), 1);
    }

    @Test
    @DisplayName("SimplifyTest")
    void simplifyTest() {
        Expression e = new Add(
                new Mul(new Number(2), new Number(6)),
                new Number(6));
        Expression e2 = e.simplify();
        assertEquals(e2.eval(null), 18);
    }
}