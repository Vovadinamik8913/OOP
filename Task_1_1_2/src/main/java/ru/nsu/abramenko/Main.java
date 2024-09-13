package ru.nsu.abramenko;

import java.io.Console;
import java.util.Scanner;
import ru.nsu.abramenko.game.Blackjack;

/** Main where is all work.
 *
 */
public class Main {

    /** showing player and dealer cards.
     *
     * @param player player
     * @param dealer dealer
     */
    public static void  showCards(String player, String dealer) {
        System.out.print("\tВаши карты: " + player + "\n");
        System.out.print("\tКарты дилера: " +  dealer + "\n");
    }


    /** menu of the blackjack game.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Console console = System.console();

        int maxRounds = 300;
        int ch = 2;
        int res = 0;
        Blackjack blackjack = new Blackjack();

        System.out.print("Добро пожаловать в Блэкджек!\n");
        while (maxRounds > blackjack.getCntOfRounds()) {
            res = 0;
            blackjack.newRound();
            //region Print
            System.out.print("Раунд " + blackjack.getCntOfRounds() + "\n");
            System.out.print("Дилер раздал карты\n");
            showCards(blackjack.getPlayerCards(), blackjack.getDealerCards());
            System.out.println();
            //endregion
            if (!blackjack.isRoundEnded()) {
                System.out.print("Ваш ход\n" + "-------\n");
                while (blackjack.isPlayerMove() && !blackjack.doesPlayerHaveBlackjack()) {
                    System.out.print("Введите “1”, чтобы взять карту, и “0”,"
                            + " чтобы остановиться, и “2”, чтобы закончить игру...\n");

                    if (console == null) {
                        if (blackjack.getPlayerSum() < 17) {
                            ch = 1;
                        } else {
                            ch = 0;
                        }
                    } else {
                        String command = System.console().readLine();
                        if (command == null) {
                            if (blackjack.getPlayerSum() < 17) {
                                ch = 1;
                            } else {
                                ch = 0;
                            }
                        } else {
                            ch = Integer.parseInt(command);
                        }
                    }

                    System.out.println();
                    switch (ch) {
                        case 1:
                            blackjack.move(ch);
                            if (!blackjack.doesPlayerHaveBlackjack()) {
                                blackjack.doesPlayerHaveOver();
                            }
                            System.out.print("Вы открыли карту "
                                    + blackjack.getLastAddedCard() + "\n");
                            showCards(blackjack.getPlayerCards(), blackjack.getDealerCards());
                            break;
                        case 0:
                            blackjack.move(ch);
                            break;
                        default:
                            return;
                    }
                }

                if (!blackjack.isRoundEnded()) {
                    System.out.print("Ход Дилера\n" + "-------\n");
                    System.out.print("Дилер открывает закрытую карту\n");
                    showCards(blackjack.getPlayerCards(), blackjack.getDealerCards());
                    while (blackjack.getDealerSum() < 17) {
                        blackjack.move(1);
                        if (!blackjack.doesDealerHaveBlackjack()) {
                            blackjack.doesDealerHaveOver();
                        }
                        System.out.print("Дилер открыл карту "
                                + blackjack.getLastAddedCard() + "\n");
                        showCards(blackjack.getPlayerCards(), blackjack.getDealerCards());
                        System.out.println();
                    }
                    blackjack.move(0);
                }

                if (blackjack.isRoundEnded()) {
                    res = blackjack.whoIsWinner();
                    if (res == 1) {
                        System.out.print("Вы выиграли раунд! ");
                    } else if (res == -1) {
                        System.out.print("Дилер выиграл раунд! ");
                    } else {
                        System.out.print("Ничья! ");
                    }

                    System.out.print("Счет " + blackjack.getPlayerWins()
                            + ":" + blackjack.getDealerWins() + " ");

                    if (blackjack.getPlayerWins() > blackjack.getDealerWins()) {
                        System.out.print("в вашу пользу.");
                    } else if (blackjack.getPlayerWins() < blackjack.getDealerWins()) {
                        System.out.print("в пользу дилера.");
                    } else {
                        System.out.print("равный.");
                    }

                    System.out.print("\n");
                    System.out.println();
                }
            }
        }
    }
}