package ru.nsu.abramenko.graph.basic;

/** record class Vertex with name of type T.
 *
 * @param name name
 * @param <T> class Types
 */
public record Vertex<T>(T name) {
    @Override
    public String toString() {
        return name.toString();
    }
}