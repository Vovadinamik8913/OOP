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
    public Div(@NotNull Expression a, @NotNull Expression b) throws ArithmeticException {
        super(a, b);
        this.expression = "(" + a + "/" + b + ")";
        Expression tst = new Number(0);
        if (b instanceof Number && b.equals(tst)) {
            throw new ArithmeticException("Division by Zero");
        }
    }

    @Override
    public double eval(String s) throws ArithmeticException {
        double res = right.eval(s);
        if (res == 0.d) {
            throw new ArithmeticException("Division by Zero");
        }
        return left.eval(s) / res;
    }

    @Override
    public Expression derivative(String s) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(s), right), new Mul(left, right.derivative(s))),
                new Mul(right, right));
    }

    @Override
    public Expression simplify() {
        if (left instanceof Number && right instanceof Number) {
            return new Number(left.eval("") / right.eval(""));
        } else if (left instanceof Number && left.eval("") == 0) {
            return new Number(0);
        }
        Expression e = new Div(left.simplify(), right.simplify());
        if (!this.equals(e)) {
            return e.simplify();
        }
        return  this;
    }
}

