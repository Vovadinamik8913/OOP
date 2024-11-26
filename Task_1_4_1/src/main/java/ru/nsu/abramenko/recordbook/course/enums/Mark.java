package ru.nsu.abramenko.recordbook.course.enums;

public enum Mark {
    PASS(5, "PASS"),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    NA(0, "NA");

    private final Integer mark;
    private final String description;
    Mark(int mark) {
        this.mark = mark;
        description = Integer.toString(mark);
    }
    Mark(int mark, String description) {
        this.mark = mark;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public int value() {
        return mark;
    }
}
