package ru.nsu.abramenko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import ru.nsu.abramenko.math.Add;
import ru.nsu.abramenko.math.Div;
import ru.nsu.abramenko.math.Expression;
import ru.nsu.abramenko.math.Mul;
import ru.nsu.abramenko.math.Number;
import ru.nsu.abramenko.math.Sub;
import ru.nsu.abramenko.math.Variable;


/** Parser.
 * Converts string to expression
 *
 */
public class Parser {

    private static boolean containsNumbers(String s) {
        Pattern digit = Pattern.compile("[0-9]");
        Matcher hasDigit = digit.matcher(s);
        return hasDigit.find();
    }

    private static boolean containsError(String s) {
        Pattern special = Pattern.compile("[!@#$%&_=|<>?{}\\[\\]~\"';:]");
        Matcher hasSpecial = special.matcher(s);

        Pattern operations = Pattern.compile("[+\\-*/]");
        Matcher hasOperations = operations.matcher(s);
        return hasSpecial.find() || !hasOperations.find();
    }

    private static Expression operator(
            char operation, Expression a, Expression b)
            throws ArithmeticException, IllegalArgumentException {
        return switch (operation) {
            case '-' -> new Sub(a, b);
            case '+' -> new Add(a, b);
            case '/' -> new Div(a, b);
            case '*' -> new Mul(a, b);
            default -> throw new IllegalArgumentException("Неправильный ввод");
        };
    }

    private static String readToken(String text, int[] pos) {
        StringBuilder token = new StringBuilder();
        char sym = text.charAt(pos[0]);
        while (sym == ' ' || sym == '\n'
                || sym == '\t' || sym == '\r') {
            sym = text.charAt(++pos[0]);
        }

        if (sym == '\0') {
            token = new StringBuilder("\0");
            return token.toString();
        }

        Pattern operatorPattern = Pattern.compile("[+\\-*/()]");
        Matcher operatorMatcher =
                operatorPattern.matcher(String.valueOf(text.charAt(pos[0])));
        if (operatorMatcher.matches()) {
            token.append(text.charAt(pos[0]++));
            return token.toString();
        }

        int left = pos[0];
        Pattern nonDigitPattern = Pattern.compile("[^+\\-*/()\\s]");
        while (true) {
            Matcher nonDidgitMatcher =
                    nonDigitPattern.matcher(String.valueOf(text.charAt(pos[0])));
            if (!nonDidgitMatcher.matches()
                    || text.charAt(pos[0]) == '\0') {
                break;
            }
            pos[0]++;
        }

        token.append(text, left, pos[0]);
        token.append('\0');
        return token.toString();
    }

    private static String peekToken(String str, int[] pos) {
        int old = pos[0];
        String token = readToken(str, pos);
        pos[0] = old;
        return token;
    }

    private static Expression parseExpr(String str, int[] pos) throws IllegalArgumentException {
        Expression res = parseManom(str, pos);
        String s = peekToken(str, pos);
        String operation = "";
        while (s.charAt(0) == '+' || s.charAt(0) == '-') {
            operation = readToken(str, pos);
            Expression add = parseManom(str, pos);
            res = operator(operation.charAt(0), res, add);
            s = peekToken(str, pos);
        }
        if (pos[0] < str.length() && str.charAt(pos[0]) == '(') {
            throw  new IllegalArgumentException("Неправильный ввод");
        }
        return res;
    }

    private static Expression parseAtom(String str, int[] pos) {
        String s = peekToken(str, pos);
        Expression res;
        if (s.charAt(0) == '(') {
            s = readToken(str, pos);
            res = parseExpr(str, pos);
            s = readToken(str, pos);
        } else {
            s = readToken(str, pos);
            Expression add = null;
            int f = 1;
            if (s.charAt(0) == '-') {
                f *= -1;
                s = peekToken(str, pos);
                if (s.charAt(0) == '-' || s.charAt(0) == '+'
                        || s.charAt(0) == '/' || s.charAt(0) == '*'
                        || s.charAt(0) == '(') {
                    add = parseExpr(str, pos);
                } else {
                    s = readToken(str, pos);
                    if (containsNumbers(s)) {
                        add = new Number(Double.parseDouble(s) * f);
                    } else {
                        add = new Variable(s.substring(0, s.length() - 1));
                    }
                }
                s = "";
            }
            if (!s.isEmpty()) {
                if (containsNumbers(s)) {
                    add = new Number(Double.parseDouble(s) * f);
                } else {
                    add = new Variable(s.substring(0, s.length() - 1));
                }
            }
            res = add;
        }
        return res;
    }

    private static Expression parseManom(String str, int[] pos) throws IllegalArgumentException {
        Expression res = parseAtom(str, pos);
        String s = peekToken(str, pos);
        String operation = "";
        while (s.charAt(0) == '*' || s.charAt(0) == '/') {
            operation = readToken(str, pos);
            Expression add = parseAtom(str, pos);
            res = operator(operation.charAt(0), res, add);
            s = peekToken(str, pos);
        }
        if (pos[0] < str.length() && str.charAt(pos[0]) == '(') {
            throw  new IllegalArgumentException("Неправильный ввод");
        }
        return res;
    }


    /** converts string to expression.
     *
     * @param str string exp
     * @return expression
     */
    public static Expression parse(@NotNull String str)
            throws IllegalArgumentException, ArithmeticException {
        if (containsError(str)) {
            throw new IllegalArgumentException("Неправильный ввод");
        }
        long countEnters = str.chars().filter(ch -> ch == '(').count();
        long countOuts = str.chars().filter(ch -> ch == ')').count();
        if (countEnters != countOuts) {
            throw new IllegalArgumentException("Неправильный ввод");
        }
        str += '\0';
        return parseExpr(str, new int[]{0});
    }
}
