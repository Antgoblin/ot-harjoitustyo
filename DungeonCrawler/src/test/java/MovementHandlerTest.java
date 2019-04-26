/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.Direction;
import dungeoncrawler.Enemy;
import dungeoncrawler.EnemyType;
import dungeoncrawler.Item;
import dungeoncrawler.Item.ItemType;
import dungeoncrawler.Map;
import dungeoncrawler.MovementHandler;
import dungeoncrawler.Player;
import dungeoncrawler.Tiletype;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
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
public class MovementHandlerTest {

    Player player;
    Enemy enemy;
    Map map;
    MovementHandler mh;

    @Before
    public void setUp() {
        player = new Player(2, 2, dungeoncrawler.Class.Warrior);
        map = new Map(100, 50, player);
        enemy = EnemyType.RAT.spawn(5, 5, player);
        map.getEnemies().add(enemy);
        mh = new MovementHandler(map);
    }

    @Test
    public void PlayersMovesToFloor() {
        map.getTile(2, 2).setType(Tiletype.Floor);
        map.getTile(2, 1).setType(Tiletype.Floor);
        mh.move(player, Direction.UP);
        assertEquals(1, player.y());
    }

    @Test
    public void PlayersDoesNotMoveToWall() {
        map.getTile(2, 1).setType(Tiletype.Wall);
        mh.move(player, Direction.UP);
        assertEquals(2, player.y());
    }

    @Test
    public void PlayerOpensDoor() {
        map.getTile(2, 1).setType(Tiletype.Door);
        mh.move(player, Direction.UP);
        assertEquals(2, player.y());
        assertEquals(Tiletype.OpenDoor, map.getTile(2, 1).getType());
    }

    @Test
    public void EnemyMovesWhenLeftIsCloserToPlayer() {
        player.move(map, Direction.DOWN);
        player.move(map, Direction.DOWN);
        player.move(map, Direction.DOWN);
        enemy.wakeUp();
        mh.act(enemy);
        assertEquals(4, enemy.x());
    }

    @Test
    public void EnemyMovesWhenUpIsCloserToPlayer() {
        player.move(map, Direction.RIGHT);
        player.move(map, Direction.RIGHT);
        player.move(map, Direction.RIGHT);
        enemy.wakeUp();
        mh.act(enemy);
        assertEquals(4, enemy.y());
    }

    @Test
    public void EnemyDoesNotMoveWhenSleeping() {
        enemy.fallAsleep();
        mh.act(enemy);
        assertEquals(5, enemy.x());
        assertEquals(5, enemy.y());
    }

    @Test
    public void ClosingDoor() {
        map.getTile(2, 1).setType(Tiletype.OpenDoor);
        mh.closeDoor(player, Direction.UP);
        assertEquals(Tiletype.Door, map.getTile(2, 1).getType());
    }

    @Test
    public void StateIsNormal() {
        assertEquals(MovementHandler.State.Normal, mh.getState());
    }

    @Test
    public void SetandGetStateWork() {
        mh.setState(MovementHandler.State.Help);
        assertEquals(MovementHandler.State.Help, mh.getState());
    }

    @Test
    public void EnemyTileIsOccupied() {
        enemy.wakeUp();
        mh.move(enemy);
        assertEquals(true, map.getTile(4, 5).occupied());
    }

    @Test
    public void Shooting() {
        mh.move(enemy);
        player = new Player(2, 5, dungeoncrawler.Class.Ranger);
        map.setplayer(player);
        mh.shoot(player, Direction.RIGHT);
        System.out.println(enemy.getCurrentHp());
        if (enemy.getCurrentHp() != enemy.getMaxHp()) {
            enemy.move(map, Direction.UP);
        }
        assertEquals(4, enemy.y());
    }

    @Test
    public void Attacking() {
        mh.attack(player, enemy);
        if (enemy.getCurrentHp() != enemy.getMaxHp()) {
            enemy.setX(10);
        }
        assertEquals(10, enemy.x());
    }

    @Test
    public void PickingUpItem() {
        map.getTile(2, 2).setItem(new Item("Test", ItemType.WEAPON));
        mh.pickUp(player);
        assertEquals(2, player.inventory().size());
    }

    @Test
    public void DroppingItems() {
        mh.dropItem(player, 0);
        assertEquals(true, map.getTile(2, 2).containsItem());
    }
}
