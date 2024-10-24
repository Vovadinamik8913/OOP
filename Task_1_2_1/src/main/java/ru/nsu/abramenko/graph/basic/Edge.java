package ru.nsu.abramenko.graph.basic;

import lombok.Data;

/** edge from vertex to vertex.
 *
 * @param <T> class Type
 */
@Data
public class Edge<T> {
    private final Vertex<T> from;
    private final Vertex<T> to;
    private int value;

    /** constructor, value equals 1.
     *
     * @param from from vertex
     * @param to to vertex
     */
    public Edge(Vertex<T> from, Vertex<T> to) {
        this.from = from;
        this.to = to;
        this.value = 1;
    }

    /** constructor with value.
     *
     * @param from from vertex
     * @param to to vertex
     * @param value int value
     */
    public Edge(Vertex<T> from, Vertex<T> to, int value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + from.toString() + ">" + to.toString() + ")";
    }
}