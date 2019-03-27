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
    private boolean acted;
    private boolean moved;
    private boolean attacked;
    private int lastDamageDealt;
    
    public Character(int x, int y, int hp) {
        this.maxhp = hp;
        this.currenthp = hp;
        this.x = x;
        this.y = y;
        this.moved = false;
        this.acted = false;
    }
    
    public int X() {
        return this.x;
    }
    
    public int Y() {
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
        if(this.currenthp > this.maxhp) {
            this.currenthp = this.maxhp;
        }
    }
    
    public void loseHp(int amount) {
        this.currenthp -= amount;
    } 
    
    public void move(Map map, Direction dir) {
        //old tile 
        map.getTile(this.x, this.y).setOccupied(false);
        //new tile 
        this.x += dir.X();
        this.y += dir.Y();
        map.getTile(this.x, this.y).setOccupied(true);
        this.moved = true;
    }
    
    public boolean ifActed() {
        return this.acted;
    }

    public void acted() {
        this.acted = true;
    }
    
    public void didNotAct() {
        this.acted = false;
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
