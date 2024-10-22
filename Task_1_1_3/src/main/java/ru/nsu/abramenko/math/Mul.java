package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** multiply expression.
 *
 */
public class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    /** a mul b.
     *
     * @param a left
     * @param b right
     */
    public Mul(@NotNull Expression a, @NotNull Expression b) {
        left = a;
        right = b;
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
        if (left instanceof  Number a && right instanceof Number b) {
            return new Number(a.getValue() * b.getValue());
        } else if ((left instanceof Number a && a.getValue() == 0)
                || (right instanceof Number b && b.getValue() == 0)) {
            return new Number(0);
        } else if (left instanceof Number a
                && a.getValue() == 1) {
            return right.simplify();
        }  else if (right instanceof Number b
                && b.getValue() == 1) {
            return left.simplify();
        }
        Expression e = new Mul(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }

    @Override
    public String toString() {
        return "(" + left + "*" + right + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Mul multi)) {
            return false;
        }
        return left.equals(multi.left) && right.equals(multi.right);
    }

    @Override
    public int hashCode() {
        int res = left.hashCode();
        res = 29 * res + right.hashCode();
        return this.toString().hashCode();
    }
}
