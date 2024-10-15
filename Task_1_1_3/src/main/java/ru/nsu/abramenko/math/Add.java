package ru.nsu.abramenko.math;


import org.jetbrains.annotations.NotNull;

/** Add expression.
 *
 */
public class Add extends Expression {

    private final Expression left;
    private final Expression right;

    /** a add b expression.
     *
     * @param a left expr
     * @param b right expr
     */
    public Add(@NotNull Expression a, @NotNull Expression b) {
        left = a;
        right = b;
    }

    @Override
    public double eval(@NotNull String s) {
        return left.eval(s) + right.eval(s);
    }

    @Override
    public Expression derivative(@NotNull String s) {
        return new Add(left.derivative(s), right.derivative(s));
    }

    @Override
    public Expression simplify() {
        if (left instanceof Number a && right instanceof Number b) {
            return new Number(a.getValue() + b.getValue());
        }
        if (left instanceof Number a && a.getValue() == 0) {
            return right.simplify();
        }
        if (right instanceof Number b && b.getValue() == 0) {
            return left.simplify();
        }
        Expression e = new Add(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }

    @Override
    public String toString() {
        return "(" + left + "+" + right + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Add add)) {
            return false;
        }
        return left.equals(add.left) && right.equals(add.right);
    }

    @Override
    public int hashCode() {
        int res = left.hashCode();
        res = 29 * res + right.hashCode();
        return this.toString().hashCode();
    }
}
