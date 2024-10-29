package ru.nsu.abramenko.graph;

/**Exception for cycle.
 *
 */
public class CycleException extends RuntimeException {
    public CycleException(String s) {
        super(s);
    }
}
