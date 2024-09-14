package ru.nsu.abramenko.math;

/** div expression.
 *
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /** a div b expression.
     *
     * @param a left
     * @param b right
     */
    public Div(Expression a, Expression b) {
        this.expression = "(" + a.getExpression() + "/" + b.getExpression() + ")";
        left = a;
        right = b;
    }

    @Override
    public double eval(String s) {
        return left.eval(s) / right.eval(s);
    }

    @Override
    public Expression derivative(String s) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(s), right), new Mul(left, right.derivative(s))),
                new Mul(right, right));
    }

    @Override
    public Expression simplify() {
        if (left.getClass() == Number.class && right.getClass() == Number.class) {
            return new Number(left.eval(null) / right.eval(null));
        } else if (left.getClass() == Number.class && left.eval(null) == 0) {
            return new Number(0);
        }
        Expression e = new Div(left.simplify(), right.simplify());
        if (!this.expression.equals(e.expression)) {
            return e.simplify();
        }
        return  this;
    }
}

