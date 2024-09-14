package ru.nsu.abramenko.math;

/** number.
 *
 */
public class Number extends Expression {
    private final double value;

    /** number.
     *
     * @param number number
     */
    public Number(double number) {
        this.expression = Double.toString(number);
        value = number;
    }

    @Override
    public double eval(String s) {
        return value;
    }

    @Override
    public Expression derivative(String s) {
        return new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
