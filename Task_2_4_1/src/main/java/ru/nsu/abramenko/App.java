package ru.nsu.abramenko;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import ru.nsu.abramenko.dsl.*;
import ru.nsu.abramenko.util.*;

public class App {
    private static final String TEST_RESULTS = "/build/test-results/test/";
    private static final String DOCS_DIR = "/build/docs/javadoc/";
    private static final String LABS_DIR = "src/main/resources/labs/";
    private static Settings settings;

    @SneakyThrows
    public static void main(String[] args) {
        Course config = new Course();
        URI configPath = Objects.requireNonNull(App.class.getClassLoader()
                .getResource("configuration.groovy")).toURI();
        config.runFrom(configPath);
        config.postProcess();
        createAssignments(config);
        processAssignments(config);
        TableBuild.generateHtmlTableChart(config.getGroups(), config.getSettings());
        cleanupLabs();
    }

    private static void createAssignments(Course config) {
        settings = config.getSettings();
        config.getGroups().forEach(group -> 
            group.getStudents().forEach(student -> {
                List<Assignment> assignments = new ArrayList<>();

                config.getDeadlines().forEach(deadline -> {
                    Assignment assignment = new Assignment();
                    assignment.setTask(deadline.getInfo());
                    assignment.setBuild("");
                    assignment.setDocs("");
                    assignment.setTestsTotal(0);
                    assignment.setTestsPassed(0);
                    assignment.setTestsIgnored(0);
                    assignment.setPoints(0);
                    assignments.add(assignment);
                });
                
                student.setAssignments(assignments);
            })
        );
    }

    private static void processAssignments(Course config) {
        config.getGroups().forEach(group -> 
            group.getStudents().forEach(student -> {
                boolean downloaded = Download.download(
                    student.getRepo(), 
                    student.getUsername(),
                    config.getSettings().getBranch()
                );

                student.getAssignments().forEach(assignment -> 
                    processAssignment(student, assignment, downloaded, config));
            })
        );
    }

    private static void processAssignment(Student student, Assignment assignment, boolean downloaded, Course config) {
        if (!downloaded) {
            assignment.setBuild("Failed to download");
            return;
        }

        String taskPath = LABS_DIR + student.getUsername() + "/" + assignment.getTask().getId();
        File taskDir = new File(taskPath);
        
        if (!taskDir.exists()) {
            assignment.setBuild("Missing");
            return;
        }

        if (settings.isCheckTests() && !Run.run(taskPath)) {
            assignment.setBuild("Failed to build");
            return;
        }

        assignment.setBuild("Successfully");

        if (settings.isCheckStyle()) {
            boolean stylePass = checkStyle(taskPath);
            assignment.setStyle(stylePass ? "Passed" : "Failed");
        }

        if (settings.isCheckDocsAndBuild()) {
            analyzeTestsAndDocs(taskPath, assignment);
        }

        if (assignment.getPoints() > 0) {
            boolean allPassed = !settings.isCheckTests() || assignment.getTestsPassed() == assignment.getTestsTotal();
            boolean hasDocumentation = !settings.isCheckDocsAndBuild() || "Generated".equals(assignment.getDocs());
            boolean hasStyle = !settings.isCheckStyle() || "Passed".equals(assignment.getStyle());
            
            if (allPassed && hasDocumentation && hasStyle) {
                CommitChecker.CommitDates commitDates = CommitChecker.getCommitsDates(taskPath);
                Deadline deadline = config.getDeadlines().stream()
                        .filter(d -> d.getInfo().getId().equals(assignment.getTask().getId()))
                        .findFirst().orElse(null);
                double point = 0;
                if (deadline != null) {
                    if (commitDates.getFirstCommit().isBefore(deadline.getSoftDeadline())) {
                        point += (double) assignment.getTask().getPoints() / 2;
                    }
                    if (commitDates.getLastCommit().isBefore(deadline.getHardDeadline())) {
                        point += (double) assignment.getTask().getPoints() / 2;
                    }
                }
                assignment.setPoints(point);
            } else {
                assignment.setPoints(0);
            }
        }
    }

    @SneakyThrows
    private static boolean checkStyle(String taskPath) {
        return Style.check(taskPath);
    }

    @SneakyThrows
    private static void analyzeTestsAndDocs(String taskPath, Assignment assignment) {
        TestsAndDocs analysis = new TestsAndDocs();
        File testResFolder = new File(taskPath + TEST_RESULTS);
        
        if (testResFolder.exists() && testResFolder.isDirectory()) {
            File[] xmlFiles = testResFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
            if (xmlFiles != null && xmlFiles.length > 0) {
                analysis.analyze(
                    taskPath + TEST_RESULTS + xmlFiles[0].getName(),
                    taskPath + DOCS_DIR
                );
                
                assignment.setDocs(analysis.getDocumentationExists());
                assignment.setTestsPassed(analysis.getPassedTests());
                assignment.setTestsTotal(analysis.getTotalTests());
                assignment.setTestsIgnored(analysis.getIgnoredTests());
                
                boolean allPassed = analysis.getPassedTests() == analysis.getTotalTests();
                boolean hasDocumentation = "Generated".equals(analysis.getDocumentationExists());
                assignment.setPoints(allPassed && hasDocumentation ? 1 : 0);
            }
        }
    }

    private static void cleanupLabs() {
        File labsDir = new File(LABS_DIR);
        if (labsDir.exists()) {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(labsDir);
            } catch (Exception e) {
                System.err.println("Failed to clean up labs directory: " + e.getMessage());
            }
        }
    }

    private static void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                }
                if (!file.delete()) {
                    return;
                }
            }
        }
        dir.delete();
    }
}
