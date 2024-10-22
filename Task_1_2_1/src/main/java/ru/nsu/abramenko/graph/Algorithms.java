package ru.nsu.abramenko.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.jetbrains.annotations.Nullable;
import ru.nsu.abramenko.graph.basic.Vertex;


/** algorithms for graph.
 *
 */
public class Algorithms {


    /** sort graph in topological order.
     *
     * @param graph graph to sort
     * @param <T> class
     * @return sorted graph
     * @throws RuntimeException if there is cycle
     */
    public @Nullable <T> ArrayList<Vertex<T>> topologicalSort(Graph<T> graph) throws RuntimeException {
        List<Vertex<T>> vertexes = graph.getAllVertexes();
        if (vertexes == null) {
            return null;
        }
        HashMap<Vertex<T>, Integer> inDegree = new HashMap<>();
        // Инициализация степени входа
        for (Vertex<T> vertex : vertexes) {
            inDegree.put(vertex, 0);
        }

        for (Vertex<T> vertex : vertexes) {
            List<Vertex<T>> neighbors = graph.getAllNeighbours(vertex);
            if (neighbors == null) {
                continue;
            }
            for (Vertex<T> neighbor : neighbors) {
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

            List<Vertex<T>> neighbors = graph.getAllNeighbours(current);
            if (neighbors == null) {
                continue;
            }
            // Обновление степени входа соседей
            for (Vertex<T> neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Проверка на наличие циклов
        if (sortedList.size() != vertexes.size()) {
            throw new RuntimeException("Граф содержит цикл, топологическая сортировка невозможна.");
        }

        return sortedList;
    }
}
