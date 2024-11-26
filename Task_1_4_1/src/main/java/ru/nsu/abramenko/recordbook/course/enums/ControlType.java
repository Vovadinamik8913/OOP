package ru.nsu.abramenko.recordbook.course.enums;

/** type of control in university.
 *
 */
public enum ControlType {
    PASS("   Зачет"),
    DIF_PASS("ДифЗачет"),
    EXAM("   Экзамен"),
    ASSIGNMENT("   Задание"),
    TEST("Контрольная"),
    COLLOQUIUM("Коллоквиум"),
    PRACTICE_DEFENSE("Практики"),
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
