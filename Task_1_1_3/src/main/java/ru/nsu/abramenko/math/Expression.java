package ru.nsu.abramenko.math;

public abstract class Expression {
    protected String expression;
    public abstract double eval(String s);
    public abstract Expression derivative(String s);
    public abstract Expression simplify();
    public String getExpression() {
        return expression;
    }
    public void print() {
        System.out.println(expression);
    }
}
