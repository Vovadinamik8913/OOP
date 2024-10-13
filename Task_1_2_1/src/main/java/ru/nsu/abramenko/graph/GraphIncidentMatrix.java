package ru.nsu.abramenko.graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.jetbrains.annotations.NotNull;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.transform.Transform;

/**
 implements Graph.
 * by using incident matrix
 *
 * @param <T> class Type
 */
public class GraphIncidentMatrix<T> implements Graph<T> {

    private final HashMap<T, HashMap<String, Integer>> graph;
    private final HashMap<String, Edge<T>> edges;

    /** init matrix.
     *
     */
    public GraphIncidentMatrix() {
        graph = new HashMap<>();
        edges = new HashMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull T v) {
        return graph.containsKey(v);
    }

    @Override
    public void addVertex(@NotNull T v) {
        if (containsVertex(v)) {
            return;
        }
        HashMap<String, Integer> buf = new HashMap<String, Integer>();
        for (HashMap<String, Integer> entry : graph.values()) {
            for (String key : entry.keySet()) {
                buf.put(key, 0);
            }
            break;
        }
        graph.putIfAbsent(v, buf);
    }

    @Override
    public void delVertex(@NotNull T v) {
        if (!containsVertex(v)) {
            return;
        }
        graph.remove(v);
        graph.values().forEach((neighbours) -> neighbours.remove(v));
    }

    @Override
    public boolean containsEdge(@NotNull Edge<T> e) {
        return edges.containsKey(e.toString());
    }

    @Override
    public void addEdge(@NotNull Edge<T> e) {
        if (containsEdge(e)) {
            return;
        }
        addVertex(e.getFrom());
        addVertex(e.getTo());
        edges.putIfAbsent(e.toString(), e);
        graph.values().forEach((inc) -> inc.put(e.toString(), 0));
        if (!e.getFrom().equals(e.getTo())) {
            graph.get(e.getTo()).replace(e.toString(), e.getValue());
        }
    }

    @Override
    public void delEdge(@NotNull Edge<T> e) {
        if (!containsEdge(e)) {
            return;
        }
        edges.remove(e.toString());
        graph.values().forEach((inc) -> inc.remove(e.toString()));
    }

    @Override
    public ArrayList<T> getAllNeighbours(@NotNull T v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<T> res = new ArrayList<>();
        for (Map.Entry<String, Integer> map : graph.get(v).entrySet()) {
            Edge<T> e = edges.get(map.getKey());
            switch (map.getValue()) {
                case 1:
                    if (!res.contains(e.getFrom())) {
                        res.add(e.getFrom());
                    }
                    break;
                case -1:
                    if (!res.contains(e.getTo())) {
                        res.add(e.getTo());
                    }
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }
        return res;
    }

    @Override
    public void scanFromFile(String path, Transform<T> transform) throws IOException {
        File file = new File(path);
        List<String> line = Files.readAllLines(file.toPath());
        for (String pair : line) {
            String[] parts = pair.split(" ");
            addEdge(new Edge<T>(
                    transform.transform(parts[0]),
                    transform.transform(parts[1])));
        }
    }

    @Override
    public ArrayList<T> topologicalSort() throws Exception {
        HashMap<T, Integer> inDegree = new HashMap<>();
        // Инициализация степени входа
        for (T vertex : graph.keySet()) {
            inDegree.put(vertex, 0);
        }
        // Подсчет степени входа для каждой вершины
        for (Map.Entry<T, HashMap<String, Integer>> entry : graph.entrySet()) {
            for (Map.Entry<String, Integer> edge : entry.getValue().entrySet()) {
                if (edge.getValue() == 0) {
                    continue;
                }
                Edge<T> e = edges.get(edge.getKey());
                T neighbor = e.getFrom();
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<T> queue = new LinkedList<>();
        // Добавление вершин с нулевой степенью входа в очередь
        for (Map.Entry<T, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        ArrayList<T> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            T current = queue.poll();
            sortedList.add(current);

            // Обновление степени входа соседей
            for (Map.Entry<String, Integer>  entry : graph.get(current).entrySet()) {
                Edge<T> edge = edges.get(entry.getKey());
                if (edge != null && entry.getValue() != 0) {
                    T neighbor = edge.getFrom();
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
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphIncidentMatrix<?> other)) {
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
        for (HashMap<String, Integer> entry : graph.values()) {
            for (String key : entry.keySet()) {
                res.append(" \t").append(key);
            }
            break;
        }
        res.append("\n");
        for (Map.Entry<T, HashMap<String, Integer>> node : graph.entrySet()) {
            res.append(node.getKey().toString());
            for (Integer val : node.getValue().values()) {
                res.append("\t\t").append(val.toString());
            }
            res.append("\n");
        }
        return res.toString();
    }
}