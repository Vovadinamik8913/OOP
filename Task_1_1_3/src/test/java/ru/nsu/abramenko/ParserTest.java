package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void strangeStrTest() {
        try {
            System.setIn(new ByteArrayInputStream("2+2!@#%^&$##VDFAGSDSAE\n".getBytes()));
            Main.main(null);
            assertTrue(true);
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("BracketsError")
    void bracketsErrorTest() {
        try {
            System.setIn(new ByteArrayInputStream("2+(3*(50-1)))))\n".getBytes()));
            Main.main(null);
            assertTrue(true);
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("BracketsError")
    void symbolTest() {
        try {
            System.setIn(new ByteArrayInputStream("(2+2)(4+4)\n".getBytes()));
            Main.main(null);
            assertTrue(true);
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName("Brackets")
    void bracketsTest() {
        try {
            System.setIn(new ByteArrayInputStream("2+(3*(50-1))\n".getBytes()));
            Main.main(null);
            assertTrue(true);
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

}