package ru.nsu.abramenko.graph.basic;

import java.util.Objects;

/** edge from vertex to vertex.
 *
 * @param <T> class Type
 */
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


    /** get value.
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /** set value or reweigh.
     *
     * @param value new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /** get vertex from.
     *
     * @return vertex from
     */
    public Vertex<T> getFrom() {
        return from;
    }

    /** get vertex to.
     *
     * @return vertex to
     */
    public Vertex<T> getTo() {
        return to;
    }

    /** is obj equals this.
     *
     * @param obj the reference object with which to compare.
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
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

    /** edge to string.
     *
     * @return (from and to)
     */
    @Override
    public String toString() {
        return "(" + from.toString() + ">" + to.toString() + ")";
    }

    /** get hashCode of edge.
     *
     * @return hashCode from String
     */
    @Override
    public int hashCode() {
        return ((from.hashCode()*31 + to.hashCode()) * 29 + value) * 7;
    }
}