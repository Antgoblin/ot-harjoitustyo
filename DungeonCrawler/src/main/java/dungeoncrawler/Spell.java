/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

/**
 *
 * @author jy
 */
public enum Spell {

    FIREBOLT("Firebolt", 5);

    private String name;
    private int mana;

    private Spell(String name, int mana) {
        this.name = name;
        this.mana = mana;
    }

    public int getMana() {
        return this.mana;
    }

    public String getName() {
        return this.name;
    }
}
