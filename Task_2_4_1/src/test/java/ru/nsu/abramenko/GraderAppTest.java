package ru.nsu.abramenko;

import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.core.CourseGrader;
import ru.nsu.abramenko.core.ResultsGenerator;
import ru.nsu.abramenko.dsl.config.Course;

import java.net.URI;
import java.util.Objects;

class GraderAppTest {
    @Test
    void testRun() {
        try {
            Course config = new Course();
            URI configPath = Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResource("configuration.groovy")).toURI();
            config.runFrom(configPath);
            config.postProcess();

            CourseGrader grader = new CourseGrader(config);
            grader.gradeAllAssignments();

            ResultsGenerator generator = new ResultsGenerator();
            generator.generateResults(config);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}