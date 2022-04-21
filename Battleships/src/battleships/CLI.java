/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 17091837
 *
 */
public class CLI {

    public static void main(String[] args) throws FileNotFoundException {

        final int ROWS = 10;
        final int COLUMNS = 10;

        boolean tries[][] = new boolean[ROWS][COLUMNS];

        BSModel model = new BSModel();

        Scanner kb = new Scanner(System.in);

        System.out.println("Enter 1 to load from text file. Enter any other letter/number to auto load game");
        int load = kb.nextInt();
        if (load == 1) {
            model.loadBoardFromText();
        } else {
            model.autoLoad();
            System.out.println("Auto Game Loaded");
        }
        
        assert (model.invariant() == true) : "game invariant must be maintained";
        
        if (model.getMessage() != null) {
            System.out.println(model.getMessage());
            model.clearMessage();
        }

        while (model.hasWon() == false) {

            String y;
            int yPos;
            int xPos;

            System.out.println("Game Board");
            System.out.println(printBoard(tries, model));

            try {
                System.out.println("Enter yPos (Letter between A-J)");
                y = kb.next();

                yPos = letterToNumber(y);

                System.out.println("Enter xPos (Number between 0-9");
                xPos = kb.nextInt();

                if (xPos < 10 && yPos < 10) {
                    if (tries[yPos][xPos] != true) {
                        guessShot(model, yPos, xPos);
                        tries[yPos][xPos] = true;
                        if (model.getDownedShip() != null) {
                            System.out.println("Ship " 
                                    + model.getDownedShip().getLength() + " is down!");
                            model.clearDownedShip();
                        }
                    } else {
                        System.out.println("Already guessed PositionX " 
                                + xPos + " PositionY: " + yPos);
                    }
                } else if (yPos > 10) {
                    System.out.println("yPos too big! Enter a number between 0 & 10");
                } else if (xPos > 10) {
                    System.out.println("xPos too big! Enter a number between 0 & 10");
                }
            } catch (Exception e) {
                System.out.println("Inavlid input, please enter a letter between a - j followed by a number");
            }
        }
        System.out.println("You Win! " + "Tries: " + model.getTries().toString());
    }

    private static int letterToNumber(String yPos) {
        String y = yPos.toLowerCase();
        char c = y.charAt(0);
        String yLetters = "abcdefghij";
        int n = 0;
        for (char l : yLetters.toCharArray()) {
            if (l == c) {
                return n;
            }
            n++;
        }
        return 10;
    }

    private static void guessShot(BSModel model, int yPos, int xPos) {
        model.guessShot(yPos, xPos);
        if (model.hasHit(yPos, xPos)) {
            System.out.println("You hit ship at PositionY: " 
                    + yPos + " and PositionX: " + xPos + " !");
        } else if (!model.hasHit(yPos, xPos)) {
            System.out.println("Missed at PositionY: " + yPos
                    + " and PositionX: " + xPos + ", try again!");
        }
    }

    private static String printBoard(boolean tries[][], BSModel model) {

        String input = "ABCDEFGHIJ";
        String str;
        String st = "  ";

        Ship[][] board = model.getBoard();

        // y axis letters
        for (int num = 0; num < 10; num++) {
            st = st + num + " ";
        }
        st = st + "\n";

        // y axis numbers
        for (int y = 0; y < 10; y++) {

            str = input.substring(y, y + 1);
            st = st + str + " ";

            for (int x = 0; x < 10; x++) {

                String symbol = ".";

                Ship ship = board[y][x];

                // If there is a ship at this position
                if (ship != null && tries[y][x] == true) {
                    symbol = "H";

                  // If there isn't
                } else if (ship == null && tries[y][x] == true) {
                    symbol = "M";
                }

                st = st + symbol + " ";
            }
            st = st + "\n";
        }
        return st;
    }

}
