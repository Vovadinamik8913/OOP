package ru.nsu.abramenko.math;

public class Sub extends Expression{

    private final Expression left;
    private final Expression right;

    public Sub(Expression a, Expression b) {
        this.expression = "(" + a.getExpression() + "-" + b.getExpression() + ")";
        left = a;
        right = b;
    }

    @Override
    public double eval(String s) {
        if (right == null) {
            return  left.eval(s);
        }
        return left.eval(s) - right.eval(s);
    }

    @Override
    public Expression derivative(String s) {
        if (right == null) {
            return  left.derivative(s);
        }
        return new Sub(left.derivative(s), right.derivative(s));
    }

    @Override
    public Expression simplify() {
        if (left.getClass() == Number.class && right.getClass() == Number.class) {
            return new Number(left.eval(null) - right.eval(null));
        } else if (left.getExpression().equals(right.getExpression())) {
            return new Number(0);
        }
        return  new Sub(left.simplify(), right.simplify());
    }
}
