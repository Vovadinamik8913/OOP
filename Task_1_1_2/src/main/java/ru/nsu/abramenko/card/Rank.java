package ru.nsu.abramenko.card;

/** Rank of card.
 *
 */
public enum Rank {
    ACE("Туз"),
    JACK("Валет"),
    QUEEN("Дама"),
    KING("Король"),
    TEN("Десятка"),
    NINE("Девятка"),
    EIGHT("Восьмерка"),
    SEVEN("Семерка"),
    SIX("Шестерка"),
    FIVE("Пятерка"),
    FOUR("Четверка"),
    THREE("Тройка"),
    TWO("Двойка");

    private final String rankname;

    /** set name of rank.
     *
     * @param rankname
     */
    Rank(String rankname) {
        this.rankname = rankname;
    }

    /** convert rank to string.
     *
     * @return rank to string
     */
    @Override
    public String toString() {
        return  rankname;
    }

    /** get value of Rank.
     *
     * @return value
     */
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
