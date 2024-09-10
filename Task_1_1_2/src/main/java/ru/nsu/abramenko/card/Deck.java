package ru.nsu.abramenko.card;

import java.util.ArrayList;
import java.util.Random;

/** 52 cards in deck
 *
 */
public class Deck {
    private final ArrayList<Card> cards;

    /** generate deck with 52 cards
     *
     */
    public Deck() {
        cards = new ArrayList<>();
        Suit[] suits = new Suit[] {Suit.CLUB, Suit.HEART, Suit.DIAMOND, Suit.SPADE};
        Rank[] ranks = new Rank[]{ Rank.ACE, Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN, Rank.NINE,
                Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO};
        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    /** shuffle deck
     *
     */
    private void shuffle() {
        Random random = new Random();
        int ind = 0;
        for (int i = 0; i < cards.size(); i++) {
            ind = random.nextInt(cards.size());
            Card tmp = cards.get(i);
            cards.set(i, cards.get(ind));
            cards.set(ind, tmp);
        }
    }

    /** get card from the top of deck
     *
     * @return card
     */
    public Card getCard() {
        return cards.remove(0);
    }
}
