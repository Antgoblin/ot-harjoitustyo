/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.logic.Map;
import dungeoncrawler.logic.Player;
import dungeoncrawler.logic.PlayerClass;
import dungeoncrawler.logic.Tile;
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
        player = new Player(1,1,PlayerClass.Warrior);
        map = new Map(100, 50, player);
    }
    
    @Test
    public void GettingSize() {
        assertEquals(100, map.getSize());
    }
    
    @Test
    public void GettingLevel() {
        assertEquals(0, map.level());
    }
    
    @Test
    public void GoDownMakesLevelGoUp() {
        map.goDown();
        assertEquals(1, map.level());
    }
    
    @Test
    public void GoUpMakesLevelGoDown() {
        map.goDown();
        map.goDown();
        map.goDown();
        map.goUp();
        assertEquals(2, map.level());
    }
    
    
}
