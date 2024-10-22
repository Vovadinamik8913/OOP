package ru.nsu.abramenko.graph;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;


/** interface to implement Graphs.
 *
 * @param <T> classType to set vertex names
 */
public interface Graph<T> {
    /** is vertex contains in graph.
     *
     * @param v vertex
     * @return true or false
     */
    boolean containsVertex(@NotNull Vertex<T> v);

    /** add vertex to graph if doesn`t exist.
     *
     * @param v vertex
     */
    void addVertex(@NotNull Vertex<T> v);

    /** del vertex if exists.
     *
     * @param v vertex
     */
    void delVertex(@NotNull Vertex<T> v);

    /** is there an edge between vertex.
     * from -> to (oriented)
     *
     * @param e edge
     * @return true or false
     */
    boolean containsEdge(@NotNull Edge<T> e);

    /** add edge if not contains.
     * add vertexes if not contains
     *
     * @param e edge
     */
    void addEdge(@NotNull Edge<T> e);

    /** del edge if contains.
     *
     * @param e edge
     */
    void delEdge(@NotNull Edge<T> e);

    /** get vertex neighbours.
     * or null
     * if no neighbours/vertex
     *
     * @param v vertex
     * @return arr of neighbours
     */
    @Nullable
    List<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v);


    /** get vertex neighbours.
     * or null
     * if no neighbours/vertex
     *
     * @return arr of neighbours
     */
    @Nullable
    List<Vertex<T>> getAllVertexes();
}