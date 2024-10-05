package ru.nsu.abramenko;

import java.io.IOException;
import ru.nsu.abramenko.graph.Graph;
import ru.nsu.abramenko.graph.GraphAdjacencyMatrix;
import ru.nsu.abramenko.graph.GraphIncidentMatrix;
import ru.nsu.abramenko.graph.basic.Edge;
import ru.nsu.abramenko.transform.IntegerTransform;


/** Main.
 *
 */
public class Main {
    /** main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        Graph<Integer> graph = new GraphAdjacencyMatrix<>();
        graph.addEdge(new Edge<>(1, 2));
        graph.addEdge(new Edge<>(1, 3));
        graph.addEdge(new Edge<>(4, 1));
        graph.delVertex(4);
        graph.delEdge(new Edge<>(1, 3));
        System.out.println();
        System.out.println(graph);
    }
}