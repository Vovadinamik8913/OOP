package ru.nsu.abramenko.math;

public class Mul extends Expression{
    private final Expression left;
    private final Expression right;

    public Mul(Expression a, Expression b) {
        if (a.getClass() == Number.class && b.getClass() == Number.class) {
            left = new Number(a.eval(null) * b.eval(null));
            right = null;
            this.expression = left.getExpression();
        } else if ((a.getClass() == Number.class && a.eval(null) == 0)
                || (b.getClass() == Number.class && b.eval(null) == 0)) {
            left = new Number(0);
            right = null;
            this.expression = left.getExpression();
        } else if (a.getClass() == Number.class
                && a.eval(null) == 1) {
            left = b;
            right = null;
            this.expression = left.getExpression();
        }  else if (b.getClass() == Number.class
                && b.eval(null) == 1) {
            left = a;
            right = null;
            this.expression = left.getExpression();
        } else {
            this.expression = "(" + a.getExpression() + "*" + b.getExpression() + ")";
            left = a;
            right = b;
        }
    }

    @Override
    public double eval(String s) {
        if (right == null) {
            return  left.eval(s);
        }
        return left.eval(s) * right.eval(s);
    }

    @Override
    public Expression derivative(String s) {
        if (right == null) {
            return  left.derivative(s);
        }
        return new Add(new Mul(left.derivative(s), right), new Mul(left, right.derivative(s)));
    }
}
