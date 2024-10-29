package ru.nsu.abramenko.graph.implement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.graph.Algorithms;
import ru.nsu.abramenko.graph.Reader;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;

class AdjacencyMatrixTest {

    @Test
    @DisplayName("ScanFile")
    void scanFileTest() {
        Reader reader = new Reader();
        AdjacencyMatrix<Integer> graph = new AdjacencyMatrix<>();
        try {
            reader.scanFromFile(graph,
                    "input.txt",
                    Integer::parseInt);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Vertex<Integer> vertex : graph.getAllNeighbours(new Vertex<>(1))) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        Algorithms algorithms = new Algorithms();
        for (Vertex<Integer> vertex : algorithms.topologicalSort(graph)) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        System.out.println(graph);
        assertTrue(true);
    }

    @Test
    @DisplayName("Contains")
    void notContainsVertex() {
        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
        assertFalse(matrix.containsVertex(
                new Vertex<>(1)));
    }

    @Test
    @DisplayName("Contains")
    void notContainsEdge() {
        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
        assertFalse(matrix.containsEdge(
                new Edge<>(
                        new Vertex<>(1),
                        new Vertex<>(2)
                )));
    }

    @Test
    @DisplayName("Delete")
    void delVertex() {
        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
        matrix.addVertex(new Vertex<>(1));
        matrix.delVertex(new Vertex<>(1));
        assertFalse(matrix.containsVertex(
                new Vertex<>(1)));
    }

    @Test
    @DisplayName("Delete")
    void delNotExistsVertex() {
        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
        matrix.delVertex(new Vertex<>(2));
        assertFalse(matrix.containsVertex(
                new Vertex<>(1)));
    }

    @Test
    @DisplayName("Add")
    void addExistsVertex() {
        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
        matrix.addVertex(new Vertex<>(1));
        matrix.addVertex(new Vertex<>(1));
        assertTrue(matrix.containsVertex(
                new Vertex<>(1)));
    }

    @Test
    @DisplayName("Delete")
    void delEdge() {
        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>();
        Edge<Integer> e = new Edge<>(
                new Vertex<>(1),
                new Vertex<>(2));
        matrix.addEdge(
                e
        );
        matrix.delEdge(e);
        assertFalse(matrix.containsEdge(e));
    }
}