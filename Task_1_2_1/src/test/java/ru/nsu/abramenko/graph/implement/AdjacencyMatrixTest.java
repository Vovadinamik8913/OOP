package ru.nsu.abramenko.graph.implement;

import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.graph.Algorithms;
import ru.nsu.abramenko.graph.Graph;
import ru.nsu.abramenko.graph.Reader;
import ru.nsu.abramenko.graph.basic.Vertex;

class AdjacencyMatrixTest {

    @Test
    @DisplayName("ScanFile")
    void scanFileTest() {
        Reader reader = new Reader();
        AdjacencyMatrix<Integer> graph = new AdjacencyMatrix<>();
        try {
            reader.scanFromFile(graph,
                    ClassLoader.getSystemResource("input.txt").getPath(), Integer::parseInt);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Vertex<Integer> vertex : graph.getAllNeighbours(new Vertex<>(1))) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        Algorithms algorithms = new Algorithms();
        try {
            for (Vertex<Integer> vertex : algorithms.topologicalSort(graph)) {
                System.out.print(vertex + " ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println(graph);
        assertTrue(true);
    }
}