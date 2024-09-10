package ru.nsu.abramenko.card;

/** class Card with Rank, suit and Value.
 *
 */
public class Card {
    private final Suit suit;
    private final Rank rank;
    private int value;
    private boolean isOpen;

    /** Constructor of closed Card.
     *
     * @param suit
     * @param rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isOpen = false;
        value = this.rank.getValue();
    }

    /** get Value of Card.
     *
     * @return value of Card
     */
    public int getValue() {
        return value;
    }


    /** change value For Ace from 11 to 1.
     *
     */
    public void changeValue() {
        if (rank == Rank.ACE) {
            this.value = 1;
        }
    }

    /** is this card open.
     *
     * @return true or false
     */
    public boolean isOpen() {
        return isOpen;
    }

    /** open this card
     *
     */
    public void open() {
        isOpen = true;
    }

    /** convert card to string.
     *
     * @return string of rank, suit and value
     */
    @Override
    public String toString() {
        if (isOpen) {
            return rank.toString() + " " + suit.toString() + " (" + value + ")";
        } else {
            return "<закрытая карта>";
        }
    }


    /** rank of card.
     *
     * @return rank of card
     */
    public Rank getRank() {
        return rank;
    }
}
