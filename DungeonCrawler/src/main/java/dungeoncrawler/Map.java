/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    private Random random = new Random();

    public Map(int size, int tileSize, Player player) {
        this.size = size;
        this.tileSize = tileSize;
        this.creatureSize = tileSize * 2 / 5;
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
        if(x < 0 || y < 0 || x >= size || y >= size) {
            Tile tile = new Tile(5, 5);
            tile.setType(Tiletype.OutOfMap);
            return tile;
        } else {
            return tiles[y][x];
            
        }
    }

    public Tile getRandomTile(Tiletype type) {
        List<Tile> tiles = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = getTile(x, y);
                if (tile.getType() == type) {
                    tiles.add(tile);
                }
            }
        }
        Collections.shuffle(tiles);
        if (!tiles.isEmpty()) {
            return tiles.get(0);
        } else {
            return null;
        }
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
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Tile tile = getTile(i, j);
                if (tile.getType() == Tiletype.Floor && !tile.occupied()) {
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

    public Enemy getEnemy(int x, int y) {
        List<Enemy> enemiesInTile = new ArrayList<>();
        enemies.forEach(enemy -> {
            if (enemy.Y() == y && enemy.X() == x) {
                enemiesInTile.add(enemy);
            }

        });
        return enemiesInTile.get(0);
    }

    public void createRoom(int TopLeftX, int TopLeftY, int BottomRightX, int BottomRightY) {
        for (int x = TopLeftX; x <= BottomRightX; x++) {
            for (int y = TopLeftY; y <= BottomRightY; y++) {
                if (x == TopLeftX || x == BottomRightX || y == TopLeftY || y == BottomRightY) {
                    getTile(x, y).setType(Tiletype.Wall);
                } else {
                    getTile(x, y).setType(Tiletype.Floor);
                }
            }
        }
    }

    public void createTempRoom(int TopLeftX, int TopLeftY, int BottomRightX, int BottomRightY) {
        for (int x = TopLeftX; x <= BottomRightX; x++) {
            for (int y = TopLeftY; y <= BottomRightY; y++) {
                if (x == TopLeftX || x == BottomRightX || y == TopLeftY || y == BottomRightY) {
                    getTile(x, y).setType(Tiletype.TempWall);
                } else if (x == TopLeftX + 1 && y == TopLeftY + 1) {
                    getTile(x, y).setType(Tiletype.RoomIndicator);
                } else {
                    getTile(x, y).setType(Tiletype.TempFloor);
                }
            }
        }
    }

    public void createDoor(int x, int y) {
        getTile(x, y).setType(Tiletype.Door);
    }

    public List<Tile> getArea(Tile topLeft, int x, int y) {

        List<Tile> area = new ArrayList<>();
        Tiletype type = topLeft.getType();

        for (int i = topLeft.X(); i <= topLeft.X() + x; i++) {
            for (int j = topLeft.Y(); j <= topLeft.Y() + y; j++) {
                Tile tile = getTile(i, j);
                if (tile.getType() == type) {
                    area.add(tile);
                }
            }
        }

        if (area.size() == (x + 1) * (y + 1)) {
            return area;
        } else {
            return null;
        }
    }

    public void createLevel() {

        int roomscreated = 0;
        int roomswanted = random.nextInt(7) + 8;
        while (roomscreated < roomswanted) {
            Tile randomtile = getRandomTile(Tiletype.Void);
            int x = random.nextInt(10) + 5;
            int y = random.nextInt(10) + 5;
            if (randomtile == null) {
                break;
            }
            List<Tile> area = getArea(randomtile, x, y);
            if (area != null) {
                createTempRoom(randomtile.X(), randomtile.Y(), randomtile.X() + x, randomtile.Y() + y);
                roomscreated++;
            }

        }
        
        Tile spawn = getRandomTile(Tiletype.TempFloor);
        spawn.setType(Tiletype.StairsUp);

    }

}
