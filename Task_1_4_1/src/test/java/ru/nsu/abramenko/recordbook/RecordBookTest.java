package ru.nsu.abramenko.recordbook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.recordbook.course.enums.Semester;


class RecordBookTest {
    @Test
    @DisplayName("BasicTest")
    void basicTest() {
        String planPath = ClassLoader.getSystemResource("plan.txt").getPath();
        RecordBook recordBook;
        try {
            recordBook = Reader.readPlanFromFile(planPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String gradesPath = ClassLoader.getSystemResource("grade1.txt").getPath();
        try {
            Reader.readGradesFromFile(gradesPath, recordBook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recordBook.setCurrent(Semester.EIGHTH);
        assertTrue(recordBook.calculateGPA() < 4.2d);
    }

    @Test
    @DisplayName("DiplomaTest")
    void redDiplomaTest() {
        String planPath = ClassLoader.getSystemResource("plan.txt").getPath();
        RecordBook recordBook;
        try {
            recordBook = Reader.readPlanFromFile(planPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String gradesPath = ClassLoader.getSystemResource("grade2.txt").getPath();
        try {
            Reader.readGradesFromFile(gradesPath, recordBook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recordBook.setCurrent(Semester.EIGHTH);
        System.out.println(recordBook.calculateGPA());
        assertTrue(recordBook.redDiploma());
    }

    @Test
    @DisplayName("BudgetTest")
    void budgetTest() {
        String planPath = ClassLoader.getSystemResource("plan.txt").getPath();
        RecordBook recordBook;
        try {
            recordBook = Reader.readPlanFromFile(planPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String gradesPath = ClassLoader.getSystemResource("grade3.txt").getPath();
        try {
            Reader.readGradesFromFile(gradesPath, recordBook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recordBook.setCurrent(Semester.FOURTH);
        assertTrue(recordBook.canGetBudget());
    }

    @Test
    @DisplayName("ScholarShipTest")
    void shipTest() {
        String planPath = ClassLoader.getSystemResource("plan.txt").getPath();
        RecordBook recordBook;
        try {
            recordBook = Reader.readPlanFromFile(planPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String gradesPath = ClassLoader.getSystemResource("grade4.txt").getPath();
        try {
            Reader.readGradesFromFile(gradesPath, recordBook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recordBook.setCurrent(Semester.FOURTH);
        assertFalse(recordBook.higherScholarShip());
    }
}