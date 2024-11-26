package ru.nsu.abramenko.recordbook.course.enums;

public enum Semester {
    FIRST("1 семестр"),
    SECOND("2 семестр"),
    THIRD("3 семестр"),
    FOURTH("4 семестр"),
    FIFTH("5 семестр"),
    SIXTH("6 семестр"),
    SEVENTH("7 семестр"),
    EIGHTH("8 семестр");

    private final String semesterName;

    Semester(String semesterName) {
        this.semesterName = semesterName;
    }

    @Override
    public String toString() {
        return semesterName;
    }
}
