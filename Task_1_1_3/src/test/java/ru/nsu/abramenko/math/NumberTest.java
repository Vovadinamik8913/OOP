package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberTest {
    @Test
    void numberTest() {
        Expression e = new Number(123);
        try {
            assertEquals(e.eval(null), 123);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Number(123);
        try {
            e = e.derivative("x");
            assertEquals(e.eval(null), 0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("SimplifyTest")
    void simplifyTest() {
        Expression e = new Number(123);
        try {
            Expression e2 = e.simplify();
            assertEquals(e.eval(null), e2.eval(null));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}