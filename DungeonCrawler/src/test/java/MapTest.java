/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.Map;
import dungeoncrawler.Player;
import dungeoncrawler.Class;
import dungeoncrawler.Tile;
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
public class MapTest {
    
    Map map;
    Player player;
    
    @Before
    public void setUp() {
        player = new Player(1,1,Class.Warrior);
        map = new Map(100, 50, player);
    }
    
    @Test
    public void GettingSize() {
        assertEquals(100, map.getSize());
    }
    
    @Test
    public void GettingLevel() {
        assertEquals(0, map.Level());
    }
    
    @Test
    public void GoDownMakesLevelGoUp() {
        map.goDown();
        assertEquals(1, map.Level());
    }
    
    @Test
    public void GoUpMakesLevelGoDown() {
        map.goDown();
        map.goDown();
        map.goDown();
        map.goUp();
        assertEquals(2, map.Level());
    }
    
    
}
