package ru.nsu.abramenko.math;

/** multiply expression.
 *
 */
public class Mul extends Expression{
    private final Expression left;
    private final Expression right;

    /** a mul b.
     *
     * @param a left
     * @param b right
     */
    public Mul(Expression a, Expression b) {
        this.expression = "(" + a.getExpression() + "*" + b.getExpression() + ")";
        left = a;
        right = b;
    }

    @Override
    public double eval(String s) {
        return left.eval(s) * right.eval(s);
    }

    @Override
    public Expression derivative(String s) {
        return new Add(new Mul(left.derivative(s), right), new Mul(left, right.derivative(s)));
    }

    @Override
    public Expression simplify() {
        if (left.getClass() == Number.class && right.getClass() == Number.class) {
            return new Number(left.eval(null) * right.eval(null));
        } else if ((left.getClass() == Number.class && left.eval(null) == 0)
                || (right.getClass() == Number.class && right.eval(null) == 0)) {
            return new Number(0);
        } else if (left.getClass() == Number.class
                && left.eval(null) == 1) {
            return right.simplify();
        }  else if (right.getClass() == Number.class
                && right.eval(null) == 1) {
            return left.simplify();
        }
        Expression e = new Mul(left.simplify(), right.simplify());
        if (!this.expression.equals(e.expression)) {
            return e.simplify();
        }
        return  this;
    }
}
