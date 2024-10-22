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

/**
 implements Graph.
 * by using incident matrix
 *
 * @param <T> class Type
 */
public class IncidentMatrix<T> implements Graph<T> {

    private final HashMap<Vertex<T>, HashMap<Edge<T>, Integer>> graph;


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
        HashMap<Edge<T>, Integer> buf = new HashMap<>();
        for (HashMap<Edge<T>, Integer> entry : graph.values()) {
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
    public @Nullable List<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<Vertex<T>> res = new ArrayList<>();
        for (Map.Entry<Edge<T>, Integer> map : graph.get(v).entrySet()) {
            if (map.getValue() != 0) {
                if (!res.contains(map.getKey().getTo())) {
                    res.add(map.getKey().getTo());
                }
            }
        }
        return res;
    }

    @Override
    public @Nullable List<Vertex<T>> getAllVertexes() {
        if (graph.isEmpty()) {
            return null;
        }
        return graph.keySet().stream().toList();
    }

    /*public ArrayList<Vertex<T>> topologicalSort() throws Exception {
        HashMap<Vertex<T>, Integer> inDegree = new HashMap<>();
        // Инициализация степени входа
        for (Vertex<T> vertex : graph.keySet()) {
            inDegree.put(vertex, 0);
        }
        // Подсчет степени входа для каждой вершины
        for (Map.Entry<Vertex<T>, HashMap<String, Integer>> entry : graph.entrySet()) {
            for (Map.Entry<String, Integer> edge : entry.getValue().entrySet()) {
                if (edge.getValue() == 0) {
                    continue;
                }
                Edge<T> e = edges.get(edge.getKey());
                Vertex<T> neighbor = e.getTo();
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<Vertex<T>> queue = new LinkedList<>();
        // Добавление вершин с нулевой степенью входа в очередь
        for (Map.Entry<Vertex<T>, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        ArrayList<Vertex<T>> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            sortedList.add(current);

            // Обновление степени входа соседей
            for (Map.Entry<String, Integer>  entry : graph.get(current).entrySet()) {
                Edge<T> edge = edges.get(entry.getKey());
                if (edge != null && entry.getValue() != 0) {
                    Vertex<T> neighbor = edge.getTo();
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Проверка на наличие циклов
        if (sortedList.size() != graph.size()) {
            throw new Exception("Граф содержит цикл, топологическая сортировка невозможна.");
        }

        return sortedList;
    }*/


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IncidentMatrix<?> other)) {
            return false;
        }
        return graph.equals(other.graph);
    }

    @Override
    public String toString() {
        if (graph.isEmpty()) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        for (HashMap<Edge<T>, Integer> entry : graph.values()) {
            for (Edge<T> key : entry.keySet()) {
                res.append(" \t").append(key);
            }
            break;
        }
        res.append("\n");
        for (Map.Entry<Vertex<T>, HashMap<Edge<T>, Integer>> node : graph.entrySet()) {
            res.append(node.getKey().toString());
            for (Integer val : node.getValue().values()) {
                res.append("\t\t").append(val.toString());
            }
            res.append("\n");
        }
        return res.toString();
    }

    @Override
    public int hashCode() {
        return graph.hashCode();
    }
}