package ru.nsu.abramenko.card;

/** Rank of card.
 *
 */
public enum Rank {
    ACE("Туз", 11),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10),
    TEN("Десятка", 10),
    NINE("Девятка", 9),
    EIGHT("Восьмерка", 8),
    SEVEN("Семерка", 7),
    SIX("Шестерка", 6),
    FIVE("Пятерка", 5),
    FOUR("Четверка", 4),
    THREE("Тройка", 3),
    TWO("Двойка", 2);

    private final String rankname;
    private final  int value;

    /**
     * set name of rank.
     *
     * @param rankname rankname
     * @param value value
     */
    Rank(String rankname, int value) {
        this.rankname = rankname;
        this.value = value;
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
        return value;
    }
}
