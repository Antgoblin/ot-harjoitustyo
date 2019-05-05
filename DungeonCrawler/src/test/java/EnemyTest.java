/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.logic.Enemy;
import dungeoncrawler.logic.Character;
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
public class EnemyTest {
    
    Enemy enemy;
    Character target;
    
    @Before
    public void setUp() {
        target = new Character("Target", 2, 2, 10, Color.BLACK);
        enemy = new Enemy("Name", 2, 3, 10, Color.BLACK, 2, 3, 10, 10, target);
        
    }
    
    @Test
    public void gettingExp() {
        assertEquals(10, enemy.getExp());
    }
    
    @Test
    public void gettingTarget() {
        assertEquals(target, enemy.getTarget());
    }
    
    @Test
    public void settingTarget() {
        Character test = new Character("Target", 5, 5, 10, Color.BLACK); 
        enemy.setTarget(test);
        assertEquals(test, enemy.getTarget());
    }
    
    @Test
    public void gettingAggressionRange() {
        assertEquals(10, enemy.aggressionRange());
    }
    
    @Test
    public void gettingAggressionRangeAfterRage() {
        enemy.rage();
        assertEquals(100, enemy.aggressionRange());
    }
    
    
    
}
