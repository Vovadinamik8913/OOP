package ru.nsu.abramenko.player;

import java.util.ArrayList;

import ru.nsu.abramenko.card.Rank;
import ru.nsu.abramenko.card.Card;

/** player.
 *
 */
public class Player {
    private final ArrayList<Card> cards;
    private int sum;
    private int cntAce;
    private int firstAce = -1;

    /** create player with 0 cards.
     *
     */
    public Player() {
        cards = new ArrayList<>();
        sum = 0;
        cntAce = 0;
    }

    /** adds one card and count the cum.
     *
     * @param card card
     */
    public void addCard(Card card) {
        cards.add(card);

        sum += card.getValue();
        if (card.getRank() == Rank.ACE) {
            cntAce++;
            if (cntAce > 1) {
                sum -= 10;
                card.changeValue();
            }
            if (firstAce == -1) {
                firstAce = cards.size() - 1;
            }
        }
        if (firstAce != -1 && cards.get(firstAce).getValue() != 1 && sum > 21) {
            sum -= 10;
            cards.get(firstAce).changeValue();
        }
    }

    /** return the last added card.
     *
     * @return last card to string
     */
    public String showLastAddedCard() {
        return cards.get(cards.size() - 1).toString();
    }

    /** returns all cards in string format.
     *
     * @return string of all cards
     */
    public String showCards() {
        String res = "[";
        for (Card card : cards) {
            res += card.toString() + ", ";
        }
        res = res.substring(0, res.length() - 2);
        res += "]";
        if (isAllCardsOpen()) {
            res += " => ";
            res += getSum();
        }
        return res;
    }


    // region Openning Cards

    /** checks if all player cards is open.
     *
     * @return true or false
     */
    public boolean isAllCardsOpen() {
        for (Card card : cards) {
            if (!card.isOpen()) {
                return false;
            }
        }
        return true;
    }

    /** open all cards.
     *
     */
    public void openCards() {
        for (Card card : cards) {
            if (!card.isOpen()) {
                card.open();
            }
        }
    }

    /** open only one card.
     *
     * @param position position in cards
     */
    public void openCard(int position) {
        cards.get(position).open();
    }

    /** opens last card.
     *
     */
    public void openLastCard() {
        cards.get(cards.size() - 1).open();
    }
    //endregion


    /** return sum of card values.
     *
     * @return sum of card values
     */
    public int getSum() {
        return sum;
    }
}
