package ru.nsu.abramenko.math;

public class Variable extends Expression {

    public Variable(String variable) {
        this.expression = variable;
    }

    @Override
    public double eval(String s) {
        String val = "0";
        if (s != null) {
            String[] keys = s.split(";");
            for (String key : keys) {
                int ind = 0;
                while (key.charAt(ind) == ' ') {
                    ind++;
                }
                int last = key.indexOf(' ', ind, key.length());
                String arg = key.substring(ind, last);
                ind = key.indexOf('=') + 1;
                while (key.charAt(ind) == ' ') {
                    ind++;
                }
                last = key.indexOf(' ', ind, key.length());
                if (last == -1) {
                    last = key.length();
                }
                String v = key.substring(ind, last);
                if (arg.equals(expression)) {
                    val = v;
                    break;
                }
            }
        }
        return Double.parseDouble(val);
    }

    @Override
    public Expression derivative(String s) {
        if (s.equals(expression)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
