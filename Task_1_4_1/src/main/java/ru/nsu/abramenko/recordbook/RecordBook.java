package ru.nsu.abramenko.recordbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.abramenko.recordbook.course.Course;
import ru.nsu.abramenko.recordbook.course.Grade;
import ru.nsu.abramenko.recordbook.course.enums.ControlType;
import ru.nsu.abramenko.recordbook.course.enums.Semester;

/** recordBook of student.
 *
 */
public class RecordBook {
    private final Map<Semester, List<Course>> recordBook;
    @Setter
    @Getter
    private Semester current;
    @Getter
    @Setter
    private boolean isBudget;

    public RecordBook(boolean isBudget) {
        recordBook = new HashMap<>();
        this.isBudget = isBudget;
    }

    /** add semester.
     *
     * @param semester semestr
     */
    public void addSemester(Semester semester) {
        if (containsSemester(semester)) {
            return;
        }
        recordBook.putIfAbsent(semester, new ArrayList<>());
    }

    /** add course.
     *
     * @param semester semestr
     * @param course course
     */
    public void addCourse(Semester semester, Course course) {
        if (containsCourse(semester, course)) {
            return;
        }
        if (!containsSemester(semester)) {
            addSemester(semester);
        }
        recordBook.get(semester).add(course);
    }

    /** contains semestr .
     *
     * @param semester semestr
     * @return true or false
     */
    public boolean containsSemester(Semester semester) {
        return recordBook.containsKey(semester);
    }

    /** contains course.
     *
     * @param semester sem
     * @param course course
     * @return true or false
     */
    public boolean containsCourse(Semester semester, Course course) {
        if (recordBook.containsKey(semester)) {
            return recordBook.get(semester).contains(course);
        }
        return false;
    }

    /** add grade.
     *
     * @param semester sem
     * @param subject subject
     * @param grade grade
     */
    public void newGrade(Semester semester, String subject, Grade grade) {
        if (containsSemester(semester)) {
            var courses = recordBook.get(semester);
            courses.stream()
                    .filter((course -> course.getSubject().equals(subject)))
                    .findAny().ifPresent(course -> course.addGrade(grade));
        }
    }

    /** get average.
     *
     * @return GPA
     */
    public double calculateGpa() {
        var ref = new Object() {
            long val = 0;
            long cnt = 0;
        };
        recordBook.values().forEach(courses -> {
            var res = courses.stream()
                    .filter(course -> course.getControlType() != ControlType.PASS);
            res.filter(course -> course.getResult() != null).forEach(course -> {
                ref.val += course.getResult().value();
                ref.cnt++;
            });
        });
        if (ref.cnt == 0) {
            return 0;
        }
        return ref.val * 1.d / ref.cnt;
    }

    /** can get higherScholarShip.
     *
     * @return true or false
     */
    public boolean higherScholarShip() {
        if (!containsSemester(current)) {
            return false;
        }
        List<Course> sem = recordBook.get(current);
        if (sem.isEmpty()) {
            return false;
        }
        return sem.stream().filter(course -> course.getResult() != null)
                .allMatch(course -> course.getResult().value() == 5);
    }

    private boolean isGoodExams(Semester semester) {
        if (!containsSemester(semester)) {
            return false;
        }
        List<Course> sem = recordBook.get(semester);
        if (sem.isEmpty()) {
            return false;
        }
        var res = sem.stream()
                .filter(course -> course.getResult() != null
                        && course.getControlType() == ControlType.EXAM);
        return res.allMatch(course -> course.getResult().value() > 3);
    }

    /** is there a chance for budget.
     *
     * @return true or false
     */
    public boolean canGetBudget() {
        if (isBudget) {
            return false;
        }
        int ind = Arrays.stream(Semester.values()).toList().indexOf(current);
        return switch (ind) {
            case 0 -> isGoodExams(current);
            case 1 -> isGoodExams(current)
                    && isGoodExams(Semester.values()[ind - 1]);
            default -> isGoodExams(Semester.values()[ind - 2])
                    && isGoodExams(Semester.values()[ind - 1]);
        };
    }

    private long cntOfWellMarks() {
        var ref = new Object() {
            long wellMarks = 0;
        };
        recordBook.values()
                .forEach(courses ->
                        ref.wellMarks += courses.stream()
                                .filter(course -> course.getControlType() != ControlType.PASS
                                        && course.getResult() != null
                                        && course.getResult().value() == 5).count());
        return ref.wellMarks;
    }

    private boolean haveBadMarks() {
        return recordBook.values().stream()
                .anyMatch(courses ->
                        courses.stream()
                                .filter(course -> course.getResult() != null)
                                .anyMatch(course -> course.getResult().value() == 3)
                );
    }

    /** can get a red diploma.
     *
     * @return true or false
     */
    public  boolean redDiploma() {
        boolean badMarks = haveBadMarks();
        if (badMarks) {
            return false;
        }

        long cnt = recordBook.values().size();
        long wellMarks = cntOfWellMarks();
        if (wellMarks * 1.d / cnt < 0.75d) {
            return false;
        }

        Course vkr = recordBook.get(Semester.EIGHTH).stream()
                .filter(course -> course.getSubject().equals("ВКР")).findAny().get();

        return vkr.getResult().value() == 5;
    }

    private long countOfControlInSemester(Semester semester, ControlType controlType) {
        if (!containsSemester(semester)) {
            return 0;
        }
        var sem = recordBook.get(semester);
        return sem.stream().mapToLong((course) -> course.countOfControl(controlType)).sum();
    }

    @Override
    public String toString() {
        String res = "";
        return res;
    }
}
