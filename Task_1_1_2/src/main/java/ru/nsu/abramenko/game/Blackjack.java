package ru.nsu.abramenko.game;

import javax.swing.plaf.synth.Region;
import java.util.ArrayList;

import ru.nsu.abramenko.card.Card;
import ru.nsu.abramenko.card.Deck;
import ru.nsu.abramenko.player.Player;

public class Blackjack {
    private Player player;
    private Player dealer;

    private int playerWins;
    private int dealerWins;
    private int cntOfRounds;

    private boolean isPlayerMove;
    private boolean isRoundEnded;
    private Deck deck;

    public Blackjack() {
        playerWins = 0;
        dealerWins = 0;
        cntOfRounds = 0;
    }

    public void newRound() {
        deck =  new Deck();
        player = new Player();
        dealer = new Player();

        player.addCard(deck.getCard());
        dealer.addCard(deck.getCard());
        player.addCard(deck.getCard());
        dealer.addCard(deck.getCard());

        player.openCards();
        dealer.openCard(0);

        isRoundEnded = false;
        isPlayerMove = true;

        cntOfRounds++;
    }

    public String getLastAddedCard() {
        if (isPlayerMove) {
            return player.showLastAddedCard();
        } else {
            return dealer.showLastAddedCard();
        }
    }

    public void move(int move) {
            switch (move) {
                case 1:
                    if (isPlayerMove) {
                        player.addCard(deck.getCard());
                        player.openLastCard();
                    } else {
                        dealer.addCard(deck.getCard());
                        dealer.openLastCard();
                    }
                    break;
                case 0:
                    if (isPlayerMove) {
                        isPlayerMove = false;
                        dealer.openCard(1);
                    } else {
                        isRoundEnded = true;
                    }
                    break;
                default:
                    break;
            }
    }

    //region Logic

    public boolean doesPlayerHaveBlackjack() {
        if (player.getSum() == 21) {
            isPlayerMove = false;
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    public boolean doesPlayerHaveOver() {
        if (player.getSum() > 21) {
            isPlayerMove = false;
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    public boolean doesDealerHaveBlackjack() {
        if (dealer.getSum() == 21) {
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    public boolean doesDealerHaveOver() {
        if (dealer.getSum() > 21) {
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    public int whoIsWinner() {
        if (doesPlayerHaveBlackjack()) {
            playerWins++;
            return 1;
        }
        if (doesDealerHaveBlackjack()) {
            dealerWins++;
            return 1;
        }

        if (doesPlayerHaveOver()) {
            dealerWins++;
            return -1;
        }
        if (doesDealerHaveOver()) {
            playerWins++;
            return 1;
        }

        if (player.getSum() < 22 && dealer.getSum() < 22) {
            if (player.getSum() > dealer.getSum()) {
                playerWins++;
                return 1;
            }
            if (player.getSum() < dealer.getSum()) {
                dealerWins++;
                return -1;
            }
        }

        return 0;
    }
    //endregion

    //region Getters
    public String getPlayerCards() {
        return player.showCards();
    }
    public int getPlayerSum() {
        return player.getSum();
    }

    public String getDealerCards() {
        return dealer.showCards();
    }

    public int getDealerSum() {
        return dealer.getSum();
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public int getDealerWins() {
        return dealerWins;
    }

    public boolean isPlayerMove() {
        return isPlayerMove;
    }

    public boolean isRoundEnded() {
        return isRoundEnded;
    }

    public int getCntOfRounds() {
        return cntOfRounds;
    }

    //endregion
}
