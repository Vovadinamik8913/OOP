package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/** div expression.
 *
 */
public class Div extends Expression {

    private final Expression left;
    private final Expression right;

    /** a div b expression.
     *
     * @param a left
     * @param b right, must not equal 0
     */
    public Div(@NotNull Expression a, @NotNull Expression b) throws ArithmeticException {
        left = a;
        right = b;
        Expression tst = new Number(0);
        if (b instanceof Number && b.equals(tst)) {
            throw new ArithmeticException("Division by Zero");
        }
    }

    @Override
    public double eval(@NotNull String s) throws ArithmeticException {
        double res = right.eval(s);
        if (res == 0.d) {
            throw new ArithmeticException("Division by Zero");
        }
        return left.eval(s) / res;
    }

    @Override
    public Expression derivative(@NotNull String s) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(s), right), new Mul(left, right.derivative(s))),
                new Mul(right, right));
    }

    @Override
    public Expression simplify() {
        if (left instanceof Number a && right instanceof Number b) {
            return new Number(a.getValue() / b.getValue());
        } else if (left instanceof Number a && a.getValue() == 0) {
            return new Number(0);
        }
        Expression e = new Div(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }

    @Override
    public String toString() {
        return "(" + left + "/" + right + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Div div)) {
            return false;
        }
        return left.equals(div.left) && right.equals(div.right);
    }

    @Override
    public int hashCode() {
        int res = left.hashCode();
        res = 29 * res + right.hashCode();
        return this.toString().hashCode();
    }
}

