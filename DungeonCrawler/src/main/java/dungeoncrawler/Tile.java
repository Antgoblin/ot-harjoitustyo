/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
import java.util.List;
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
    private List<Item> items = new ArrayList<>();

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Tiletype.Void;

    }

    public void setItem(Item item) {
        this.items.add(item);
    }

    public void removeItem() {
        this.items.remove(items.size() - 1);
    }
    
    public void removeItems() {
        this.items.clear();
    }

    public Item getItem() {
        if (!items.isEmpty()) {
            return items.get(items.size() - 1);
        } else {
            return null;
        }
    }

    public boolean containsItem() {
        if (items.isEmpty()) {
            return false;
        } else {
            return true;
        }
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
