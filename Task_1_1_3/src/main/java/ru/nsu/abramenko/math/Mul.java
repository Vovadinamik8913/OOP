package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** multiply expression.
 *
 */
public class Mul extends Expression {

    /** a mul b.
     *
     * @param a left
     * @param b right
     */
    public Mul(@NotNull Expression a, @NotNull Expression b) {
        super(a, b);
        this.expression = "(" + a + "*" + b + ")";
    }

    @Override
    public double eval(String s) throws Exception {
        return left.eval(s) * right.eval(s);
    }

    @Override
    public Expression derivative(String s) throws Exception {
        return new Add(new Mul(left.derivative(s), right), new Mul(left, right.derivative(s)));
    }

    @Override
    public Expression simplify() throws Exception {
        if (left instanceof  Number && right instanceof Number) {
            return new Number(left.eval(null) * right.eval(null));
        } else if ((left instanceof Number && left.eval(null) == 0)
                || (right instanceof Number && right.eval(null) == 0)) {
            return new Number(0);
        } else if (left instanceof Number
                && left.eval(null) == 1) {
            return right.simplify();
        }  else if (right instanceof Number
                && right.eval(null) == 1) {
            return left.simplify();
        }
        Expression e = new Mul(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }
}
