package ru.nsu.abramenko;

import ru.nsu.abramenko.math.Add;
import ru.nsu.abramenko.math.Div;
import ru.nsu.abramenko.math.Mul;
import ru.nsu.abramenko.math.Number;
import ru.nsu.abramenko.math.Expression;
import ru.nsu.abramenko.math.Sub;
import ru.nsu.abramenko.math.Variable;

import java.util.Scanner;

/** main.
 *
 */
public class Main {

    /** main.
     *
     * @param args string
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        expression += '\0';
        Expression e = Parser.parseExpr(expression, new int[] {0});
        e.print();
        Expression de = e.simplify();
        de.print();
    }
}