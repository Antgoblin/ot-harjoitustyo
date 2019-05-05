/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.logic.Tile;
import dungeoncrawler.logic.Character;
import dungeoncrawler.logic.Tiletype;
import javafx.scene.paint.Color;
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
public class TileTest {
    
    Tile tile;
    
    @Before
    public void setUp() {
        tile = new Tile(1,10);     
    }
    
    @Test
    public void gettingX() {
        assertEquals(1, tile.x());
    }
    
    @Test
    public void gettingY() {
        assertEquals(10, tile.y());
    }
    
    @Test
    public void TiletypeIsVoid() {
        assertEquals(Tiletype.Void, tile.getType());
    }
    
    @Test
    public void TiletypyAfterSettingIT() {
        tile.setType(Tiletype.Floor);
        assertEquals(Tiletype.Floor, tile.getType());
    }
    
    @Test
    public void occupiedIsFalse() {
        assertEquals(false, tile.occupied());
    }
    
    @Test
    public void occupiedIsTrueIfCharacter() {
        tile.setCharacter(new Character("Test", 2, 2, 10, Color.BLACK));
        assertEquals(true, tile.occupied());
    }
    
    @Test
    public void GettingCharacter() {
        assertEquals(null, tile.getCharacter());
        Character Test = new Character("Test", 2, 2, 10, Color.BLACK);
        tile.setCharacter(Test);
        assertEquals(Test, tile.getCharacter());
    }
}
