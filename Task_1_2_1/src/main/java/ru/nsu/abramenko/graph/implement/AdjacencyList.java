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

/**implements Graph.
 * by using Adjacency list
 *
 * @param <T> classType
 */
@EqualsAndHashCode(exclude={"graph"})
public class AdjacencyList<T> implements Graph<T> {

    private final Map<Vertex<T>, List<Edge<T>>> graph;

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
        for (List<Edge<T>> neighbors : graph.values()) {
            neighbors.removeIf((e) -> e.getTo().equals(v));
        }
    }

    @Override
    public void addEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            return;
        }
        addVertex(e.getFrom());
        addVertex(e.getTo());
        graph.get(e.getFrom()).add(e);
    }

    @Override
    public void delEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            graph.get(e.getFrom()).remove(e);
        }
    }

    @Override
    @Nullable
    public List<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<Vertex<T>> neighbors = new ArrayList<>();
        for (var edge : graph.get(v)) {
            if (!neighbors.contains(edge.getTo())) {
                neighbors.add(edge.getTo());
            }
        }
        return neighbors;
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
    public boolean containsVertex(@NotNull Vertex<T> v) {
        return graph.containsKey(v);
    }

    @Override
    public boolean containsEdge(@NotNull Edge<T> e) {
        if (containsVertex(e.getFrom()) && containsVertex(e.getTo())) {
            List<Edge<T>> adj = graph.get(e.getFrom());
            return adj.contains(e);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var entry : graph.entrySet()) {
            sb.append(entry.getKey().toString()).append(" -> ");
            if (entry.getValue() != null) {
                for (Edge<T> neighbor : entry.getValue()) {
                    sb.append(neighbor.getTo().toString()).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}