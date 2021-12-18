package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    Scanner scanner;
    Player player1;
    Player player2;
    Boolean endGame;

    public Game() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        scanner = new Scanner(System.in);
        endGame = false;
    }

    public void start() {
        // Размещение кораблей
        player1.playerPlaceAllShip();
        player2.playerPlaceAllShip();



//      while (player1.field.hasShipAfloat() && player2.field.hasShipAfloat()) {
        while (!endGame) {
            int[] tmpShot;
            // player1 turn
            // вывод полей
            player2.field.printFieldWithFogAndShotPoint();
            System.out.print("---------------------");
            player1.field.printField();

            tmpShot = player1.shotPoint(); // name + ", it's your turn" возвращает координаты выстрела
            player2.field.shootRegistration(tmpShot); // отметка на карте X или M
            player2.field.printHitOrMiss(tmpShot); // вывод попадание или промах

            // проверка на потопление корабля
            for (Ship ship : player2.ships) {
                if (ship.atFloat) { // если корабль на плаву
                    // если проверка показала что он утонул и есть другие на плаву
                    if (!ship.isShipAtFloat(player2.field) && player2.field.hasShipAfloat()) {
                        System.out.println("You sank a ship! Specify a new target:");
                    } else if (!ship.isShipAtFloat(player2.field) && !player2.field.hasShipAfloat()) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        endGame = true;
                        break;
                    }
                }
            }

            promptPressEnter();

            // player2 turn
            // вывод полей
            player1.field.printFieldWithFogAndShotPoint();
            System.out.print("---------------------");
            player2.field.printField();

            tmpShot = player2.shotPoint(); // name + ", it's your turn" возвращает координаты выстрела
            player1.field.shootRegistration(tmpShot); // отметка на карте X или M
            player1.field.printHitOrMiss(tmpShot); // вывод попадание или промах

            // проверка на потопление корабля
            for (Ship ship : player1.ships) {
                if (ship.atFloat) { // если корабль на плаву
                    // если проверка показала что он утонул и есть другие на плаву
                    if (!ship.isShipAtFloat(player1.field) && player1.field.hasShipAfloat()) {
                        System.out.println("You sank a ship! Specify a new target:");
                    } else if (!ship.isShipAtFloat(player1.field) && !player1.field.hasShipAfloat()) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        endGame = true;
                        break;
                    }
                }
            }

            promptPressEnter();
        }
    }


    public static void promptPressEnter() {
        System.out.print("\nPress Enter and pass the move to another player\n" + "...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
