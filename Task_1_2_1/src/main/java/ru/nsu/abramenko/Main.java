package ru.nsu.abramenko;

import java.io.IOException;
import ru.nsu.abramenko.graph.Graph;
import ru.nsu.abramenko.graph.GraphIncidentMatrix;
import ru.nsu.abramenko.transform.IntegerTransform;



public class Main {
    public static void main(String[] args) {
        Graph <Integer> graph = new GraphIncidentMatrix<>();
        IntegerTransform transform = new IntegerTransform();
        try {
            graph.scanFromFile("/home/abrikosjr/Desktop/OOPworkspace/OOP/Task_1_2_1/src/main/java/ru/nsu/abramenko/input.txt", transform);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Integer vertex : graph.getAllNeighbours(1)) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        try {
            for (Integer vertex : graph.topologicalSort()) {
                System.out.print(vertex + " ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        System.out.println(graph.toString());
    }
}