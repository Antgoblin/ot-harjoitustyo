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

    /**
     * palauttaa loitsukirjan nimen
     *
     * @return loitsukirjan nimen
     */
    public String getName() {
        return this.s.getName();
    }

    /**
     * palauttaa loitsun joka on tyypin sisällä
     *
     * @return loitsun joka on kirjan sisällä
     */
    public Spell getSpell() {
        return this.s.getSpell();
    }

    /**
     * Kertoo mitä kirjalle pystyy tehdä. Kaikki Itemiä lisäävät luokat
     * omistavat getAction() metodin
     *
     * @return String "Read"
     */
    public String getAction() {
        return "Read";
    }

}
