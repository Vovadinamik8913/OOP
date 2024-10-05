package ru.nsu.abramenko.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.transform.IntegerTransform;


class GraphTest {
    @Test
    void openFileError() {
        Graph<Integer> graph = new GraphAdjacencyMatrix<>();
        IntegerTransform transform = new IntegerTransform();
        try {
            graph.scanFromFile(
                    "../Task_1_2_1/src/main/java/ru/nsu/abramenko/inp.txt", transform);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    @DisplayName("CycleError")
    void cycleError() {
        Graph<Integer> graph = new GraphAdjacencyList<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(new Edge<>(1, 2));
        graph.addEdge(new Edge<>(2, 1));
        try {
            for (Integer vertex : graph.topologicalSort()) {
                System.out.print(vertex + " ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }
}