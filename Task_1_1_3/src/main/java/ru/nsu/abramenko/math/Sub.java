package ru.nsu.abramenko.math;

public class Sub extends Expression{

    private final Expression left;
    private final Expression right;

    public Sub(Expression a, Expression b) {
        if (a.getClass() == Number.class && b.getClass() == Number.class) {
            left = new Number(a.eval(null) - b.eval(null));
            right = null;
            this.expression = left.getExpression();
        } else if (a.getExpression().equals(b.getExpression())) {
            left = new Number(0);
            right = null;
            this.expression = left.getExpression();
        } else {
            this.expression = "(" + a.getExpression() + "-" + b.getExpression() + ")";
            left = a;
            right = b;
        }
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
}
