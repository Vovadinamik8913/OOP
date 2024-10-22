package ru.nsu.abramenko.graph;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;
import ru.nsu.abramenko.graph.implement.AdjacencyList;

import java.util.ArrayList;


class AlgorithmsTest {

    @Test
    @DisplayName("CycleError")
    void cycleError() {
        Graph<Integer> graph = new AdjacencyList<>();
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(new Edge<>(new Vertex<>(1), new Vertex<>(2)));
        graph.addEdge(new Edge<>(new Vertex<>(2), new Vertex<>(1)));
        Algorithms algorithms = new Algorithms();
        assertThrows(RuntimeException.class, () -> {
            for (Vertex<Integer> vertex : algorithms.topologicalSort(graph)) {
                System.out.print(vertex + " ");
            }
        });
    }

    @Test
    @DisplayName("EmptyGraph")
    void emptyGraph() {
        Graph<Integer> graph = new AdjacencyList<>();
        Algorithms algorithms = new Algorithms();
        ArrayList<Vertex<Integer>> sorted = algorithms.topologicalSort(graph);
        assertTrue(sorted == null);
    }
}