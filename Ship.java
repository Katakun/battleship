package battleship;
import java.util.Scanner;

public class Ship {
    int shipSize;
    String type;
    int[][] coordinates;
    boolean atFloat;
    int row1;
    int col1;
    int row2;
    int col2;

    public Ship(String coordinate, String shipType, int shipSize) {
        Scanner scan = new Scanner(System.in);

        coordinates = convertInput(coordinate); // A1 D1 -> [0, 0][3, 0]
        this.shipSize = shipSize;
        this.type = shipType;
        atFloat = true;
        row1 = coordinates[0][0];
        col1 = coordinates[0][1];
        row2 = coordinates[1][0];
        col2 = coordinates[1][1];
    }


    // Проверка на потопление корабля
    public boolean isShipAtFloat(Field field) {
        if (row1 == row2) { // если они на одном ряду
            for (int i = Math.min(col1, col2); i <= Math.max(col1, col2); i++) {
                if (field.field[row1][i].equals(" O")) {
                    return true;
                }
            }
        } else if (col1 == col2) { // если он вертикальный
            for (int i = Math.min(row1, row2); i <= Math.max(row1, row2); i++) {
                if (field.field[i][col1].equals(" O")) {
                    return true;
                }
            }
        }
        atFloat = false;
        return false;
    }


    public boolean isShipSizeOk() {
        return getSize() == shipSize;
    }

    public boolean isLocationOk() {
        return row1 == row2 || col1 == col2;
    }

    public int getSize() {
        int size = 0;
        if (row1 == row2) {
            size =  Math.max(col1, col2) - Math.min(col1, col2) + 1;
        } else if (col1 == col2) {
            size = Math.max(row1, row2) - Math.min(row1, row2) + 1;
        }
        return size;
    }

    private int[][] convertInput(String input) {
        // A1 D1 -> [0, 0][3, 0]
        String[] stringArr = input.split(" ");
        int[] intArr1 = stringToInt(stringArr[0]);
        int[] intArr2 = stringToInt(stringArr[1]);
        int[][] ship = {intArr1, intArr2};
        return ship;
    }

    public static int[] stringToInt(String input) {
        // A1 -> [0, 0]
        char ch = input.charAt(0);
        int row = (int) ch - 65;
        int col = Integer.parseInt(input.substring(1)) - 1;
        int[] result = {row, col};
        return result;
    }
}
