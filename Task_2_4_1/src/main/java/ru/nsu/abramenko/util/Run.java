package ru.nsu.abramenko.util;

import java.io.File;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

public class Run {
    @SneakyThrows
    public static boolean run(String labDir) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(labDir));
        @Cleanup ProjectConnection connection = connector.connect();
        connection.newBuild()
                .forTasks("test", "javadoc")
                .run();
        return true;
    }
}
