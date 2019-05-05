/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.logic.Spell;
import dungeoncrawler.logic.Spellbook;
import dungeoncrawler.logic.SpellbookType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jy
 */
public class SpellbookTest {

    Spellbook spellbook;

    @Before
    public void setUp() {
        spellbook = new Spellbook(SpellbookType.HEALBOOK);
    }

    @Test
    public void getName() {
        assertEquals("Book of Healing", spellbook.getName());
    }

    @Test
    public void getSpell() {
        assertEquals(Spell.HEAL, spellbook.getSpell());
    }

    @Test
    public void getAction() {
        assertEquals("Read", spellbook.getAction());
    }

    @Test
    public void getSpellbookFromName() {
        spellbook = new Spellbook(SpellbookType.FIREBOLTBOOK.getSpellbook("Book of Healing"));
        assertEquals("Book of Healing", spellbook.getName());
    }

    @Test
    public void getSpellBookReturnsNullIfNoSimiliarName() {
        assertEquals(null, SpellbookType.FIREBOLTBOOK.getSpellbook("Book"));
    }

}
