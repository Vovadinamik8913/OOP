package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** sub expression.
 *
 */
public class Sub extends Expression {

    private final Expression left;
    private final Expression right;

    /** a sub b.
     *
     * @param a left
     * @param b right
     */
    public Sub(@NotNull Expression a, @NotNull Expression b) {
        left = a;
        right = b;
    }

    @Override
    public double eval(@NotNull String s) {
        return left.eval(s) - right.eval(s);
    }

    @Override
    public Expression derivative(@NotNull String s) {
        return new Sub(left.derivative(s), right.derivative(s));
    }

    @Override
    public Expression simplify() {
        if (left instanceof Number && right instanceof Number) {
            return new Number(left.eval("") - right.eval(""));
        } else if (left.equals(right)) {
            return new Number(0);
        }
        if (right instanceof Number && right.eval("") == 0) {
            return left.simplify();
        }
        Expression e = new Sub(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }

    @Override
    public String toString() {
        return "(" + left + "-" + right + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Sub sub)) {
            return false;
        }
        return left.equals(sub.left) && right.equals(sub.right);
    }

    @Override
    public int hashCode() {
        int res = left.hashCode();
        res = 29 * res + right.hashCode();
        return this.toString().hashCode();
    }
}
