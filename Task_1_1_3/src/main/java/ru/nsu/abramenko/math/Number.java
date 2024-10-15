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

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Number num)) {
            return false;
        }
        return num.value == value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
