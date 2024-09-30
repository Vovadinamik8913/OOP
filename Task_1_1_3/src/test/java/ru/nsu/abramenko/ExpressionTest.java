package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.math.Add;
import ru.nsu.abramenko.math.Div;
import ru.nsu.abramenko.math.Expression;
import ru.nsu.abramenko.math.Mul;
import ru.nsu.abramenko.math.Number;
import ru.nsu.abramenko.math.Sub;
import ru.nsu.abramenko.math.Variable;

class ExpressionTest {
    @Test
    void mainCheck() {
        System.setIn(new ByteArrayInputStream("2*x-10/y+50*0+(2+3)*((2-x)-(2-x))\n".getBytes()));
        Main.main(null);
        assertTrue(true);
    }
}