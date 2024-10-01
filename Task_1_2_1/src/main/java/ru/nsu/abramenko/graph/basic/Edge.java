package ru.nsu.abramenko.graph.basic;

import java.util.Objects;

public class Edge<T> {
    private final T from;
    private final T to;
    private int value;

    public Edge(T from, T to) {
        this.from = from;
        this.to = to;
        this.value = 1;
    }

    public Edge(T from, T to, int value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Edge<?> other)) {
            return false;
        }
        return Objects.equals(from, other.from)
                && Objects.equals(to, other.to)
                && value == other.value;
    }

    @Override
    public String toString() {
        return "(" + from.toString() + " > " + to.toString() +")";
    }
}