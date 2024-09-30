package ru.nsu.abramenko.math;

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
     * @param a left
     * @param b right
     */
    public Expression(Expression a, Expression b) {
        left = a;
        right = b;
    }
    
    /** evaluate.
     * may throw assert fail with variable
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
     * @return expression
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
