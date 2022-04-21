package battleships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 17091837
 */

/**
 * Checks game board to see if ships are viable for placement
 */
public class BoardChecker {

    /**
     * FR5 If ship can be placed & place is free & not out of bounds
     * @param board contains current game state
     * @param vertical if the ship is vertical
     * @param startPos start position of ship
     * @param endPos end position of ship
     * @param randomColumn column of ship
     * @return true if ship can be placed, otherwise false
     */
    public boolean canPlace(Ship board[][], boolean vertical, 
            int startPos, int endPos, int randomColumn) {
        boolean possible = false;
        if (isInGrid(startPos, endPos)) {
            if (vertical) {
                for (int pos = startPos; pos < endPos; pos++) {
                    if (board[pos][randomColumn] == null && 
                            ajacentPositionsFree(board, pos, randomColumn)) {
                        possible = true;
                    } else {
                        possible = false;
                        return possible;
                    }
                }
            } else if (!vertical) {
                for (int pos = startPos; pos < endPos; pos++) {
                    if (board[randomColumn][pos] == null && 
                            ajacentPositionsFree(board, randomColumn, pos)) {
                        possible = true;
                    } else {
                        possible = false;
                        return possible;
                    }
                }
            }
        }
        return possible;
    }

    /**
     * Ensure ships don't touch 
     * https://stackoverflow.com/questions/2035522/get-adjacent-elements-in-a-two-dimensional-array 
     * @param board the current board state to check
     * @param x is the x axis of the ship to place
     * @param y is the y axis of the ship to place
     * @return true if ship placement has no ships next to it, false otherwise
     */
    private boolean ajacentPositionsFree(Ship board[][], int x, int y) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (dx != 0 || dy != 0) {
                    if (isInGrid(x + dx, y + dy)) {
                        Ship ship = board[x + dx][y + dy];
                        if (ship != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isInGrid(int x, int y) {
        return (x < 10 && y < 10 && x >= 0 && y >= 0);
    }

    /**
     * Obtains list for comparison and compares 
     * to list with accurate ship lengths
     * @param board the current board state to check
     * @return true if they are the same, false if not
     */
    public boolean accurateShipLengths(Ship board[][]) {
        List<Integer> list = new ArrayList<>();
        List<Ship> shipList = new ArrayList<>();

        List numbers = Arrays.asList(2, 2, 3, 4, 5);

        for (Ship[] x : board) {
            for (Ship y : x) {
                if (y != null && !shipList.contains(y)) {
                    shipList.add(y);
                    int length = y.getLength();
                    list.add(length);
                }
            }
        }
        return equalLists(list, numbers);
    }

    /**
     * Checks to see if both lists are the same 
     * https://stackoverflow.com/questions/20054737/java-compare-unordered-arraylists 
     * @param one the first list for comparison
     * @param two the second list for comparison
     * @return true if they are the same, false if not
     */
    private boolean equalLists(List<Integer> one, List<Integer> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()) {
            return false;
        }

        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }

}
