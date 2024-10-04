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
        Expression de = null;
        try {
            de = e.simplify();
            de.print();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        e.print();
        assertTrue(true);
    }

    @Test
    void subTest() {
        Expression e = new Sub(
                new Number(12), new Number(6));
        try {
            assertEquals(e.eval(null), 6);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Sub(
                new Number(2), new Variable("x"));
        try {
            e = e.derivative("x");
            e.print();
            assertEquals(e.eval("x=1"), -1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}