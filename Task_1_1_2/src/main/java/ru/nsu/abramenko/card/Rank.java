package ru.nsu.abramenko.card;

public enum Rank {
    ACE,
    JACK,
    QUEEN,
    KING,
    TEN,
    NINE,
    EIGHT,
    SEVEN,
    SIX,
    FIVE,
    FOUR,
    THREE,
    TWO;

    @Override
    public String toString() {
        return switch (this) {
            case ACE -> "Туз";
            case JACK -> "Валет";
            case QUEEN -> "Дама";
            case KING -> "Король";
            case TEN -> "Десятка";
            case NINE -> "Девятка";
            case EIGHT -> "Восьмерка";
            case SEVEN -> "Семерка";
            case SIX -> "Шестерка";
            case FIVE -> "Пятерка";
            case FOUR -> "Четверка";
            case THREE -> "Тройка";
            case TWO -> "Двойка";
            default -> throw new IllegalArgumentException();
        };
    }

    public int getValue() {
        return switch (this) {
            case ACE -> 11;
            case JACK -> 10;
            case QUEEN -> 10;
            case KING -> 10;
            case TEN -> 10;
            case NINE -> 9;
            case EIGHT -> 8;
            case SEVEN -> 7;
            case SIX -> 6;
            case FIVE -> 5;
            case FOUR -> 4;
            case THREE -> 3;
            case TWO -> 2;
            default -> throw new IllegalArgumentException();
        };
    }
}
