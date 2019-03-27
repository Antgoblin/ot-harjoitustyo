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
    
    private Tiletype type;
    private boolean occupied;
        
    public Tile() {
        this.type = Tiletype.Void;
        this.occupied = false;

    }
  
    
    public void setType(Tiletype type) {
        this.type = type;
    }
    
    public Tiletype getType() {
        return this.type;
    }
    
    public boolean occupied() {
        return this.occupied;
    }
    
    public void setOccupied(boolean bool) {
        this.occupied = bool;
    }
}
