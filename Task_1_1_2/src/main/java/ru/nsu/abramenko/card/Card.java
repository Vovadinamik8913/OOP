package ru.nsu.abramenko.card;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private int value;
    private boolean isOpen;

    public Card(Suit suit,Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isOpen = false;
        value = this.rank.getValue();
    }

    public Card(Suit suit,Rank rank, boolean isOpen) {
        this.suit = suit;
        this.rank = rank;
        this.isOpen = isOpen;
        value = this.rank.getValue();
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public void changeValue() {
        if (rank == Rank.ACE) {
            this.value = 1;
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }

    @Override
    public String toString() {
        if (isOpen) {
            return rank.toString() + " " + suit.toString() + " (" + value + ")";
        } else {
            return "<закрытая карта>";
        }
    }
}
