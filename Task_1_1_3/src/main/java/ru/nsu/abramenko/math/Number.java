package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

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
    public double eval(@NotNull String s) {
        return value;
    }

    @Override
    public Expression derivative(@NotNull String s) {
        return new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
