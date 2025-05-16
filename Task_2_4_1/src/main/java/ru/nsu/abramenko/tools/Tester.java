package ru.nsu.abramenko.tools;

import java.io.File;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

public class Tester {
    public static boolean run(String labDir) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(labDir));
        ProjectConnection connection = connector.connect();
        connection.newBuild()
                .forTasks("test", "javadoc")
                .run();
        return true;
    }
}
