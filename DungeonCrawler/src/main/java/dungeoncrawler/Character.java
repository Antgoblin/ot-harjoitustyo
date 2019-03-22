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
    
    private int maxhp;
    private int currenthp;
    private Map map;
    private int x;
    private int y;
    
    public Character(int x, int y, int hp) {
        this.maxhp = hp;
        this.currenthp = hp;
        this.map = null;
        this.x = x;
        this.y = y;
    }
    
    public void setOnMap(Map map) {
        this.map = map;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getMaxHp() {
        return this.maxhp;
    }
    
    public int getCurrentHp() {
        return this.currenthp;
    }
    
    public void moveUp() {
        this.y -= 1;
    }
    
    public void moveDown() {
        this.y += 1;
    }
    
    public void moveRight() {
        this.x += 1;
    }
    
    public void moveLeft() {
        this.x -= 1;
        
    }    
}
