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
    public double eval(@NotNull String s) {
        return left.eval(s) * right.eval(s);
    }

    @Override
    public Expression derivative(@NotNull String s) {
        return new Add(new Mul(left.derivative(s), right), new Mul(left, right.derivative(s)));
    }

    @Override
    public Expression simplify() {
        if (left instanceof  Number && right instanceof Number) {
            return new Number(left.eval("") * right.eval(""));
        } else if ((left instanceof Number && left.eval("") == 0)
                || (right instanceof Number && right.eval("") == 0)) {
            return new Number(0);
        } else if (left instanceof Number
                && left.eval("") == 1) {
            return right.simplify();
        }  else if (right instanceof Number
                && right.eval("") == 1) {
            return left.simplify();
        }
        Expression e = new Mul(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }
}
