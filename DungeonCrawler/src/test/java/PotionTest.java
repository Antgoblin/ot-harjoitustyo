/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.Items.Potion;
import dungeoncrawler.Items.PotionType;
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
public class PotionTest {

    Potion potion;

    @Before
    public void setUp() {
        potion = new Potion(PotionType.HP);
    }

    @Test
    public void getHealthAndManaGain() {
        assertEquals(50, potion.getHealthGain());
        assertEquals(0, potion.getManaGain());
    }

    @Test
    public void getAction() {
        assertEquals("Drink", potion.getAction());
    }

    @Test
    public void getPotionFromName() {
        potion = new Potion(PotionType.HP.getPotion("Mana potion"));
        assertEquals("Mana potion", potion.getName());
    }

    @Test
    public void getPotionReturnsNullIfNameIsWrong() {
        assertEquals(null, PotionType.HP.getPotion("Mana p"));
    }

}
