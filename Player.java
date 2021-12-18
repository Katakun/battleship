package battleship;

import java.util.Scanner;

public class Player {
    Field field;
    String name;
    Ship[] ships = new Ship[5];
    Scanner scan = new Scanner(System.in);

    public Player(String name) {
        this.field = new Field();
        this.name = name;
    }

    public void playerPlaceAllShip() {
        System.out.println(name + ", place your ships on the game field");
        field.printField();

        ships[0] = playerPlaceShip("Aircraft Carrier", 5);
        field.printField();

        ships[1] = playerPlaceShip("Battleship", 4);
        field.printField();

        ships[2] = playerPlaceShip("Submarine", 3);
        field.printField();

        ships[3] = playerPlaceShip("Cruiser", 3);
        field.printField();

        ships[4] = playerPlaceShip("Destroyer", 2);
        field.printField();

        Game.promptPressEnter();
    }

    // метод из Game
    public Ship playerPlaceShip(String shipType, int shipSize) {
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", shipType, shipSize);

        while (true) {
            String coordinate = scan.nextLine();
            Ship ship = new Ship(coordinate, shipType, shipSize);

            if (ship.isShipSizeOk() && ship.isLocationOk() && field.isShipPositionOk(ship)) {
                field.placeShipInField(ship);
                return ship;
            } else if (!ship.isShipSizeOk()) {
                System.out.printf("\nError! Wrong length of the %s! Try again:\n\n", shipType);
            } else if (!ship.isLocationOk()) {
                System.out.println("\nError! Wrong ship location! Try again:");
            } else { // !isShipPositionOk
                System.out.println("\nError! You placed it too close to another one. Try again:\n");
            }
        }
    }

    // стрельба из Game
    public void shooting() {
        field.printFieldWithFogAndShotPoint(); // Выводит карту с туманом войны
        System.out.println("---------------------");
        int[] shotPoint = shotPoint(); // Take a shot возвращает координаты
        field.shootRegistration(shotPoint);  // Отмечает на карте X или M
        field.printHitOrMiss(shotPoint);  // You hit a ship! или You missed!

        // проверка на потопление корабля
        for (Ship ship : ships) {
            if (ship.atFloat) { // если корабль на плаву
                if (!ship.isShipAtFloat(field) && field.hasShipAfloat()) { // если проверка показала что он утонул
                    System.out.println("You sank a ship! Specify a new target:");
                }
            }
        }
    }


    // из Game
    public int[] shotPoint() {
        System.out.println("\n" + name + ", it's your turn:\n");

        while (true) {
            String input = scan.nextLine();
            int[] shotPoint = Ship.stringToInt(input);
            if (shotPoint[0] < 0 || shotPoint[0] > 9 || shotPoint[1] < 0 || shotPoint[1] > 9 ) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            } else {
                return shotPoint;
            }
        }
    }
}
