package ru.nsu.abramenko.recordbook.course;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.nsu.abramenko.recordbook.course.enums.ControlType;
import ru.nsu.abramenko.recordbook.course.enums.Mark;

/** describes subject, his controls and res mark.
 *
 */
@EqualsAndHashCode
public class Course {
    @Getter
    private final String subject;
    @Getter
    private final ControlType controlType;
    private final List<Grade> grades;
    @Getter
    private Mark result;

    public Course(String subject, ControlType controlType) {
        this.subject = subject;
        this.controlType = controlType;
        grades = new ArrayList<>();
        result = null;
    }

    /** add new grade.
     *
     * @param grade grade
     */
    public void addGrade(Grade grade) {
        if (grade.controlType() == controlType) {
            result = grade.mark();
        }
        grades.add(grade);
    }

    /** count amount of controls.
     *
     * @param controlType type of control
     * @return count
     */
    public long countOfControl(ControlType controlType) {
        if (this.controlType == controlType) {
            return 1;
        }
        return grades.stream().filter((grade -> grade.controlType() == controlType)).count();
    }
}
