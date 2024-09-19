package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.abramenko.card.Card;
import ru.nsu.abramenko.card.Deck;
import ru.nsu.abramenko.card.Rank;
import ru.nsu.abramenko.card.Suit;
import ru.nsu.abramenko.player.Player;

import java.util.ArrayList;

class  MainTest {
    @Test
    void mainCheck() {
        Main.main(null);
        assertTrue(true);
    }

    @Test
    @DisplayName("CardTest")
    void cardTest() {
        int cnt = 0;
        Card card1 = new Card(Suit.CLUB, Rank.ACE);
        cnt += card1.getValue();
        card1.open();
        Card card2 = new Card(Suit.SPADE, Rank.ACE);
        card2.changeValue();
        cnt += card2.getValue();
        Card card3 = new Card(Suit.HEART, Rank.NINE);
        card3.changeValue();
        cnt += card3.getValue();
        assertTrue(21 == cnt && card1.isOpen());
    }

    @Test
    @DisplayName("DeckTest")
    void deckTest() {
        Deck deck = new Deck();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(deck.getCard());
        cards.add(deck.getCard());
        assertFalse(cards.stream().allMatch((card) -> card.isOpen()));
    }

    @Test
    @DisplayName("PlayerTest")
    void playerTest() {
        Player player = new Player();
        Deck deck = new Deck();
        player.addCard(deck.getCard());
        player.addCard(deck.getCard());
        player.openLastCard();
        assertTrue(!player.isAllCardsOpen() && player.getSum() >= 2);
    }
}