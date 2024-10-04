package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** div expression.
 *
 */
public class Div extends Expression {

    /** a div b expression.
     *
     * @param a left
     * @param b right, must not equal 0
     */
    public Div(@NotNull Expression a, @NotNull Expression b) throws Exception {
        super(a, b);
        this.expression = "(" + a.getExpression() + "/" + b.getExpression() + ")";
        if (b.getExpression().equals("0.0")) {
            throw new Exception("Division by Zero");
        }
    }

    @Override
    public double eval(String s) throws Exception {
        return left.eval(s) / right.eval(s);
    }

    @Override
    public Expression derivative(String s) throws Exception{
        return new Div(
                new Sub(
                        new Mul(left.derivative(s), right), new Mul(left, right.derivative(s))),
                new Mul(right, right));
    }

    @Override
    public Expression simplify() throws Exception {
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

