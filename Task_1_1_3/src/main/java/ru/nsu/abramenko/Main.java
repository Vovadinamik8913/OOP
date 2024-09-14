package ru.nsu.abramenko;

import ru.nsu.abramenko.math.Add;
import ru.nsu.abramenko.math.Div;
import ru.nsu.abramenko.math.Mul;
import ru.nsu.abramenko.math.Number;
import ru.nsu.abramenko.math.Expression;
import ru.nsu.abramenko.math.Sub;
import ru.nsu.abramenko.math.Variable;

/** main.
 *
 */
public class Main {

    /** main.
     *
     * @param args string
     */
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        double result = e.eval("x = 10; y = 13");
        System.out.println(result);

        e = new Sub(new Add(new Number(2),
                new Variable("x")), new Add(new Variable("x"), new Number(2)));
        de = e.simplify();
        e.print();
        de.print();
    }
}