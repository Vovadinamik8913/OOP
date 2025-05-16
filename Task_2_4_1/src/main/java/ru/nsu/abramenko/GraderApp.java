package ru.nsu.abramenko;

import lombok.SneakyThrows;
import ru.nsu.abramenko.core.CourseGrader;
import ru.nsu.abramenko.core.ResultsGenerator;
import ru.nsu.abramenko.dsl.config.Course;

import java.net.URI;
import java.util.Objects;

public class GraderApp {
    @SneakyThrows
    public static void main(String[] args) {
        Course config = new Course();
        URI configPath = Objects.requireNonNull(GraderApp.class.getClassLoader()
                .getResource("configuration.groovy")).toURI();
        config.runFrom(configPath);
        config.postProcess();

        CourseGrader grader = new CourseGrader(config);
        grader.gradeAllAssignments();

        ResultsGenerator generator = new ResultsGenerator();
        generator.generateResults(config);
    }
}
