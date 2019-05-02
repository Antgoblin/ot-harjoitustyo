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
public enum SpellbookType {

    FIREBOLTBOOK("Book of Firebolt", Spell.FIREBOLT);

    private Spell spell;
    private String name;

    private SpellbookType(String name, Spell spell) {
        this.name = name;
        this.spell = spell;
    }

    public String getName() {
        return this.name;
    }

    public Spell getSpell() {
        return this.spell;
    }

}
