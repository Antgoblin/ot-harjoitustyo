/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.logic.Spell;
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
public class SpellTest {

    Spell spell;

    @Before
    public void setUp() {
        spell = Spell.HEAL;
    }

    @Test
    public void getName() {
        assertEquals("Heal", spell.getName());
    }

    @Test
    public void getMana() {
        assertEquals(5, spell.getMana());
    }

}
