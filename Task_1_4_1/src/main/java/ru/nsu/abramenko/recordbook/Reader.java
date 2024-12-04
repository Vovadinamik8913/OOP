package ru.nsu.abramenko.recordbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import ru.nsu.abramenko.recordbook.course.Course;
import ru.nsu.abramenko.recordbook.course.Grade;
import ru.nsu.abramenko.recordbook.course.enums.ControlType;
import ru.nsu.abramenko.recordbook.course.enums.Mark;
import ru.nsu.abramenko.recordbook.course.enums.Semester;


/** class for reading file with grades and plans.
 *
 */
public class Reader {
    /** creates plan in recordbook.
     *
     * @param path path to plan file
     * @return recordbook with plan
     * @throws IOException exception
     */
    public static RecordBook readPlanFromFile(String path) throws IOException {
        RecordBook recordBook = new RecordBook(false);
        File file = new File(path);
        List<String> line = Files.readAllLines(file.toPath());
        for (String pair : line) {
            String[] parts = pair.split(";");
            if (parts.length == 1) {
                continue;
            }
            Semester semester = Semester.valueOf(parts[0]);
            Course course = new Course(
                    parts[1],
                    ControlType.values()[Integer.parseInt(parts[2])]
            );
            recordBook.addCourse(semester, course);
        }
        return recordBook;
    }

    /** add grades in record.
     *
     * @param path path to grades file
     * @param recordBook your recordBook
     * @throws IOException exception
     */
    public static void readGradesFromFile(String path, RecordBook recordBook) throws IOException {
        File file = new File(path);
        List<String> line = Files.readAllLines(file.toPath());
        for (String pair : line) {
            String[] parts = pair.split(";");
            if (parts.length == 1) {
                continue;
            }
            Semester semester = Semester.valueOf(parts[0]);
            String subject = parts[1];
            Grade grade = new Grade(
                    Mark.valueOf(parts[2]),
                    ControlType.valueOf(parts[3])
            );
            recordBook.newGrade(semester, subject, grade);
        }
    }
}
