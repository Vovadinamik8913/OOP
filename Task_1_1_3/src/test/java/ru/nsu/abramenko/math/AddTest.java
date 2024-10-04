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
        try {
            Expression de = e.derivative("x");
            de.print();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            assertEquals(23.0, e.eval("x = 10; y = 13"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void addTest() {
        Expression e = new Add(
                new Number(12), new Number(6));
        try {
            assertEquals(e.eval(null), 18);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("DerivativeTest")
    void derivativeTest() {
        Expression e = new Add(
                new Number(2), new Variable("x"));
        try {
            e = e.derivative("x");
            e.print();
            assertEquals(e.eval("x=1"), 1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    @DisplayName("SimplifyTest")
    void simplifyTest() {
        Expression e = new Add(
                new Mul(new Number(2), new Number(6)),
                new Number(6));
        try {
            Expression e2 = e.simplify();
            assertEquals(e2.eval(null), 18);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}