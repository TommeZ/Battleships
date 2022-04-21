/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author 17091837
 */
public final class BSModel extends Observable {

    private int allShipsHealth;
    private Integer tries;

    private final Random random = new Random();

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;

    private Ship board[][] = new Ship[ROWS][COLUMNS];

    private final BoardChecker boardChecker = new BoardChecker();
    private final TextReader textReader = new TextReader();

    private Ship downedShip;

    private String message;

    public BSModel() {
        this.tries = 0;
        Notify();
    }

    public void Notify() {
        setChanged();
        notifyObservers();
    }

    /**
     * Makes guess attempt on the board using coordinates (x, y)
     * @pre x and y must contain value
     * @param x coordinate x
     * @param y coordinate y
     * @post board[x][y] ship - 1 health if hit, allShipsHealth --, tries ++
     */
    public void guessShot(int x, int y) {
        assert (x >= 0 && x <= 9 && y >= 0 && y <= 9) : "Position out of range";
        if (hasWon() != true) {
            tries++;
            if (hasHit(x, y)) {
                board[x][y].hit();
                allShipsHealth--;
                if (shipDown(x, y)) {
                    this.downedShip = board[x][y];
                }
            }
            assert (invariant() == true) : "game invariant must be maintained";
            Notify();
        }
    }

    public boolean hasHit(int x, int y) {
        return board[x][y] != null;
    }

    public boolean shipDown(int x, int y) {
        Ship ship = board[x][y];
        return ship.getHealth() == 0;
    }

    /**
     * Auto loads game board and commands ship placements
     * @pre true
     * @post 5 ships conforming to functional requirements are placed on board
     */
    public void autoLoad() {

        Stack ShipLengths = getAutoShipLengths();

        boolean placed;
        int length;
        int endPos;
        int ShipsPlaced = 0;

        length = (int) ShipLengths.pop();
        assert (length >= 2 & length <= 6) : "Ship length not accurate";

        while (ShipsPlaced != 5) {

            int startPos = random.nextInt(9);
            int randomColumn = random.nextInt(9);

            Ship ship = new Ship();

            endPos = startPos + length;

            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[x].length; y++) {

                    placed = false;

                    // FR5 If ship can be placed & place is free & not out of bounds
                    if (boardChecker.canPlace(this.board, ship.isVertical(), 
                            startPos, endPos, randomColumn)) {
                        ship.setLength(length);
                        paceShipOnBoard(ship, startPos, endPos, randomColumn);
                        placed = true;
                        ShipsPlaced++;
                    }
                    if (placed == true && !ShipLengths.isEmpty()) {
                        length = (int) ShipLengths.pop();
                        endPos = startPos + length;
                    }
                }
            }
        }
    }

    private void paceShipOnBoard(Ship ship, int startPos, int endPos, int randomColumn) {
        assert (ship != null) : "No ship provided";
        if (ship.isVertical()) {
            for (int pos = startPos; pos < endPos; pos++) {
                board[pos][randomColumn] = ship;
                allShipsHealth += 1;
            }

        }
        if (!ship.isVertical()) {
            for (int pos = startPos; pos < endPos; pos++) {
                board[randomColumn][pos] = ship;
                allShipsHealth += 1;
            }
        }
    }

    private Stack getAutoShipLengths() {
        Stack ShipLengths = new Stack();
        ShipLengths.push(2);
        ShipLengths.push(2);
        ShipLengths.push(3);
        ShipLengths.push(4);
        ShipLengths.push(5);
        return ShipLengths;
    }

    public boolean hasWon() {
        return allShipsHealth <= 0;
    }

    public Ship[][] getBoard() {
        return this.board;
    }

    public Integer getTries() {
        return this.tries;
    }

    /**
     * Gives user window to open text file for new game
     * @pre true
     * @throws FileNotFoundException thrown if no file is found to load
     * @post board contains new game, loaded from text file if conforms to
     * functional requirements, auto loaded otherwise
     */
    public void loadBoardFromText() throws FileNotFoundException {
        this.board = textReader.readFile();
        this.allShipsHealth = textReader.getAllShipsHealth();
        assert (board != null) : "board not loaded";
        checkShipLengths();
        Notify();
    }

    private void checkShipLengths() {
        if (boardChecker.accurateShipLengths(this.board) == false) {
            this.board = new Ship[ROWS][COLUMNS];
            allShipsHealth = 0;
            this.message = "Text file does not conform to format, ships "
                    + "have been placed automatically";
            autoLoad();
        } else {
            this.message = "Game Succesfully Loadeded from text file";
        }
    }

    public Ship getDownedShip() {
        return this.downedShip;
    }

    /**
     * Clears the downed ship currently stored in memory
     * @pre true
     * @post downedShip = null
     */
    public void clearDownedShip() {
        this.downedShip = null;
    }

    /**
     * Clears the String message currently stored in memory
     * @pre true
     * @post message = null
     */
    public void clearMessage() {
        this.message = null;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * Either in winning state with 0 all ship health || playing not 
     * won against all ship health > 0
     * @return game state
     */
    public boolean invariant() {
        return this.hasWon() != true && this.allShipsHealth > 0 || this.hasWon() == true && this.allShipsHealth <= 0;
    }

}
