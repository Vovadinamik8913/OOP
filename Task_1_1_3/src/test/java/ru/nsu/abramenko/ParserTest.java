package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.math.Expression;

class ParserTest {
    @Test
    void strangeStrTest() {
        String expression = "2+2!@#%^&$##VDFAGSDSAE";
        try {
            Expression e = Parser.parse(expression);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(true);
    }

    @Test
    @DisplayName("BracketsError")
    void bracketsErrorTest() {
        String expression = "2+(3*(50-1)))))";
        try {
            Expression e = Parser.parse(expression);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(true);
    }

    @Test
    @DisplayName("BracketsErr")
    void symbolTest() {
        String expression = "(2+2)(4+4)";
        try {
            Expression e = Parser.parse(expression);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(true);
    }


    @Test
    @DisplayName("Brackets")
    void bracketsTest() {
        String expression = "2+(3*(50-1))";
        try {
            Expression e = Parser.parse(expression);
            e.print();
            Expression de = e.simplify();
            de.print();
            de = e.derivative("x");
            de.print();
            Expression sde = de.simplify();
            sde.print();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(true);
    }


    @Test
    @DisplayName("Normal")
    void normalTest() {
        try {
            Expression e = Parser.parse("2*x-10/y+50*0+(2+3)*((2-x)-(2-x))");
            e.print();
            Expression de = e.simplify();
            de.print();
            de = e.derivative("x");
            de.print();
            Expression sde = de.simplify();
            sde.print();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}