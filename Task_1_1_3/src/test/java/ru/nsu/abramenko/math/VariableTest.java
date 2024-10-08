package ru.nsu.abramenko.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.Parser;

class VariableTest {
    @Test
    void variableTest() {
        Expression e = new Variable("x");
        assertEquals(e.eval("x=1"), 1);
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Variable("x");
        Expression dx = e.derivative("x");
        Expression dy = e.derivative("y");
        assertEquals(dy.eval("x=1") + dx.eval("x=1"), 1);
    }

    @Test
    @DisplayName("EqualsErrorTest")
    void equalsErrorTest() {
        Expression e = new Variable("x");
        assertThrows(RuntimeException.class, () -> {
            e.eval("x 1");
        });
    }


    @Test
    @DisplayName("SpaceErrorTest")
    void spaceArgErrorTest() {
        Expression e = new Variable("x");
        assertThrows(RuntimeException.class, () -> {
            e.eval("x 1=10");
        });
    }

    @Test
    @DisplayName("SpaceErrorTest")
    void spaceValErrorTest() {
        Expression e = new Variable("x");
        assertThrows(RuntimeException.class, () -> {
            e.eval("x=10000 10");
        });
    }
}