package ru.nsu.abramenko.math;

/**abstract expression.
 *
 */
public abstract class Expression {
    protected String expression;

    /** evluate.
     *
     * @param s arg equals something
     * @return number
     */
    public abstract double eval(String s);

    /** derivative.
     *
     * @param s for which arg
     * @return new expression
     */
    public abstract Expression derivative(String s);

    /** simplify original.
     *
     * @return new expression
     */
    public abstract Expression simplify();

    /** getter of expression.
     *
     * @return expressiont
     */
    public String getExpression() {
        return expression;
    }

    /** print.
     *
     */
    public void print() {
        System.out.println(expression);
    }
}
