package ru.nsu.abramenko.graph.basic;

import java.util.Objects;

/** record class Vertex with name of type T.
 *
 * @param name name
 * @param <T> class Types
 */
public record Vertex<T>(T name) {

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
        if (!(obj instanceof Vertex<?> other)) {
            return false;
        }
        return Objects.equals(name, other.name);
    }

    /** name of vertex to string.
     *
     * @return name to string
     */
    @Override
    public String toString() {
        return name.toString();
    }

    /** hashCode of vertex.
     *
     * @return hashCode from name
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}