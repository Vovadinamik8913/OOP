package ru.nsu.abramenko.graph.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.abramenko.graph.Graph;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;


/** implements Graph.
 * by using Adjacency matrix
 *
 * @param <T> class Type
 */
@EqualsAndHashCode(exclude={"graph"})
public class AdjacencyMatrix<T> implements Graph<T> {

    private final Map<Vertex<T>, Map<Vertex<T>, Integer>> graph;

    public AdjacencyMatrix() {
        this.graph = new HashMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull Vertex<T> v) {
        return graph.containsKey(v);
    }

    @Override
    public void addVertex(@NotNull Vertex<T> v) {
        if (containsVertex(v)) {
            return;
        }
        Map<Vertex<T>, Integer> buf = new HashMap<>();
        for (Vertex<T> key : graph.keySet()) {
            buf.put(key, 0);
        }
        graph.putIfAbsent(v, buf);
        graph.values().forEach((neighbours) -> neighbours.putIfAbsent(v, 0));
    }

    @Override
    public void delVertex(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return;
        }
        graph.remove(v);
        graph.values().forEach((neighbours) -> neighbours.remove(v));
    }

    @Override
    public boolean containsEdge(@NotNull Edge<T> e) {
        if (containsVertex(e.getFrom()) && containsVertex(e.getTo())) {
            return graph.get(e.getFrom()).get(e.getTo()) != 0;
        }
        return false;
    }

    @Override
    public void addEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            return;
        }
        addVertex(e.getFrom());
        addVertex(e.getTo());
        graph.get(e.getFrom()).replace(e.getTo(), e.getValue());
    }

    @Override
    public void delEdge(@NotNull Edge<T> e) {
        if (!containsEdge(e)) {
            return;
        }
        graph.get(e.getFrom()).replace(e.getTo(), 0);
    }

    @Override
    @Nullable
    public List<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<Vertex<T>> neighbours = new ArrayList<>();
        for (var node : graph.get(v).entrySet()) {
            if (node.getValue() != 0 && !neighbours.contains(node.getKey())) {
                neighbours.add(node.getKey());
            }
        }
        return neighbours;
    }

    @Override
    @Nullable
    public List<Vertex<T>> getAllVertexes() {
        if (graph.isEmpty()) {
            return null;
        }
        return graph.keySet().stream().toList();
    }

    @Override
    public String toString() {
        if (graph.isEmpty()) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        for (Vertex<T> keys : graph.keySet()) {
            res.append("\t").append(keys.toString());
        }
        res.append("\n");
        for (var node : graph.entrySet()) {
            res.append(node.getKey().toString());
            for (Integer val : node.getValue().values()) {
                res.append("\t").append(val.toString());
            }
            res.append("\n");
        }
        return res.toString();
    }
}