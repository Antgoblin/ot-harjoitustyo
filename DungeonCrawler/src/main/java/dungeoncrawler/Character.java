/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author jy
 */
public class Character {
    
    private String name;
    private int x;
    private int y;
    private int maxhp;
    private int currenthp;
    private boolean acted = false;
    private boolean attacked = false;
    private int lastDamageDealt;
    private Color color;
    
    public Character(String name, int x, int y, int hp, Color color) {
        this.name = name;
        this.maxhp = hp;
        this.currenthp = hp;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    public int X() {
        return this.x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int Y() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    
    public int getMaxHp() {
        return this.maxhp;
    }
    
    public int getCurrentHp() {
        return this.currenthp;
    }
    
    public void gainHp(int amount) {
        this.currenthp += amount;
        if(this.currenthp > this.maxhp) {
            this.currenthp = this.maxhp;
        }
    }
    
    public void loseHp(int amount) {
        this.currenthp -= amount;
    } 
    
    public void move(Map map, Direction dir) {
        //old tile 
        map.getTile(this.x, this.y).setCharacter(null);
        //new tile 
        this.x += dir.X();
        this.y += dir.Y();
        map.getTile(this.x, this.y).setCharacter(this);
    }
    
    public boolean hasActed() {
        return this.acted;
    }

    public void setActed(boolean status) {
        this.acted = status;
    }
    
    public void attacked(int damage) {
        this.attacked = true;
        this.lastDamageDealt = damage;
    }
    
    public void hasNotAttacked() {
        this.attacked = false;
    }
    
    public boolean hasAttacked() {
        return attacked;
    }
    
    public int getLastDamage() {
        return lastDamageDealt;
    }
    
    public boolean checkIfDead() {
        if (currenthp <= 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public Color color() {
        return this.color;
    }
}
