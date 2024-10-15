package ru.nsu.abramenko.math;

import org.jetbrains.annotations.NotNull;

/**abstract expression.
 *
 */
public abstract class Expression {
    
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
}
