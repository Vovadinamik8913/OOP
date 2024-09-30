package ru.nsu.abramenko.math;

/** sub expression.
 *
 */
public class Sub extends Expression {

    /** a sub b.
     *
     * @param a left
     * @param b right
     */
    public Sub(Expression a, Expression b) {
        super(a, b);
        this.expression = "(" + a.getExpression() + "-" + b.getExpression() + ")";
    }

    @Override
    public double eval(String s) {
        return left.eval(s) - right.eval(s);
    }

    @Override
    public Expression derivative(String s) {
        return new Sub(left.derivative(s), right.derivative(s));
    }

    @Override
    public Expression simplify() {
        if (left.getClass() == Number.class && right.getClass() == Number.class) {
            return new Number(left.eval(null) - right.eval(null));
        } else if (left.getExpression().equals(right.getExpression())) {
            return new Number(0);
        }
        if (right.getClass() == Number.class && right.eval(null) == 0) {
            return left.simplify();
        }
        Expression e = new Sub(left.simplify(), right.simplify());
        if (!this.expression.equals(e.expression)) {
            return e.simplify();
        }
        return  this;
    }
}
