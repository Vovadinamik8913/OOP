package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void strangeStrTest() {
        System.setIn(new ByteArrayInputStream("2+2!@#%^&$##VDFAGSDSAE\n".getBytes()));
        Main.main(null);
        assertTrue(true);
    }

    @Test
    @DisplayName("BracketsError")
    void bracketsErrorTest() {
        System.setIn(new ByteArrayInputStream("2+(3*(50-1)))))\n".getBytes()));
        Main.main(null);
        assertTrue(true);
    }

    @Test
    @DisplayName("BracketsErr")
    void symbolTest() {
        System.setIn(new ByteArrayInputStream("(2+2)(4+4)\n".getBytes()));
        Main.main(null);
        assertTrue(true);
    }


    @Test
    @DisplayName("Brackets")
    void bracketsTest() {
        System.setIn(new ByteArrayInputStream("2+(3*(50-1))\n".getBytes()));
        Main.main(null);
        assertTrue(true);
    }

}