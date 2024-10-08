package ru.nsu.abramenko.math;


import org.jetbrains.annotations.NotNull;

/** Add expression.
 *
 */
public class Add extends Expression {

    /** a add b expression.
     *
     * @param a left expr
     * @param b right expr
     */
    public Add(@NotNull Expression a, @NotNull Expression b) {
        super(a, b);
        this.expression = "(" + a + "+" + b + ")";
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
        if (left instanceof Number && right instanceof Number) {
            return new Number(left.eval("") + right.eval(""));
        }
        if (left instanceof Number && left.eval("") == 0) {
            return right.simplify();
        }
        if (right instanceof Number && right.eval("") == 0) {
            return left.simplify();
        }
        Expression e = new Add(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }
}
