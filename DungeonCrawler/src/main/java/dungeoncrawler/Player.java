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
    private int gold;
    private Weapon weapon;
    private Weapon weapon2;

    public Player(int x, int y, int hp, Class playerclass) {
        super("You", x, y, hp);
        this.weapon = playerclass.StartingWeapon();
        this.weapon2 = playerclass.SecondWeapon();
        this.playerClass = playerclass;
        this.Lvl = 1;
        this.exp = 0;
        this.gold = 0;
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

    public void gainExp(int amount) {
        this.exp += amount;
    }

    public void loseExp(int amount) {
        this.exp -= amount;
    }

    public int getGold() {
        return this.gold;
    }

    public void gainGold(int amount) {
        this.gold += amount;
    }

    public void loseGold(int amount) {
        this.gold -= amount;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public Weapon getWeapon2() {
        return this.weapon2;
    }

    public void Switch() {
        if (this.weapon != null && this.weapon2 != null) {
            Weapon one = this.weapon;
            Weapon two = this.weapon2;
            this.weapon = two;
            this.weapon2 = one;

        }
    }

    public int getRange() {
        return weapon.getRange();
    }
}
