/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author jy
 */
public class Tile extends StackPane {
    
    private Tiletype type;
        
    public Tile() {
        this.type = Tiletype.Void;

    }
  
    public void setWall() {
        this.type = Tiletype.Wall;
    }
    
    public void setType(Tiletype type) {
        this.type = type;
    }
    
    public Tiletype getType() {
        return this.type;
    }
}
