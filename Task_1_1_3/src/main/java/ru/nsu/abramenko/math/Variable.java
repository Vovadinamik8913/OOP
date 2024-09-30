package ru.nsu.abramenko.math;

/** variable.
 *
 */
public class Variable extends Expression {

    /** new var.
     *
     * @param variable name of var
     */
    public Variable(String variable) {
        this.expression = variable;
    }

    @Override
    public double eval(String s) {
        String val = null;
        assert s != null : "Конфликт имен";

        String[] keys = s.split(";");
        for (String key : keys) {
            int ind = 0;
            while (key.charAt(ind) == ' ') {
                ind++;
            }
            int last = key.indexOf(' ', ind, key.length());
            if (last == -1) {
                last = key.indexOf("=");
            }
            String arg = key.substring(ind, last);
            if (arg.equals(expression)) {
                ind = key.indexOf('=') + 1;
                while (key.charAt(ind) == ' ') {
                    ind++;
                }
                last = key.indexOf(' ', ind, key.length());
                if (last == -1) {
                    last = key.length();
                }
                val = key.substring(ind, last);
                break;
            }
        }

        assert val != null : "Конфликт имен";
        return Double.parseDouble(val);
    }

    @Override
    public Expression derivative(String s) {
        if (s != null && s.equals(expression)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
