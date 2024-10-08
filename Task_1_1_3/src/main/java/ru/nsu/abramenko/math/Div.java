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
        this.expression = "(" + a + "/" + b + ")";
        if (b.expression.equals("0.0")) {
            throw new Exception("Division by Zero");
        }
    }

    @Override
    public double eval(String s) throws Exception {
        double res = right.eval(s);
        return left.eval(s) / res;
    }

    @Override
    public Expression derivative(String s) throws Exception {
        return new Div(
                new Sub(
                        new Mul(left.derivative(s), right), new Mul(left, right.derivative(s))),
                new Mul(right, right));
    }

    @Override
    public Expression simplify() throws Exception {
        if (left instanceof Number && right instanceof Number) {
            return new Number(left.eval(null) / right.eval(null));
        } else if (left instanceof Number && left.eval(null) == 0) {
            return new Number(0);
        }
        Expression e = new Div(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }
}

