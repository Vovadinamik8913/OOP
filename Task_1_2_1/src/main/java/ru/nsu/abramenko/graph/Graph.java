package ru.nsu.abramenko.graph;

import org.jetbrains.annotations.NotNull;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.transform.Transform;
import java.io.IOException;
import java.util.ArrayList;

public interface Graph<T> {


    boolean contains(@NotNull T v);
    void addVertex(@NotNull T v);
    void delVertex(@NotNull T v);

    boolean contains(@NotNull Edge<T> e);
    void addEdge(@NotNull Edge<T> e);
    void delEdge(@NotNull Edge<T> e);

    ArrayList<T> getAllNeighbours(@NotNull T v);

    void scanFromFile(String path, Transform<T> transform) throws IOException;

    ArrayList<T> topologicalSort() throws Exception;
}