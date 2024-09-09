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
        switch (this) {
            case ACE: return "Туз";
            case JACK: return "Валет";
            case QUEEN: return "Дама";
            case KING: return "Король";
            case TEN: return "Десятка";
            case NINE: return "Девятка";
            case EIGHT: return "Восьмерка";
            case SEVEN: return "Семерка";
            case SIX: return "Шестерка";
            case FIVE: return "Пятерка";
            case FOUR: return "Четверка";
            case THREE: return "Тройка";
            case TWO: return "Двойка";
            default: throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        switch (this) {
            case ACE: return 11;
            case JACK: return 10;
            case QUEEN: return 10;
            case KING: return 10;
            case TEN: return 10;
            case NINE: return 9;
            case EIGHT: return 8;
            case SEVEN: return 7;
            case SIX: return 6;
            case FIVE: return 5;
            case FOUR: return 4;
            case THREE: return 3;
            case TWO: return 2;
            default: throw new IllegalArgumentException();
        }
    }
}
