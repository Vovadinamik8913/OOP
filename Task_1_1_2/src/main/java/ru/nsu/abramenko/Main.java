package ru.nsu.abramenko;

import java.util.Scanner;
import ru.nsu.abramenko.game.Blackjack;

public class Main {

    public static void  showCards(String player, String dealer) {
        System.out.print("\tВаши карты: " + player + "\n");
        System.out.print("\tКарты дилера: " +  dealer + "\n");
    }


    public static void main(String[] args) throws InterruptedException {
        int ch = 2;
        int res = 0;
        boolean isGameEnded = false;
        Scanner scanner = new Scanner(System.in);
        Blackjack blackjack = new Blackjack();
        System.out.print("Добро пожаловать в Блэкджек!\n");
        while (!isGameEnded) {
            res = 0;
            blackjack.newRound();
            System.out.print("Раунд " + blackjack.getCntOfRounds() + "\n");
            System.out.print("Дилер раздал карты\n");
            showCards(blackjack.getPlayerCards(), blackjack.getDealerCards());
            System.out.println();
            if (!blackjack.isRoundEnded()) {
                System.out.print("Ваш ход\n" + "-------\n");
                while (blackjack.isPlayerMove() && !blackjack.doesPlayerHaveBlackjack()) {
                    System.out.print("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться, и “2”, чтобы закончить игру...\n");
                    ch = scanner.nextInt();
                    System.out.println();
                    switch (ch) {
                        case 1:
                            blackjack.move(ch);
                            if (!blackjack.doesPlayerHaveBlackjack()) {
                                blackjack.doesPlayerHaveOver();
                            }
                            System.out.print("Вы открыли карту " + blackjack.getLastAddedCard() + "\n");
                            showCards(blackjack.getPlayerCards(), blackjack.getDealerCards());
                            break;
                        case 0:
                            blackjack.move(ch);
                            break;
                        case 2:
                            isGameEnded = true;
                            break;
                        default:
                            break;
                    }
                }
                if (ch == 2) {
                    continue;
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
                        System.out.print("Дилер открыл карту " + blackjack.getLastAddedCard() + "\n");
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

                    System.out.print("Счет " + blackjack.getPlayerWins() + ":" + blackjack.getDealerWins() + " ");

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
                Thread.sleep(1500);
            }
        }
    }
}