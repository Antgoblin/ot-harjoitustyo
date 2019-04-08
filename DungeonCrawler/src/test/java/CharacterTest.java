/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import dungeoncrawler.Character;
import dungeoncrawler.Direction;
import dungeoncrawler.Map;
import dungeoncrawler.Player;
import dungeoncrawler.Class;

/**
 *
 * @author jy
 */
public class CharacterTest {
    
    Character character;
    
    @Before
    public void setUp() {
        character = new Character("Test", 1, 2, 10, Color.BLACK);
    }
    
    @Test
    public void GettingX(){
        assertEquals( 1, character.X());
    }
    
    @Test
    public void GettingY(){
        assertEquals( 2, character.Y());
    }
    
    @Test
    public void GettingHpWhenFull() {
        assertEquals(10, character.getMaxHp());
        assertEquals(10, character.getCurrentHp());
    } 
    
    @Test
    public void LosingHp() {
        character.loseHp(4);
        assertEquals(10, character.getMaxHp());
        assertEquals(6, character.getCurrentHp());
    } 
    
    @Test
    public void GainingHp() {
        character.loseHp(4);
        character.gainHp(3);
        assertEquals(10, character.getMaxHp());
        assertEquals(9, character.getCurrentHp());
    } 
    
    @Test
    public void GainingHpOverMaxMakesCurrentHpEqualMax() {
        character.loseHp(4);
        character.gainHp(10);
        assertEquals(10, character.getMaxHp());
        assertEquals(10, character.getCurrentHp());
    } 
    
    @Test
    public void CheckkingIfDeadWhenOverZero() {
        character.loseHp(3);
        assertEquals(false, character.checkIfDead());
    }
    
    @Test
    public void GettingHpWhenBelowZero() {
        character.loseHp(12);
        assertEquals(true, character.checkIfDead());
    }
    
    @Test
    public void GettingColor(){
        assertEquals( Color.BLACK, character.color());
    }
    
    @Test
    public void GettingName(){
        assertEquals( "Test", character.getName());
    }
    
    @Test
    public void ActedAndAttackedFalseInBeginning() {
        assertEquals(false, character.hasActed());
        assertEquals(false, character.hasAttacked());
    }
    
    @Test
    public void AttackedTrueAfterAttackingAndGettingLastDamage() {
        character.attacked(5);
        assertEquals(true, character.hasAttacked());
        assertEquals(5, character.getLastDamage());
    }
    
    @Test
    public void HasNotAttackedMakesAttackedEqualFalse() {
        character.attacked(5);
        character.hasNotAttacked();
        assertEquals(false, character.hasAttacked());
        assertEquals(5, character.getLastDamage());
    } 
    
    @Test
    public void setActedWorks() {
        character.setActed(true);
        assertEquals(true, character.hasActed());
        character.setActed(false);
        assertEquals(false, character.hasActed());
    }
    
    @Test
    public void MoveWorks() {
        Map map = new Map(100, 50, new Player(3,3,Class.Warrior));
        character.move(map, Direction.DOWN);
        character.move(map, Direction.RIGHT);
        assertEquals(2, character.X());
        assertEquals(3, character.Y());
        
    }
    
    @Test
    public void SetXAndSetY() {
        character.setX(5);
        character.setY(10);
        assertEquals(10, character.Y());
        assertEquals(5, character.X());
        
    }
    
    
    
    
}
