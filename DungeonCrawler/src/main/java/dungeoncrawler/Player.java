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
public class Player extends Character {
    
    private Class playerClass;
    private int Lvl;
    private int exp;
    private Weapon weapon;
    
    public Player(int x, int y, int hp) {
        super(x, y, hp);
        this.weapon = new Weapon("dagger", 2, 5);
        this.playerClass = Class.Warrior;
        this.Lvl = 1;
        this.exp = 0;
    }
    
    public void attack(Character target) {
        int damage = weapon.getDamage();
        this.attacked(damage);
        target.loseHp(damage);
    }
    
    public Class getPlayerClass() {
        return this.playerClass;
    }
    
    public int getLvl() {
        return this.Lvl;
    }
    
    public int getExp() {
        return this.exp;
    }
}
