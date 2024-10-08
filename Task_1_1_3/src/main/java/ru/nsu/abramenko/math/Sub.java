package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** sub expression.
 *
 */
public class Sub extends Expression {

    /** a sub b.
     *
     * @param a left
     * @param b right
     */
    public Sub(@NotNull Expression a, @NotNull Expression b) {
        super(a, b);
        this.expression = "(" + a + "-" + b + ")";
    }

    @Override
    public double eval(String s) throws Exception {
        return left.eval(s) - right.eval(s);
    }

    @Override
    public Expression derivative(String s) throws Exception {
        return new Sub(left.derivative(s), right.derivative(s));
    }

    @Override
    public Expression simplify() throws Exception {
        if (left instanceof Number && right instanceof Number) {
            return new Number(left.eval(null) - right.eval(null));
        } else if (left.equals(right)) {
            return new Number(0);
        }
        if (right instanceof Number && right.eval(null) == 0) {
            return left.simplify();
        }
        Expression e = new Sub(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }
}
