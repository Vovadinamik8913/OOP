package ru.nsu.abramenko.math;


/** Add expression.
 *
 */
public class Add extends Expression{
    private final Expression left;
    private final Expression right;

    /** a add b expression.
     * 
     * @param a left expr
     * @param b right expr
     */
    public Add(Expression a, Expression b) {
        this.expression = "(" + a.getExpression() + "+" + b.getExpression() + ")";
        left = a;
        right = b;
    }

    @Override
    public double eval(String s) {
        double res = 0;
        if (left != null) {
            res += left.eval(s);
        }
        if (right != null) {
            res += right.eval(s);
        }
        return res;
    }

    @Override
    public Expression derivative(String s) {
        if (right == null) {
            return  left.derivative(s);
        }
        return new Add(left.derivative(s),right.derivative(s));
    }

    @Override
    public Expression simplify() {
        if (left.getClass() == Number.class && right.getClass() == Number.class) {
            return new Number(left.eval(null) + right.eval(null));
        }
        return new Add(left.simplify(), right.simplify());
    }
}
