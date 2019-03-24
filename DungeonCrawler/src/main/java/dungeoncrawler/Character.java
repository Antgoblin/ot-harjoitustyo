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
    private boolean attacked;
    private int lastDamageDealt;
    
    public Character(int x, int y, int hp) {
        this.maxhp = hp;
        this.currenthp = hp;
        this.x = x;
        this.y = y;
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
    
    public void loseHp(int amount) {
        this.currenthp -= amount;
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
