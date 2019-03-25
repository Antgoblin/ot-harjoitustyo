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
    
    private int x;
    private int y;
    private int maxhp;
    private int currenthp;
    private boolean moved;
    private boolean attacked;
    private int lastDamageDealt;
    
    public Character(int x, int y, int hp) {
        this.maxhp = hp;
        this.currenthp = hp;
        this.x = x;
        this.y = y;
        this.moved = false;
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
    
    public void gainHp(int amount) {
        this.currenthp += amount;
    }
    
    public void loseHp(int amount) {
        this.currenthp -= amount;
    } 
    
    public void move(Direction dir) {
        this.x += dir.getX();
        this.y += dir.getY();
        this.moved = true;
    }    
    
    public void doNotMove() {
        this.moved = false;
    }
    
    public void attacked(int damage) {
        this.attacked = true;
        this.lastDamageDealt = damage;
    }
    
    public void noAttack() {
        this.attacked = false;
    }
    
    public boolean getIfAttacked() {
        return attacked;
    }
    
    public boolean getIfMoved() {
        return moved;
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
}
