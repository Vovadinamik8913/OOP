package ru.nsu.abramenko.recordbook.course.enums;

public enum ControlType {
    PASS("Зачет"),
    DIF_PASS("Дифференцированный зачет"),
    EXAM("Экзамен"),
    ASSIGNMENT("Задание"),
    TEST("Контрольная"),
    COLLOQUIUM("Коллоквиум"),
    PRACTICE_DEFENSE("Защита отчета по практике"),
    THESIS_DEFENSE("Защита ВКР");

    private final String description;

    ControlType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  description;
    }
}
