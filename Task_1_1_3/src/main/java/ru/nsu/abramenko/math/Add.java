package ru.nsu.abramenko.math;


/** Add expression.
 *
 */
public class Add extends Expression {

    /** a add b expression.
     *
     * @param a left expr
     * @param b right expr
     */
    public Add(Expression a, Expression b) {
        this.expression = "(" + a.getExpression() + "+" + b.getExpression() + ")";
        left = a;
        right = b;
    }

    @Override
    public double eval(String s) {
        return left.eval(s) + right.eval(s);
    }

    @Override
    public Expression derivative(String s) {
        return new Add(left.derivative(s), right.derivative(s));
    }

    @Override
    public Expression simplify() {
        if (left.getClass() == Number.class && right.getClass() == Number.class) {
            return new Number(left.eval(null) + right.eval(null));
        }
        if (left.getClass() == Number.class && left.eval(null) == 0) {
            return right.simplify();
        }
        if (right.getClass() == Number.class && right.eval(null) == 0) {
            return left.simplify();
        }
        Expression e = new Add(left.simplify(), right.simplify());
        if (!this.expression.equals(e.expression)) {
            return e.simplify();
        }
        return  this;
    }
}