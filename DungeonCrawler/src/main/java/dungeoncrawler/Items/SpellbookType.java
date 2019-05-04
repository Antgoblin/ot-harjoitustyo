/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jy
 */
public enum SpellbookType {

    FIREBOLTBOOK("Book of Firebolt", Spell.FIREBOLT),
    TELEPORTBOOK("Book of Teleportation", Spell.TELEPORT),
    HEALBOOK("Book of Healing", Spell.HEAL);

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

    public List<SpellbookType> getAll() {
        List<SpellbookType> all = new ArrayList<>();
        all.add(FIREBOLTBOOK);
        all.add(TELEPORTBOOK);
        all.add(HEALBOOK);
        return all;
    }

    public SpellbookType Randomize() {
        List<SpellbookType> all = getAll();
        Collections.shuffle(all);
        return all.get(0);
    }

    /**
     * Metodilla haetaan loitsukirja jolla tietty nimi
     *
     * @param name minkä nimistä etsitään
     * @return loitsukirjan jolla sama nimi kuin mitä on annettu, jos ei löydy
     * palauttaa null.
     */
    public SpellbookType getSpellbook(String name) {
        List<SpellbookType> spellbookTypes = getAll();
        List<SpellbookType> searchedSpellBooks = new ArrayList<>();
        spellbookTypes.forEach(w -> {
            if (w.getName().equals(name)) {
                searchedSpellBooks.add(w);
            }
        });
        if (!searchedSpellBooks.isEmpty()) {
            return searchedSpellBooks.get(0);

        } else {
            return null;
        }
    }

}
