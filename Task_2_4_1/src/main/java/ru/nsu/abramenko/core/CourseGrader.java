package ru.nsu.abramenko.core;

import ru.nsu.abramenko.dsl.config.Course;
import ru.nsu.abramenko.dsl.model.Student;
import ru.nsu.abramenko.dsl.task.Assignment;
import ru.nsu.abramenko.dsl.task.Deadline;
import ru.nsu.abramenko.tools.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CourseGrader {
    private static final String TEST_RESULTS = "/build/test-results/test/";
    private static final String DOCS_DIR = "/build/docs/javadoc/";
    private static final String LABS_DIR;

    static {
        LABS_DIR = ClassLoader.getSystemResource("").toString() + File.separator + "labs";
    }
    
    private final Course config;

    public CourseGrader(Course config) {
        this.config = config;
    }

    public void gradeAllAssignments() {
        createAssignments();
        processAssignments();
    }

    private void createAssignments() {
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

    private void processAssignments() {
        config.getGroups().forEach(group -> 
            group.getStudents().forEach(student -> {
                boolean downloaded = Installer.download(
                        student.getRepo(),
                        student.getUsername(),
                        config.getSettings().getBranch()
                );

                student.getAssignments().forEach(assignment -> 
                    processAssignment(student, assignment, downloaded));
            })
        );
    }

    private void processAssignment(Student student, Assignment assignment, boolean downloaded) {
        if (!downloaded) {
            assignment.setBuild("Failed to download");
            return;
        }

        String studentRepoPath = LABS_DIR + student.getUsername();
        
        Deadline deadline = config.getDeadlines().stream()
                .filter(d -> d.getInfo().getId().equals(assignment.getTask().getId()))
                .findFirst().orElse(null);

        if (deadline == null) {
            return;
        }

        String taskPath = studentRepoPath + "/" + assignment.getTask().getId();
        processTask(taskPath, assignment, deadline);
    }

    private void processTask(String taskPath, Assignment assignment, Deadline deadline) {
        try {
            File taskDir = new File(taskPath);
            if (!taskDir.exists()) {
                assignment.setBuild("Missing");
                return;
            }
            if (config.getSettings().isCheckTests() && !Tester.run(taskPath)) {
                assignment.setBuild("Failed to build");
                return;
            }
        } catch (Exception ignored) {
            assignment.setBuild("Error");
            return;
        }

        assignment.setBuild("Successfully");

        if (config.getSettings().isCheckStyle()) {
            boolean stylePass = StyleChecker.check(taskPath);
            assignment.setStyle(stylePass ? "Passed" : "Failed");
        }

        if (config.getSettings().isCheckDocsAndBuild()) {
            analyzeTestsAndDocs(taskPath, assignment);
        }

        calculatePoints(taskPath, assignment, deadline);
    }

    private void analyzeTestsAndDocs(String taskPath, Assignment assignment) {
        TestsAndDocs analysis = new TestsAndDocs();
        File testResFolder = new File(taskPath + TEST_RESULTS);
        
        if (testResFolder.exists() && testResFolder.isDirectory()) {
            File[] xmlFiles = testResFolder.listFiles((dir, name) -> 
                    name.toLowerCase().endsWith(".xml"));
            if (xmlFiles != null && xmlFiles.length > 0) {
                analysis.analyze(
                    taskPath + TEST_RESULTS + xmlFiles[0].getName(),
                    taskPath + DOCS_DIR
                );
                
                updateAssignmentWithAnalysis(assignment, analysis);
            }
        }
    }

    private void updateAssignmentWithAnalysis(Assignment assignment, TestsAndDocs analysis) {
        assignment.setDocs(analysis.getDocumentationExists());
        assignment.setTestsPassed(analysis.getPassedTests());
        assignment.setTestsTotal(analysis.getTotalTests());
        assignment.setTestsIgnored(analysis.getIgnoredTests());
        
        boolean allPassed = analysis.getPassedTests() == analysis.getTotalTests();
        boolean hasDocumentation = "Generated".equals(analysis.getDocumentationExists());
        assignment.setPoints(allPassed && hasDocumentation ? 1 : 0);
    }

    private void calculatePoints(String taskPath, Assignment assignment, Deadline deadline) {
        if (assignment.getPoints() > 0) {
            boolean allPassed = !config.getSettings().isCheckTests() 
                    || assignment.getTestsPassed() == assignment.getTestsTotal();
            boolean hasDocumentation = !config.getSettings().isCheckDocsAndBuild() 
                    || "Generated".equals(assignment.getDocs());
            boolean hasStyle = !config.getSettings().isCheckStyle() 
                    || "Passed".equals(assignment.getStyle());
            
            if (allPassed && hasDocumentation && hasStyle) {
                CommitChecker.CommitDates commitDates = CommitChecker.getCommitsDates(taskPath);
                calculatePointsByDeadlines(assignment, deadline, commitDates);
            } else {
                assignment.setPoints(0);
            }
        }
    }

    private void calculatePointsByDeadlines(Assignment assignment, Deadline deadline, 
            CommitChecker.CommitDates commitDates) {
        double point = 0;
        if (commitDates.getFirstCommit().isBefore(deadline.getSoftDeadline())
                || commitDates.getFirstCommit().isEqual(deadline.getSoftDeadline())) {
            point += (double) assignment.getTask().getPoints() / 2;
        }
        if (commitDates.getLastCommit().isBefore(deadline.getHardDeadline())
                || commitDates.getLastCommit().isEqual(deadline.getHardDeadline())) {
            point += (double) assignment.getTask().getPoints() / 2;
        }
        assignment.setPoints(point);
    }
}
