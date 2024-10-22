package ru.nsu.abramenko.graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.graph.basic.Vertex;


/** class for reading graphs.
 *
 */
public class Reader {
    /** create graph by scanning file.
     * Data view: vertex vertex
     *
     * @param graph graph
     * @param path path to file
     * @param parse function of Generic transform
     * @param <T> class
     * @throws IOException If can`t open File
     */
    public <T> void scanFromFile(Graph<T> graph,String path, Function<String, T> parse) throws IOException {
        File file = new File(path);
        List<String> line = Files.readAllLines(file.toPath());
        for (String pair : line) {
            String[] parts = pair.split(" ");
            graph.addEdge(new Edge<T>(
                    new Vertex<>(parse.apply(parts[0])),
                    new Vertex<>(parse.apply(parts[1]))));
        }
    }
}
