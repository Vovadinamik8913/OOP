package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/**abstract expression.
 *
 */
public abstract class Expression {
    protected String expression;
    protected Expression left;
    protected Expression right;

    /** empty constructor.
     * 
     */
    public Expression() {}
    
    /** expression a b.
     *
     *
     * @param a left
     * @param b right
     */
    public Expression(@NotNull Expression a, @NotNull Expression b) {
        left = a;
        right = b;
    }
    
    /** evaluate.
     * may throw assert fail with variable
     *
     * @param s arg equals something
     * @return number
     */
    public abstract double eval(@NotNull String s);

    /** derivative.
     *
     * @param s for which arg
     * @return new expression
     */
    public abstract Expression derivative(@NotNull String s);

    /** simplify original.
     *
     * @return new expression
     */
    public abstract Expression simplify();

    /** print.
     *
     */
    public void print() {
        System.out.println(this);
    }


    @Override
    public String toString() {
        return expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Expression other)) {
            return false;
        }
        return this.expression.equals(other.expression);
    }

    @Override
    public int hashCode() {
        return expression.hashCode();
    }
}