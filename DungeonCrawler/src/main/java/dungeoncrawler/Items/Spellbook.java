/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

import dungeoncrawler.Items.SpellbookType;

/**
 *
 * @author jy
 */
public class Spellbook extends Item {

    private SpellbookType s;

    public Spellbook(SpellbookType s) {
        super(s.getName(), ItemType.SPELLBOOK);
        this.s = s;
    }

    public String getName() {
        return this.s.getName();
    }

    public Spell getSpell() {
        return this.s.getSpell();
    }

//    public ItemType getType() {
//        return ItemType.SPELLBOOK;
//    }

    public String getAction() {
        return "Read";
    }

}
