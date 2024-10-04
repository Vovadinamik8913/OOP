package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** variable.
 *
 */
public class Variable extends Expression {

    /** new var.
     *
     * @param variable name of var
     */
    public Variable(@NotNull String variable) {
        this.expression = variable;
    }


    private String[] parseVariable(String variable) throws Exception {
        if (variable == null) {
            throw  new NullPointerException("Конфликт имен: variable = null");
        }
        if (!variable.contains("=")) {
            throw  new Exception("Конфликт имен: there is no =");
        }
        variable = variable.strip();

        String tmp = variable.substring(0, variable.indexOf("=")).strip();
        if (tmp.contains(" ")) {
            throw  new Exception("Конфликт имен: problems in arg");
        }

        String[] res = new String[2];
        res[0] = tmp;

        tmp = variable.substring(variable.indexOf("=") + 1).strip();
        if (tmp.contains(" ")) {
            throw  new Exception("Конфликт имен: problems in val");
        }
        res[1] = tmp;

        return res;
    }

    @Override
    public double eval(String s) throws Exception{
        String val = null;
        if (s == null)  {
            throw  new NullPointerException("Конфликт имен: variable = null");
        }

        String[] keys = s.split(";");
        for (String key : keys) {
            String[] kv = parseVariable(key);
            if (expression.equals(kv[0])) {
                val = kv[1];
                break;
            }
        }

        if (val == null) {
            throw new Exception("Конфликт имен");
        }
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
