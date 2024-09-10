package ru.nsu.abramenko.card;

/** suit of card.
 *
 */
public enum Suit{
    SPADE,
    DIAMOND,
    HEART,
    CLUB;

    /** suit to string.
     *
     * @return
     */
    @Override
    public String toString() {
        switch (this) {
            case CLUB: return "♣";
            case HEART: return "♥";
            case DIAMOND: return "♦";
            case SPADE: return "♠";
            default: throw new IllegalArgumentException();
        }
    }
}
