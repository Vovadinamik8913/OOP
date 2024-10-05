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


/** implements Graph.
 * by using Adjacency matrix
 *
 * @param <T> class Type
 */
public class GraphAdjacencyMatrix<T> implements Graph<T> {

    private final HashMap<T, HashMap<T, Integer>> graph;


    /** init matrix.
     *
     */
    public GraphAdjacencyMatrix() {
        this.graph = new HashMap<>();
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
        HashMap<T, Integer> buf = new HashMap<T, Integer>();
        for (T key : graph.keySet()) {
            buf.put(key, 0);
        }
        graph.putIfAbsent(v, buf);
        graph.values().forEach((neighbours) -> neighbours.putIfAbsent(v, 0));
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
        graph.get(e.getFrom()).replace(e.getTo(), 1);
    }

    @Override
    public void delEdge(@NotNull Edge<T> e) {
        if (!containsEdge(e)) {
            return;
        }
        graph.get(e.getFrom()).replace(e.getTo(), 0);
    }

    @Override
    public ArrayList<T> getAllNeighbours(@NotNull T v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<T> neighbours = new ArrayList<>();
        for (Map.Entry<T, HashMap<T, Integer>> node : graph.entrySet()) {
            if (!node.getKey().equals(v)) {
                if (node.getValue().get(v) != 0 && !neighbours.contains(node.getKey())) {
                    neighbours.add(node.getKey());
                }
            } else {
                for (Map.Entry<T, Integer> value : node.getValue().entrySet()) {
                    if (value.getValue() != 0 && !neighbours.contains(value.getKey())) {
                        neighbours.add(value.getKey());
                    }
                }
            }
        }
        return neighbours;
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
        for (Map.Entry<T, HashMap<T, Integer>> entry : graph.entrySet()) {
            for (Map.Entry<T, Integer> neighbor : entry.getValue().entrySet()) {
                if (neighbor.getValue() != 0) {
                    inDegree.put(neighbor.getKey(), inDegree.get(neighbor.getKey()) + 1);
                }
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
            for (Map.Entry<T, Integer> neighbor : graph.get(current).entrySet()) {
                if (neighbor.getValue() != 0) {
                    inDegree.put(neighbor.getKey(), inDegree.get(neighbor.getKey()) - 1);
                    if (inDegree.get(neighbor.getKey()) == 0) {
                        queue.add(neighbor.getKey());
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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphAdjacencyMatrix<?> other)) {
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
        for (T keys : graph.keySet()) {
            res.append("\t").append(keys.toString());
        }
        res.append("\n");
        for (Map.Entry<T, HashMap<T, Integer>> node : graph.entrySet()) {
            res.append(node.getKey().toString());
            for (Integer val : node.getValue().values()) {
                res.append("\t").append(val.toString());
            }
            res.append("\n");
        }
        return res.toString();
    }
}