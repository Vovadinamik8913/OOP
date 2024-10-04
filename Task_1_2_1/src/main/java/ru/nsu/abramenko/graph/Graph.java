package ru.nsu.abramenko.graph;

import java.io.IOException;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.transform.Transform;


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
    boolean contains(@NotNull T v);

    /** add vertex to graph if doesn`t exist.
     *
     * @param v vertex
     */
    void addVertex(@NotNull T v);

    /** del vertex if exists.
     *
     * @param v vertex
     */
    void delVertex(@NotNull T v);

    /** is there an edge between vertex.
     * from -> to (oriented)
     *
     * @param e edge
     * @return true or false
     */
    boolean contains(@NotNull Edge<T> e);

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
     *
     * @param v vertex
     * @return arr of neighbours
     */
    ArrayList<T> getAllNeighbours(@NotNull T v);

    /** create graph by scanning file.
     * Data view: vertex vertex
     *
     * @param path path to file
     * @param transform function of Generic transformation
     * @throws IOException If can`t open File
     */
    void scanFromFile(String path, Transform<T> transform) throws IOException;

    /** sort graph in topological order.
     *
     * @return sorted graph
     * @throws Exception if there is cycle
     */
    ArrayList<T> topologicalSort() throws Exception;
}