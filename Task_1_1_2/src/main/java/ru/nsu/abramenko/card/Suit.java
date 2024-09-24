package ru.nsu.abramenko.card;

/** suit of card.
 *
 */
public enum Suit {
    SPADE("♠"),
    DIAMOND("♦"),
    HEART("♥"),
    CLUB("♣");

    private final String suitname;

    /** constructor of suit.
     *
     * @param suitname suit name
     */
    Suit(String suitname) {
        this.suitname = suitname;
    }

    /** suit to string.
     *
     * @return name of suit
     */
    @Override
    public String toString() {
        return suitname;
    }
}
