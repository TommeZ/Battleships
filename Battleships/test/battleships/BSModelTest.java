/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 17091837
 */
public class BSModelTest {

    public BSModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of autoLoad method, of class BSModel. A 2D Ships Array board will be
     * loaded in, with Ships conforming to FR4
     */
    @Test
    public void testAutoLoad() {
        System.out.println("autoLoad");
        BSModel instance = new BSModel();
        BoardChecker boardChecker = new BoardChecker();
        instance.autoLoad();
        Ship[][] board = instance.getBoard();
        assertFalse(board == null);
        assertTrue(boardChecker.accurateShipLengths(board));
    }

    // assert board != enull
    // assert board conforms to FR4
    
    
    /**
     * Test of hasWon method, of class BSModel. False hasWon expected, one shot
     * will not result in game win
     */
    @Test
    public void testHasWon() {
        int x = 5;
        int y = 6;
        System.out.println("hasWon");
        BSModel instance = new BSModel();
        instance.autoLoad();
        instance.guessShot(x, y);
        boolean expResult = false;
        boolean result = instance.hasWon();
        assertEquals(expResult, result);
    }

    // assert (!board.hasWon())
    
    
    /**
     * Test of getTries method, of class BSModel. Tries in BSModel will reflect
     * exact amount of guess shots made. Game invariant must also be maintained
     */
    @Test
    public void testGetTries() {
        System.out.println("getTries");
        BSModel instance = new BSModel();
        instance.autoLoad();
        Ship[][] board = instance.getBoard();
        Random random = new Random();

        // Random * Random shots/tries
        int r = random.nextInt(9);
        int r2 = random.nextInt(9);

        for (int x = r; x < board.length; x++) {
            for (int y = r2; y < board[r].length; y++) {
                instance.guessShot(x, y);
            }
        }

        Integer expResult = (board.length - r) * (board.length - r2);
        Integer result = instance.getTries();
        
        assert (instance.invariant() == true) : "game invariant must be maintained";
        assertEquals(expResult, result);
    }

    // assert model.getTries = Number of guess shots
}
