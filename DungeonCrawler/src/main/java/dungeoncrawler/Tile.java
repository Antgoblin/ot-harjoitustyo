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
    
    private Boolean solid;
        
    public Tile() {
        this.solid = false;

    }
  
    public void setWall() {
        this.solid = true;
    }
    
    public Boolean isWall() {
        return this.solid;
    }
}
