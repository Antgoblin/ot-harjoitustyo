/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author jy
 */
public class Player extends Character {

    private Class playerClass;
    private int lvl;
    private int exp;
    private int gold;
    private int maxMana;
    private int currentMana;
    private Weapon weapon;
    private Weapon weapon2;
    private int regenerationTimer = 6;
    private int str = 0;
    private List<Item> inventory = new ArrayList<>();

    public Player(int x, int y, Class playerclass) {
        super("You", x, y, playerclass.hp(), Color.BLACK);
        this.weapon = playerclass.startingWeapon();
        this.weapon2 = playerclass.secondWeapon();
        if (this.weapon != null) {
            this.inventory.add(this.weapon);
        }
        if (this.weapon2 != null) {
            this.inventory.add(this.weapon2);
        }
        this.playerClass = playerclass;
        this.lvl = 1;
        this.exp = 0;
        this.gold = 0;
        this.maxMana = playerclass.mana();
        this.currentMana = playerclass.mana();
    }

    public void attack(Enemy target) {
        target.rage();
        int damage = weapon.getDamage();
        this.attacked(damage);
        target.loseHp(damage);
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
        return this.lvl;
    }

    public int getExp() {
        return this.exp;
    }

    public void gainExp(int amount) {
        this.exp += amount;
    }

    public void loseExp(int amount) {
        this.exp -= amount;
        if (this.exp < 0) {
            this.exp = 0;
        }
    }

    public int maxMana() {
        return this.maxMana;
    }

    public int currentMana() {
        return this.currentMana;
    }

    public void gainMana(int amount) {
        this.currentMana += amount;
        if (this.currentMana > this.maxMana) {
            this.currentMana = this.maxMana;
        }
    }

    public boolean loseMana(int amount) {
        if (this.currentMana >= amount) {
            this.currentMana -= amount;
            return true;
        } else {
            return false;
        }
    }

    public int getGold() {
        return this.gold;
    }

    public void gainGold(int amount) {
        this.gold += amount;
    }

    public void loseGold(int amount) {
        this.gold -= amount;
        if (this.gold < 0) {
            this.gold = 0;
        }
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public Weapon getWeapon2() {
        return this.weapon2;
    }

    public List<Item> inventory() {
        return this.inventory;
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void switchWeapons() {
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

    public void checkIfLevelUp() {
        if (this.exp >= this.lvl * this.lvl * 100) {
            lvlUp();
        }
    }

    public void lvlUp() {
        this.gainMaxHp(this.lvl * 10);
        this.lvl++;
        this.str++;
    }

    public void checkIfRegenerates() {
        if (this.regenerationTimer > 0) {
            this.regenerationTimer--;
        } else {
            this.gainHp(1);
            this.regenerationTimer = 6;
        }
    }
}
