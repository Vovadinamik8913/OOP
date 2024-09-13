package ru.nsu.abramenko.math;

public class Add extends Expression{
    private final Expression left;
    private final Expression right;

    public Add(Expression a, Expression b) {
        if (a.getClass() == Number.class && b.getClass() == Number.class) {
            left = new Number(a.eval(null) + b.eval(null));
            right = null;
            this.expression = left.getExpression();
        } else {
            this.expression = "(" + a.getExpression() + "+" + b.getExpression() + ")";
            left = a;
            right = b;
        }
    }

    @Override
    public double eval(String s) {
        double res = 0;
        if (left != null) {
            res += left.eval(s);
        }
        if (right != null) {
            res += right.eval(s);
        }
        return res;
    }

    @Override
    public Expression derivative(String s) {
        if (right == null) {
            return  left.derivative(s);
        }
        return new Add(left.derivative(s),right.derivative(s));
    }
}
