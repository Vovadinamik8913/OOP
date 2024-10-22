package ru.nsu.abramenko.graph.implement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.abramenko.graph.Graph;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;

/**implements Graph.
 * by using Adjacency list
 *
 * @param <T> classType
 */
public class AdjacencyList<T> implements Graph<T> {

    private final HashMap<Vertex<T>, ArrayList<Vertex<T>>> graph;

    public AdjacencyList() {
        this.graph = new HashMap<>();
    }

    @Override
    public void addVertex(@NotNull Vertex<T> v) {
        if (containsVertex(v)) {
            return;
        }
        graph.putIfAbsent(v, new ArrayList<>());
    }

    @Override
    public void delVertex(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return;
        }
        graph.remove(v);
        for (ArrayList<Vertex<T>> neighbors : graph.values()) {
            neighbors.remove(v);
        }
    }

    @Override
    public void addEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            return;
        }
        addVertex(e.getFrom());
        addVertex(e.getTo());
        graph.get(e.getFrom()).add(e.getTo());
    }

    @Override
    public void delEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            graph.get(e.getFrom()).remove(e.getTo());
        }
    }

    @Override
    public @Nullable List<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return null;
        }
        return graph.get(v);
    }

    @Override
    public @Nullable List<Vertex<T>> getAllVertexes() {
        if (graph.isEmpty()) {
            return null;
        }
        return graph.keySet().stream().toList();
    }

    @Override
    public boolean containsVertex(@NotNull Vertex<T> v) {
        return graph.containsKey(v);
    }

    @Override
    public boolean containsEdge(@NotNull Edge<T> e) {
        if (containsVertex(e.getFrom()) && containsVertex(e.getTo())) {
            ArrayList<Vertex<T>> adj = graph.get(e.getFrom());
            return adj.contains(e.getTo());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Vertex<T>, ArrayList<Vertex<T>>> entry : graph.entrySet()) {
            sb.append(entry.getKey().toString()).append(" -> ");
            if (entry.getValue() != null) {
                for (Vertex<T> neighbor : entry.getValue()) {
                    sb.append(neighbor.toString()).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdjacencyList<?> other)) {
            return false;
        }
        return graph.equals(other.graph);
    }

    @Override
    public int hashCode() {
        return graph.hashCode();
    }
}