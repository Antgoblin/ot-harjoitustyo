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
public class Character {
    
    private Polygon shape;
    private int maxhp;
    private int currenthp;
    private Tile tile;
    
    public Character(Polygon shape, int x, int y, int hp) {
        this.shape = shape;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);
        this.maxhp = hp;
        this.currenthp = hp;
    }
    
    public Polygon getShape() {
        return this.shape;
    }
    
    public int getMaxHp() {
        return this.maxhp;
    }
    
    public int getCurrentHp() {
        return this.currenthp;
    }
    
    public void moveUp() {
        double y = this.shape.getTranslateY() - 50;
        this.shape.setTranslateY(y);
    }
    
    public void moveDown() {
        double y = this.shape.getTranslateY() + 50;
        this.shape.setTranslateY(y);
    }
    
    public void moveRight() {
        double x = this.shape.getTranslateX() + 50;
        this.shape.setTranslateX(x);
    }
    
    public void moveLeft() {
        double x = this.shape.getTranslateX() - 50;
        this.shape.setTranslateX(x);
        
    }    
}
