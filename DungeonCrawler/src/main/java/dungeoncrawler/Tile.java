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
    private Rectangle border;
    private Color fillColor;
    private Color borderColor;
        
    public Tile(double x, double y) {
        this.border = new Rectangle(50, 50);
//        border.setFill(null);
//        border.setStroke(Color.BLACK);
//
//        setAlignment(Pos.CENTER);
//        getChildren().addAll(border);
        this.solid = false;
        this.fillColor = null;
        this.borderColor = Color.BLACK;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    
    public void render() {
        border.setFill(fillColor);
        border.setStroke(borderColor);
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(border);
        
    }
    
    public void wall() {
        this.fillColor = Color.BLACK;
        this.solid = true;
    }
    
    public Boolean getSolid() {
        return this.solid;
    }
}
