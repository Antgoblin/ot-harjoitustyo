/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author jy
 */
public class Map {
    
//    private List<Tile> tiles;
    private Tile[][] tiles;
    private int size;
    private int tileSize;
    private int creatureSize;
    private List<Enemy> enemies = new ArrayList<>();
    private Player player;
    
    public Map(int size, int tileSize, Player player) {
        this.size = size;
        this.tileSize = tileSize;
        this.creatureSize = tileSize * 2/5;
        this.player = player;
        this.tiles = new Tile[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                tiles[x][y] = new Tile(y, x);
            } 
        }
    }
    
    public Tile[][] getTiles() {
        return this.tiles;
    }
    
    public Tile getTile(int x, int y) {
        //tarkoituksella väärinpäin.
        return tiles[y][x];
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getTileSize() {
        return this.tileSize;
    }
    
    public int getCreatureSize() {
        return this.creatureSize;
    }
    
    public void setplayer(Player player) {
        this.player = player;
        enemies.forEach(enemy -> {
            enemy.setTarget(player);
        });
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void spawnEnemy(Enemy enemy) {
        enemies.add(enemy);
        Tile tile = getTile(enemy.X(), enemy.Y());
        tile.setCharacter(enemy);
    }
    
    public void spawnEnemyRandom(Enemy enemy) {
        List<Tile> vapaat = new ArrayList<>(); 
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                Tile tile = getTile(i, j);
               if(tile.getType() == Tiletype.Floor && !tile.occupied()) {
                   vapaat.add(tile);
               }  
            }
        }
        Collections.shuffle(vapaat);
        Tile tile = vapaat.get(0);
        enemy.setX(tile.X());
        enemy.setY(tile.Y());
        this.enemies.add(enemy);
        tile.setCharacter(enemy);
        
    }
    
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    public void createRoom(int TopLeftX, int TopLeftY, int BottomRightX, int BottomRightY) {
        for( int x = TopLeftX; x <= BottomRightX; x++) {
            for(int y = TopLeftY; y <= BottomRightY; y++) {
                if(x == TopLeftX || x == BottomRightX || y == TopLeftY || y == BottomRightY) {
                    getTile(x,y).setType(Tiletype.Wall);
                } else {
                    getTile(x,y).setType(Tiletype.Floor);
                }
            }
        }        
    }
    
    public void createDoor(int x, int y) {
        getTile(x,y).setType(Tiletype.Door);
    }
}


