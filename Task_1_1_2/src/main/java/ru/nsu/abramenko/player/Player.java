package ru.nsu.abramenko.player;

import ru.nsu.abramenko.card.Rank;
import ru.nsu.abramenko.card.Card;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cards;
    private int sum;
    private int cntAce;
    private int firstAce = -1;

    public Player() {
        cards = new ArrayList<>();
        sum = 0;
        cntAce = 0;
    }

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

    public String showLastAddedCard() {
        return cards.getLast().toString();
    }

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

    //region Openning Cards
    public boolean isAllCardsOpen() {
        for (Card card : cards){
            if (!card.isOpen()) {
                return false;
            }
        }
        return true;
    }

    public void openCards() {
        for (Card card : cards) {
            if (!card.isOpen()) {
                card.open();
            }
        }
    }

    public void openCard(int position) {
        cards.get(position).open();
    }

    public void openLastCard() {
        cards.getLast().open();
    }
    //endregion


    public int getSum() {
        //count();
        return sum;
    }
}
