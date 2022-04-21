/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 17091837
 */
public class TextReader {

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private int allShipsHealth = 0;

    private final BoardChecker boardChecker = new BoardChecker();

    public int getAllShipsHealth() {
        return this.allShipsHealth;
    }

    /**
     * Reads text file and stores in 2D array 
     * https://www.tutorialspoint.com/How-to-read-a-2d-array-from-a-file-in-java 
     * @return 2D array with ship lengths & positions
     * @throws FileNotFoundException 
     */
    public Ship[][] readFile() throws FileNotFoundException {
        int[][] numbers = new int[ROWS][COLUMNS];
        Scanner sc = new Scanner(choseTextFile());

        while (sc.hasNextLine()) {
            for (int i = 0; i < numbers.length; i++) {
                String[] line = sc.nextLine().trim().split("," + " ");
                for (int j = 0; j < line.length; j++) {

                    int num = Integer.parseInt(line[j]);

                    if (num > 1 && num < 6) {
                        numbers[i][j] = num;
                    }
                }
            }
        }
        Ship[][] board = convertToBoard(numbers);
        return board;
    }

    /**
     * Provides dialog box to chose file from 
     * https://stackoverflow.com/questions/40255039/how-to-choose-file-in-java/40255184
     * @return the file selected
     */
    private File choseTextFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        File[] file = dialog.getFiles();
        return file[0];
    }

    private Ship[][] convertToBoard(int[][] numbers) {
        Ship[][] board = new Ship[ROWS][COLUMNS];
        int nextH;
        int nextV;

        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {

                int length = numbers[x][y];

                Ship ship = new Ship();
                ship.setLength(length);

                // Avoid ArrayIndexOutOfBoundsException
                if (y != 9) {
                    nextH = numbers[x][y + 1];

                    // If horizontal ship found
                    if (length > 1 && length < 6 && length == nextH && board[x][y] == null) {
                        ship.setVertical(false);

                        placeHorizontalShip(board, ship, y, length, x);
                    }
                }

                // Avoid ArrayIndexOutOfBoundsException
                if (x != 9) {
                    nextV = numbers[x + 1][y];

                    // If vertical ship found
                    if (length > 1 && length < 6 && length == nextV && board[x][y] == null) {
                        ship.setVertical(true);

                        placeVerticalShip(board, ship, x, length, y);
                    }
                }
            }
        }
        return board;
    }
    
    private void placeVerticalShip(Ship[][] board, Ship ship, int x, int length, int y) {
        // FR5 If ship can be placed & place is free & not out of bounds
        if (boardChecker.canPlace(board, ship.isVertical(), x, x + length, y)) {
            int n;

            // For the length of that ship
            for (n = x; n < x + length; n++) {
                board[n][y] = ship;
                allShipsHealth++;
            }
        }
    }

    private void placeHorizontalShip(Ship[][] board, Ship ship, int y, int length, int x) {
        // FR5 If ship can be placed & place is free & not out of bounds
        if (boardChecker.canPlace(board, ship.isVertical(), y, y + length, x)) {
            int n;

            // For the length of that ship
            for (n = y; n < y + length; n++) {
                board[x][n] = ship;
                allShipsHealth++;
            }
        }
    }

}
