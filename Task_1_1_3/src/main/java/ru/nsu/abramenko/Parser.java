package ru.nsu.abramenko;

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
        return s.charAt(0) == '1' || s.charAt(0) == '2'
                || s.charAt(0) == '3' || s.charAt(0) == '4'
                || s.charAt(0) == '5' || s.charAt(0) == '6'
                || s.charAt(0) == '7' || s.charAt(0) == '8'
                || s.charAt(0) == '9' || s.charAt(0) == '0';
    }

    private static Expression oper(char operation, Expression a, Expression b) {
        if (operation == '-') {
            return new Sub(a, b);
        }
        if (operation == '+') {
            return new Add(a, b);
        }
        if (operation == '*') {
            return new Mul(a, b);
        }
        if (operation == '/') {
            return new Div(a, b);
        }
        return null;
    }

    private static String readToken(String text, int[] pos) {
        String token = "";
        while (text.charAt(pos[0]) == ' ' || text.charAt(pos[0]) == '\n'
                || text.charAt(pos[0]) == '\t' || text.charAt(pos[0]) == '\r') {
            pos[0]++;
        }
        if (text.charAt(pos[0]) == '\0') {
            token = "\0";
            return token;
        }
        if (text.charAt(pos[0]) == '+' || text.charAt(pos[0]) == '-'
                || text.charAt(pos[0]) == '*' || text.charAt(pos[0]) == '/'
                || text.charAt(pos[0]) == '(' || text.charAt(pos[0]) == ')') {
            token += text.charAt(pos[0]++);
            return token;
        }
        int left = pos[0];
        while (text.charAt(pos[0]) != '+' && text.charAt(pos[0]) != '-'
                && text.charAt(pos[0]) != '*' && text.charAt(pos[0]) != '/'
                && text.charAt(pos[0]) != '(' && text.charAt(pos[0]) != ')'
                && text.charAt(pos[0]) != ' ' && text.charAt(pos[0]) != '\0') {
            pos[0]++;
        }
        int size = pos[0] - left;
        for (int i = 0; i < size; i++) {
            token += text.charAt(left + i);
        }
        token += '\0';
        return token;
    }

    private static String peekToken(String str, int[] pos) {
        int old = pos[0];
        String token = readToken(str, pos);
        pos[0] = old;
        return token;
    }

    /** converts string to expression.
     *
     * @param str string exp
     * @param pos begin
     * @return expression
     */
    public static Expression parseExpr(String str, int[] pos) {
        Expression res = parseManom(str, pos);
        String s = peekToken(str, pos);
        String operation = "";
        while (s.charAt(0) == '+' || s.charAt(0) == '-') {
            operation = readToken(str, pos);
            Expression add = parseManom(str, pos);
            res = oper(operation.charAt(0), res, add);
            s = peekToken(str, pos);
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

    private static Expression parseManom(String str, int[] pos) {
        Expression res = parseAtom(str, pos);
        String s = peekToken(str, pos);
        String operation = "";
        while (s.charAt(0) == '*' || s.charAt(0) == '/') {
            operation = readToken(str, pos);
            Expression add = parseAtom(str, pos);
            res = oper(operation.charAt(0), res, add);
            s = peekToken(str, pos);
        }
        return res;
    }

    /*public static String replaceMinusWithNegative(String str) {
        char[] com = new char[100001];
        int len = str.length();
        int j = 0;

        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == '-') {
                com[j++] = '-';
                com[j++] = '1';
                com[j++] = '*';
            } else {
                com[j++] = str.charAt(i);
            }
        }

        com[j] = '\0';
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < j; i++) {
            res.append(com[i]);
        }
        return res.toString();
    }*/
}
