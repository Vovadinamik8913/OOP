package ru.nsu.abramenko.card;

public enum Suit{
    SPADE,
    DIAMOND,
    HEART,
    CLUB;

    @Override
    public String toString() {
        return switch (this) {
            case CLUB -> "♣";
            case HEART -> "♥";
            case DIAMOND -> "♦";
            case SPADE -> "♠";
            default -> throw new IllegalArgumentException();
        };
    }
}
