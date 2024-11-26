package ru.nsu.abramenko.recordbook;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.abramenko.recordbook.course.Course;
import ru.nsu.abramenko.recordbook.course.Grade;
import ru.nsu.abramenko.recordbook.course.enums.ControlType;
import ru.nsu.abramenko.recordbook.course.enums.Semester;

import java.util.*;


public class RecordBook {
    private final Map<Semester, List<Course>> recordBook;
    @Setter
    @Getter
    private Semester current;
    @Getter
    private boolean isBudget;

    public RecordBook(boolean isBudget) {
        recordBook = new HashMap<>();
        this.isBudget = isBudget;
    }

    public void addSemester(Semester semester) {
        if (containsSemester(semester)) {
            return;
        }
        recordBook.putIfAbsent(semester, new ArrayList<>());
    }

    public void addCourse(Semester semester, Course course) {
        if (containsCourse(semester, course)) {
            return;
        }
        if (!containsSemester(semester)) {
            addSemester(semester);
        }
        recordBook.get(semester).add(course);
    }

    public boolean containsSemester(Semester semester) {
        return recordBook.containsKey(semester);
    }

    public boolean containsCourse(Semester semester, Course course) {
        if (recordBook.containsKey(semester)) {
            return recordBook.get(semester).contains(course);
        }
        return false;
    }

    public void newGrade(Semester semester, String subject, Grade grade) {
        if (containsSemester(semester)) {
            var courses = recordBook.get(semester);
            courses.stream()
                    .filter((course -> course.getSubject().equals(subject)))
                    .findAny().ifPresent(course -> course.addGrade(grade));
        }
    }

    public double calculateGPA() {
        var ref = new Object() {
            long val = 0;
            long cnt = 0;
        };
        recordBook.values().forEach(courses -> {
            var res = courses.stream().filter(course -> course.getControlType() != ControlType.PASS);
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

    public boolean canGetHigherScholarShip() {
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
                .filter(course -> course.getResult() != null && course.getControlType() == ControlType.EXAM);
        return res.allMatch(course -> course.getResult().value() > 3);
    }

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

    public void moveToBudget() {
        if (canGetBudget()) {
            isBudget = true;
        }
    }

    private long cntOfWellMarks() {
        var ref = new Object() {
            long wellMarks = 0;
        };
        recordBook.values()
                .forEach(courses ->
                        ref.wellMarks += courses.stream()
                                .filter(course -> course.getControlType() != ControlType.PASS
                                        && course.getResult() != null && course.getResult().value() == 5).count());
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

    public  boolean canGetRedDiploma() {
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

    @Override
    public String toString() {
        return super.toString();
    }
}
