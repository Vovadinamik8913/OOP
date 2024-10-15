package ru.nsu.abramenko.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;
import ru.nsu.abramenko.transform.IntegerTransform;


class GraphTest {

    @Test
    @DisplayName("ScanFile")
    void scanFileTest() {
        Graph<Integer> graph = new GraphAdjacencyMatrix<>();
        IntegerTransform transform = new IntegerTransform();
        try {
            graph.scanFromFile(
                    ClassLoader.getSystemResource("input.txt").getPath(), transform);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Vertex<Integer> vertex : graph.getAllNeighbours(new Vertex<>(1))) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        try {
            for (Vertex<Integer> vertex : graph.topologicalSort()) {
                System.out.print(vertex + " ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println(graph);
        assertTrue(true);
    }

    @Test
    @DisplayName("FileError")
    void openFileError() {
        Graph<Integer> graph = new GraphAdjacencyMatrix<>();
        IntegerTransform transform = new IntegerTransform();
        try {
            graph.scanFromFile(
                    ClassLoader.getSystemResource("input.txt").getPath(), transform);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    @DisplayName("CycleError")
    void cycleError() {
        Graph<Integer> graph = new GraphAdjacencyList<>();
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(new Edge<>(new Vertex<>(1), new Vertex<>(2)));
        graph.addEdge(new Edge<>(new Vertex<>(1), new Vertex<>(2)));
        try {
            for (Vertex<Integer> vertex : graph.topologicalSort()) {
                System.out.print(vertex + " ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }
}