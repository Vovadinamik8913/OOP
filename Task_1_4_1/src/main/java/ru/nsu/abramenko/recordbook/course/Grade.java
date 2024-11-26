package ru.nsu.abramenko.recordbook.course;

import ru.nsu.abramenko.recordbook.course.enums.ControlType;
import ru.nsu.abramenko.recordbook.course.enums.Mark;

public record Grade(Mark mark, ControlType controlType) {
}
