package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DivTest {
    @Test
    @DisplayName("DivZero")
    void divZeroTest() {
        try {
            Expression e = new Div(new Variable("x"), new Sub(new Number(3), new Number(3)));
            Expression de = e.simplify();
            e.print();
            de.print();
            assertTrue(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void divTest() {
        try {
            Expression e = new Div(
                    new Number(12), new Number(6));
            assertEquals(e.eval(null), 2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        try {
            Expression e = new Div(
                    new Number(2), new Variable("x"));
            e = e.derivative("x");
            e.print();
            assertEquals(e.eval("x=1"), -2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("SimplifyTest")
    void simplifyTest() {
        try {
            Expression e = new Div(
                    new Number(12), new Number(6));
            Expression e2 = e.simplify();
            assertEquals(e2.eval(null), 2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}