package ru.nsu.abramenko.graph.basic;

import java.util.Objects;

public record Vertex<T>(T name) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vertex<?> other)) {
            return false;
        }
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return name.toString();
    }
}