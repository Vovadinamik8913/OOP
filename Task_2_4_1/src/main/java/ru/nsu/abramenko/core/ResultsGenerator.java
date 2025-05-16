package ru.nsu.abramenko.core;

import org.apache.commons.io.FileUtils;
import ru.nsu.abramenko.dsl.config.Course;
import ru.nsu.abramenko.tools.TableBuild;

import java.io.File;

public class ResultsGenerator {
    private static final String LABS_DIR;

    static {
        LABS_DIR = ClassLoader.getSystemResource("").toString() + File.separator + "labs";
    }


    public void generateResults(Course config) {
        TableBuild.generateHtmlTableChart(config.getGroups(), config.getSettings());
        cleanupLabs();
    }

    private void cleanupLabs() {
        File labsDir = new File(LABS_DIR);
        if (labsDir.exists()) {
            try {
                FileUtils.deleteDirectory(labsDir);
            } catch (Exception e) {
                System.err.println("Failed to clean up labs directory: " + e.getMessage());
            }
        }
    }
}
