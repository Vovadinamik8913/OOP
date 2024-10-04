package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VariableTest {
    @Test
    void variableTest() {
        Expression e = new Variable("x");
        try {
            assertEquals(e.eval("x=1"), 1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Variable("x");
        try {
            Expression dx = e.derivative("x");
            Expression dy = e.derivative("y");
            assertEquals(dy.eval("x=1") + dx.eval("x=1"), 1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}