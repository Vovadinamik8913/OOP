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
import ru.nsu.abramenko.graph.basic.Vertex;
import ru.nsu.abramenko.transform.Transform;

/**implements Graph.
 * by using Adjacency list
 *
 * @param <T> classType
 */
public class GraphAdjacencyList<T> implements Graph<T> {

    private final HashMap<Vertex<T>, ArrayList<Vertex<T>>> graph;

    /** init list.
     *
     */
    public GraphAdjacencyList() {
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
    public ArrayList<Vertex<T>> getAllNeighbours(@NotNull Vertex<T> v) {
        if (!containsVertex(v)) {
            return null;
        }
        ArrayList<Vertex<T>> neighbours = graph.get(v);
        for (Map.Entry<Vertex<T>, ArrayList<Vertex<T>>> node : graph.entrySet()) {
            if (!node.getKey().equals(v)) {
                if (node.getValue().contains(v) && !neighbours.contains(node.getKey())) {
                    neighbours.add(node.getKey());
                }
            }
        }
        return neighbours;
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
    public void scanFromFile(String path, Transform<T> transform) throws IOException {
        File file = new File(path);
        List<String> line = Files.readAllLines(file.toPath());
        for (String pair : line) {
            String[] parts = pair.split(" ");
            addEdge(new Edge<T>(
                    new Vertex<>(transform.transform(parts[0])),
                    new Vertex<>(transform.transform(parts[1]))));
        }
    }

    @Override
    public ArrayList<Vertex<T>> topologicalSort() throws Exception {
        HashMap<Vertex<T>, Integer> inDegree = new HashMap<>();
        // Инициализация степени входа
        for (Vertex<T> vertex : graph.keySet()) {
            inDegree.put(vertex, 0);
        }

        for (Map.Entry<Vertex<T>, ArrayList<Vertex<T>>> entry : graph.entrySet()) {
            for (Vertex<T> neighbor : entry.getValue()) {
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
            for (Vertex<T> neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
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
        if (!(obj instanceof GraphAdjacencyList<?> other)) {
            return false;
        }
        return graph.equals(other.graph);
    }

    @Override
    public int hashCode() {
        return graph.hashCode();
    }
}