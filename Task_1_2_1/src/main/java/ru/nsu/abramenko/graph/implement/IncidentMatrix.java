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

/**
 implements Graph.
 * by using incident matrix
 *
 * @param <T> class Type
 */
@EqualsAndHashCode(exclude = {"graph"})
public class IncidentMatrix<T> implements Graph<T> {

    private final Map<Vertex<T>, Map<Edge<T>, Integer>> graph;


    public IncidentMatrix() {
        graph = new HashMap<>();
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
        Map<Edge<T>, Integer> buf = new HashMap<>();
        for (Map<Edge<T>, Integer> entry : graph.values()) {
            for (Edge<T> key : entry.keySet()) {
                buf.put(key, 0);
            }
            break;
        }
        graph.putIfAbsent(v, buf);
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
        return graph.containsValue(e);
    }

    @Override
    public void addEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            return;
        }
        addVertex(e.getFrom());
        addVertex(e.getTo());
        graph.values().forEach((inc) -> inc.put(e, 0));
        if (!e.getFrom().equals(e.getTo())) {
            graph.get(e.getFrom()).replace(e, e.getValue());
        }
    }

    @Override
    public void delEdge(@NotNull Edge<T> e) {
        if (!containsEdge(e)) {
            return;
        }
        graph.values().forEach((inc) -> inc.remove(e));
    }

    @Override
    @Nullable
    public List<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<Vertex<T>> res = new ArrayList<>();
        for (var map : graph.get(v).entrySet()) {
            if (map.getValue() != 0) {
                if (!res.contains(map.getKey().getTo())) {
                    res.add(map.getKey().getTo());
                }
            }
        }
        return res;
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
        for (Map<Edge<T>, Integer> entry : graph.values()) {
            for (Edge<T> key : entry.keySet()) {
                res.append(" \t").append(key);
            }
            break;
        }
        res.append("\n");
        for (var node : graph.entrySet()) {
            res.append(node.getKey().toString());
            for (Integer val : node.getValue().values()) {
                res.append("\t\t").append(val.toString());
            }
            res.append("\n");
        }
        return res.toString();
    }
}