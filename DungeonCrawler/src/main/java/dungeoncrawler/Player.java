/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import javafx.scene.shape.Polygon;

/**
 *
 * @author jy
 */
public class Player extends Character {
    
    private int Size;
    
    public Player(int x, int y, int hp, int size) {
        super(x, y, hp);
        this.Size = size;
    }

    public int getSize() {
        return this.Size;
    }
}
