/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.Player;
import dungeoncrawler.Class;
import dungeoncrawler.Item;
import java.util.ArrayList;
import java.util.List;
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
public class PlayerTest {
    
    Player player;
    
    @Before
    public void setUp() {
        player = new Player(2,3,Class.Ranger);
    }
    
    @Test
    public void getLvl() {
        assertEquals(1, player.getLvl());
    }
    
    @Test
    public void getMana() {
        assertEquals(10, player.currentMana());
        assertEquals(10, player.maxMana());
    }
    
    @Test
    public void LosingMana() {
        assertEquals(true, player.loseMana(5));
        assertEquals(5, player.currentMana());
        assertEquals(10, player.maxMana());
    }
    
    @Test
    public void LosingTooMuchMana() {
        assertEquals(false, player.loseMana(100));
        assertEquals(10, player.currentMana());
        assertEquals(10, player.maxMana());
    }
    
    @Test
    public void GainingMana() {
        player.loseMana(5);
        player.gainMana(3);
        assertEquals(8, player.currentMana());
        assertEquals(10, player.maxMana());
    }
    
    @Test
    public void GainingTooMuchMana() {
        player.loseMana(5);
        player.gainMana(100);
        assertEquals(10, player.currentMana());
        assertEquals(10, player.maxMana());
    }
    
    @Test
    public void GoldAnDExpAtBeginning() {
        assertEquals(0, player.getGold());
        assertEquals(0, player.getExp());
    }
    
    @Test
    public void GainingGoldAnDExp() {
        player.gainExp(10);
        player.gainGold(10);
        assertEquals(10, player.getGold());
        assertEquals(10, player.getExp());
    }
    
    @Test
    public void LosingGoldAnDExp() {
        player.gainExp(10);
        player.gainGold(10);
        player.loseExp(5);
        player.loseGold(5);
        assertEquals(5, player.getGold());
        assertEquals(5, player.getExp());
    }
    
    @Test
    public void GoldAndExpDontGoToNegativesWhenLosingTooMuch() {
        player.gainExp(10);
        player.gainGold(10);
        player.loseExp(500);
        player.loseGold(500);
        assertEquals(0, player.getGold());
        assertEquals(0, player.getExp());
    }
    
    @Test
    public void GettingRange() {
        assertEquals(10, player.getRange());
    }
    
    @Test
    public void GettingWeaponAndWeapon2() {
        assertEquals(Class.Ranger.StartingWeapon(), player.getWeapon());
        assertEquals(Class.Ranger.SecondWeapon(), player.getWeapon2());
    }
    
    @Test
    public void SwitchingWeaponAndWeapon2() {
        player.Switch();
        assertEquals(Class.Ranger.SecondWeapon(), player.getWeapon());
        assertEquals(Class.Ranger.StartingWeapon(), player.getWeapon2());
    }
    
    @Test
    public void GettingInventory() {
        List<Item> items = new ArrayList<>();
        items.add(Class.Ranger.StartingWeapon());
        items.add(Class.Ranger.SecondWeapon());
        assertEquals(items, player.inventory());
    }
    
    @Test
    public void GettingPlayerClass() {
        assertEquals(Class.Ranger, player.getPlayerClass());
    }
    
}
