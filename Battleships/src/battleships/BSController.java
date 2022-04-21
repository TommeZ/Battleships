/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import java.io.FileNotFoundException;

/**
 *
 * @author 17091837
 */

public class BSController {

    private final BSModel model;

    public BSController(BSModel model) {
        this.model = model;
    }

    public void guessShot(int x, int y) {
        model.guessShot(x, y);
    }

    public void loadBoardFromText() throws FileNotFoundException {
        model.loadBoardFromText();
    }

    public void autoLoad() {
        model.autoLoad();
    }

    public void initialize() {
        model.Notify();
    }

}
