/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.util.Random;

/**
 *
 * @author 17091837
 */

public class Ship {

    private final Random random = new Random();

    private boolean vertical;
    private int length;
    private int health;

    public Ship() {
        vertical = random.nextBoolean();
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setLength(int length) {
        this.length = length;
        this.health = length;
    }

    public int getLength() {
        return this.length;
    }

    public void hit() {
        this.health--;
    }

    public int getHealth() {
        return this.health;
    }

}
