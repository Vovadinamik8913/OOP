package ru.nsu.abramenko.recordbook.course;

import ru.nsu.abramenko.recordbook.course.enums.ControlType;
import ru.nsu.abramenko.recordbook.course.enums.Mark;

/** grade for that controlltype.
 *
 * @param mark mark
 * @param controlType type of control
 */
public record Grade(Mark mark, ControlType controlType) {
}
