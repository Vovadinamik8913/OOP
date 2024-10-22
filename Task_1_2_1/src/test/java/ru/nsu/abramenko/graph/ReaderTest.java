package ru.nsu.abramenko.graph;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.graph.implement.AdjacencyList;
import ru.nsu.abramenko.graph.implement.AdjacencyMatrix;

class ReaderTest {

    @Test
    @DisplayName("ScanFile")
    void scanFileTest() {
        Reader reader = new Reader();
        Graph<Integer> graph = new AdjacencyList<>();
        assertDoesNotThrow(() -> {
            reader.scanFromFile(
                    graph,
                    ClassLoader.getSystemResource("input.txt").getPath(), Integer::parseInt
            );
        });
    }

    @Test
    @DisplayName("FileError")
    void openFileError() {
        Reader reader = new Reader();
        Graph<Integer> graph = new AdjacencyMatrix<>();
        assertThrows(IOException.class, () -> {
            reader.scanFromFile(
                    graph,
                    "/inp.txt", Integer::parseInt);
        });
    }
}