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

    public int x() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int y() {
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

    /**
     * Metodilla lisätään hahmolle elämää. Elämä ei voi nuosta yli maksimin
     *
     * @param amount kuinka paljon lisätään
     */
    public void gainHp(int amount) {
        this.currenthp += amount;
        if (this.currenthp > this.maxhp) {
            this.currenthp = this.maxhp;
        }
    }

    /**
     * Metodilla vähennetään hahmolta elämää
     *
     * @param amount kuinka paljon vähennetään
     */
    public void loseHp(int amount) {
        this.currenthp -= amount;
    }

    /**
     * Metodilla lisätään hahmon elämäpisteiden maksimimäärää
     *
     * @param amount kuinka paljolla lisätään
     */
    public void gainMaxHp(int amount) {
        this.maxhp += amount;
    }

    /**
     * Metodi muuttuu hahmon x ja y koordinaatteja suunnan verran Metodi myös
     * poistaa kartalta vanhojen koordinaattien ruudusta hahmon ja lisää uusien
     * koordinaattien ruutuun
     *
     * @param map millä kartalla pelaaja liikkuu
     * @param dir mihin suuntaan pelaaja liikkuu
     */
    public void move(Map map, Direction dir) {
        //old tile 
        map.getTile(this.x, this.y).setCharacter(null);
        //new tile 
        this.x += dir.x();
        this.y += dir.y();
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

    /**
     * Metodi tarkistaa onko hahmon elämäpisteet vähemmän kuin yksi
     *
     * @return true jos hahmon elämäpisteet ovat nollan tai alle, muuten false
     */
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
