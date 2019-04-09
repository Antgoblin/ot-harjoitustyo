/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import javafx.scene.layout.StackPane;

/**
 *
 * @author jy
 */
public class Tile extends StackPane {

    private int x;
    private int y;
    private Tiletype type;
    private Character character = null;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Tiletype.Void;

    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setType(Tiletype type) {
        this.type = type;
    }

    public Tiletype getType() {
        return this.type;
    }

    public boolean occupied() {
        return this.character != null;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return this.character;
    }
}
