package ru.nsu.abramenko;


import java.util.Scanner;
import ru.nsu.abramenko.math.Expression;

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
        Expression e = Parser.parse(expression, new int[] {0});
        e.print();
        Expression de = e.simplify();
        de.print();
        de = e.derivative("x");
        de.print();
        Expression sde = de.simplify();
        sde.print();
    }
}