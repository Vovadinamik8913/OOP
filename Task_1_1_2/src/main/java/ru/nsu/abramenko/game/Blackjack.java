package ru.nsu.abramenko.game;


import ru.nsu.abramenko.card.Deck;
import ru.nsu.abramenko.player.Player;

/** blackjack the Game
 *
 */
public class Blackjack {
    private Player player;
    private Player dealer;

    private int playerWins;
    private int dealerWins;
    private int cntOfRounds;

    private boolean isPlayerMove;
    private boolean isRoundEnded;
    private Deck deck;

    /** constructor
     *
     */
    public Blackjack() {
        playerWins = 0;
        dealerWins = 0;
        cntOfRounds = 0;
    }

    /** starting new round
     *
     */
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

    /** get last added card
     * @return  card to stiring
     */
    public String getLastAddedCard() {
        if (isPlayerMove) {
            return player.showLastAddedCard();
        } else {
            return dealer.showLastAddedCard();
        }
    }

    /**moves in game
     * 1 - adding card to curren player card
     * 0 - ending the current player move
     * @param move
     */
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

    /** is player sum == 21
     * @return true or false
     */
    public boolean doesPlayerHaveBlackjack() {
        if (player.getSum() == 21) {
            isPlayerMove = false;
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    /** is player sum > 21
     * @return true or false
     */
    public boolean doesPlayerHaveOver() {
        if (player.getSum() > 21) {
            isPlayerMove = false;
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    /** is dealer sum == 21
     * @return true or false
     */
    public boolean doesDealerHaveBlackjack() {
        if (dealer.getSum() == 21) {
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    /** is player sum > 21
     * @return true or false
     */
    public boolean doesDealerHaveOver() {
        if (dealer.getSum() > 21) {
            isRoundEnded = true;
            return true;
        }
        return false;
    }

    /** return:
     * 1 - player is winner
     * -1 - dealer is winner
     * 0 - draw
     * @return who is winner
     */
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

    /** get player cards in string format
     * @return cards
     */
    public String getPlayerCards() {
        return player.showCards();
    }

    /** get player sum
     * @return sum
     */
    public int getPlayerSum() {
        return player.getSum();
    }

    /** get dealer cards in string format
     * @return cards
     */
    public String getDealerCards() {
        return dealer.showCards();
    }

    /** get dealer sum
     * @return sum
     */
    public int getDealerSum() {
        return dealer.getSum();
    }

    /** how many times did player win
     * @return player wins
     */
    public int getPlayerWins() {
        return playerWins;
    }

    /** how many times did dealer win
     * @return dealer wins
     */
    public int getDealerWins() {
        return dealerWins;
    }

    /** is player move now
     * @return true or false
     */
    public boolean isPlayerMove() {
        return isPlayerMove;
    }


    /** is current round ended
     * @return true or false
     */
    public boolean isRoundEnded() {
        return isRoundEnded;
    }

    /** get all cnt of rounds
     * @return cnt of rounds
     */
    public int getCntOfRounds() {
        return cntOfRounds;
    }

    //endregion
}
