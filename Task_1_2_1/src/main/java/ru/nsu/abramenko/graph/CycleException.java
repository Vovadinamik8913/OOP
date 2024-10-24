package ru.nsu.abramenko.graph;

public class CycleException extends RuntimeException {
    public CycleException(String s) {
        super(s);
    }
}
