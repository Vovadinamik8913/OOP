package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.math.*;
import ru.nsu.abramenko.math.Number;

class ExpressionTest {
    @Test
    void mainCheck() {
        Main.main(null);
        assertTrue(true);
    }

    @Test
    @DisplayName("AddTest")
    void addTest() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        assertEquals(23.0, e.eval("x = 10; y = 13"));
    }

    @Test
    @DisplayName("MulTest")
    void mulTest() {
        Expression e = new Mul(new Number(3), new Div(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        assertEquals(12.0, e.eval("x = 0.5"));
    }

    @Test
    @DisplayName("SubEquals")
    void subEqualsTest() {
        Expression e = new Sub(new Add(new Number(2),
                new Variable("x")), new Add(new Variable("x"), new Number(2)));
        Expression de = e.simplify();
        e.print();
        de.print();
        assertTrue(true);
    }
}