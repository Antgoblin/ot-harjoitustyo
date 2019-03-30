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
    private int Level = 0;

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
        if (x < 0 || y < 0 || x >= size || y >= size) {
            Tile tile = new Tile(5, 5);
            tile.setType(Tiletype.OutOfMap);
            return tile;
        } else {
            return tiles[y][x];

        }
    }

    public Tile getRandomTile(Tiletype type) {
        List<Tile> tiles = new ArrayList<>();
        for (int x = 0; x < size - 0; x++) {
            for (int y = 0; y < size - 0; y++) {
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

    // Random Tile from certain direction (Used for creating hallways) 
    public Tile getRandomTile(Tiletype type, Tile limit, Direction dir) {
        // Area where to look for another tile
        // +2/-2 to not get the tile too close from map edges
        int TLx = 2;
        int TLy = 2;
        int BRx = size - 2;
        int BRy = size - 2;

        //Direction where to search
        // +2/-2 to not get the other tile from too close
        switch (dir) {
            case UP:
                BRy = limit.Y() - 2;
                break;
            case DOWN:
                TLy = limit.Y() + 2;
                break;
            case RIGHT:
                TLx = limit.X() + 2;
                break;
            case LEFT:
                BRx = limit.X() - 2;
                break;
        }

        // All possible tiles
        List<Tile> tiles = new ArrayList<>();
        for (int x = TLx; x < BRx; x++) {
            for (int y = TLy; y < BRy; y++) {
                Tile tile = getTile(x, y);
                // Making sure the tile is not on the same side of room as the first tile 
                // for example if both tiles where on the Right side of different rooms the hallway would look weird
                if (tile.getType() == type && (getTile(tile.X() - dir.X(), tile.Y() - dir.Y()).getType() != Tiletype.TempFloor)) {
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
    
    public int Level() {
        return this.Level;
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

    public void createHallway(Tile tile1, Tile tile2) {

        // 2x3 tile parts coming from rooms to make hallways look better
        Direction dir = Direction.UP;
        // Tile 1      
        if (getTile(tile1.X(), tile1.Y() + 1).getType() == Tiletype.TempFloor) {
            dir = Direction.UP;
        } else if (getTile(tile1.X(), tile1.Y() - 1).getType() == Tiletype.TempFloor) {
            dir = Direction.DOWN;
        } else if (getTile(tile1.X() + 1, tile1.Y()).getType() == Tiletype.TempFloor) {
            dir = Direction.LEFT;
        } else if (getTile(tile1.X() - 1, tile1.Y()).getType() == Tiletype.TempFloor) {
            dir = Direction.RIGHT;
        }

        if (random.nextInt(3) < 3) {
            tile1.setType(Tiletype.Door);
        } else {
            tile1.setType(Tiletype.Floor);
        }

        tile1 = getTile(tile1.X() + dir.X(), tile1.Y() + dir.Y());
        tile1.setType(Tiletype.Floor);

        for (int x = tile1.X() - 1; x <= tile1.X() + 1; x++) {
            for (int y = tile1.Y() - 1; y <= tile1.Y() + 1; y++) {
                Tile tile = getTile(x, y);
                if (tile.getType() != Tiletype.Floor && tile.getType() != Tiletype.StairsUp && tile.getType() != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Tile 2
        if (getTile(tile2.X(), tile2.Y() + 1).getType() == Tiletype.TempFloor) {
            dir = Direction.UP;
        } else if (getTile(tile2.X(), tile2.Y() - 1).getType() == Tiletype.TempFloor) {
            dir = Direction.DOWN;
        } else if (getTile(tile2.X() + 1, tile2.Y()).getType() == Tiletype.TempFloor) {
            dir = Direction.LEFT;
        } else if (getTile(tile2.X() - 1, tile2.Y()).getType() == Tiletype.TempFloor) {
            dir = Direction.RIGHT;
        }

        if (random.nextInt(3) < 3) {
            tile2.setType(Tiletype.Door);
        } else {
            tile2.setType(Tiletype.Floor);
        }
        tile2 = getTile(tile2.X() + dir.X(), tile2.Y() + dir.Y());
        tile2.setType(Tiletype.Floor);

        for (int x = tile2.X() - 1; x <= tile2.X() + 1; x++) {
            for (int y = tile2.Y() - 1; y <= tile2.Y() + 1; y++) {
                Tile tile = getTile(x, y);
                if (tile.getType() != Tiletype.Floor && tile.getType() != Tiletype.StairsUp && tile.getType() != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Horizontal Part
        for (int x = Math.min(tile1.X(), tile2.X()); x <= Math.max(tile1.X(), tile2.X()); x++) {
            for (int y = tile1.Y() - 1; y <= tile1.Y() + 1; y++) {
                Tile tile = getTile(x, y);
                Tiletype type = tile.getType();
                if (y == tile1.Y() && type != Tiletype.StairsUp && type != Tiletype.Door) {
                    tile.setType(Tiletype.Floor);
                } else if (type != Tiletype.StairsUp && type != Tiletype.Floor && type != Tiletype.TempFloor && type != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Vertical Part
        for (int x = tile2.X() - 1; x <= tile2.X() + 1; x++) {
            for (int y = Math.min(tile1.Y(), tile2.Y()); y <= Math.max(tile1.Y(), tile2.Y()); y++) {
                Tile tile = getTile(x, y);
                Tiletype type = tile.getType();
                if (x == tile2.X() && type != Tiletype.StairsUp && type != Tiletype.StairsDown && type != Tiletype.Door) {
                    tile.setType(Tiletype.Floor);
                } else if (type != Tiletype.StairsUp && type != Tiletype.StairsDown && type != Tiletype.Floor && type != Tiletype.TempFloor && type != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Corner piece
        Tile tile = null;
        if (tile1.Y() > tile2.Y() && tile1.X() > tile2.X()) {
            tile = getTile(tile2.X() - 1, tile1.Y() + 1);
        } else if (tile1.Y() > tile2.Y() && tile1.X() < tile2.X()) {
            tile = getTile(tile2.X() + 1, tile1.Y() + 1);
        } else if (tile1.Y() < tile2.Y() && tile1.X() > tile2.X()) {
            tile = getTile(tile2.X() - 1, tile1.Y() - 1);
        } else if (tile1.Y() < tile2.Y() && tile1.X() < tile2.X()) {
            tile = getTile(tile2.X() + 1, tile1.Y() - 1);
        }

        if (tile != null) {
            if (tile.getType() != Tiletype.Floor && tile.getType() != Tiletype.TempFloor) {
                tile.setType(Tiletype.Wall);
            }

        }

    }

    public void createTempRoom(int TopLeftX, int TopLeftY, int BottomRightX, int BottomRightY) {
        for (int x = TopLeftX; x <= BottomRightX; x++) {
            for (int y = TopLeftY; y <= BottomRightY; y++) {
                //corners
                if ((x == TopLeftX && y == TopLeftY) || (x == BottomRightX && y == BottomRightY) || (x == TopLeftX && y == BottomRightY) || (x == BottomRightX && y == TopLeftY)) {
                    getTile(x, y).setType(Tiletype.Corner);
                    //walls
                } else if (x == TopLeftX || x == BottomRightX || y == TopLeftY || y == BottomRightY) {
                    getTile(x, y).setType(Tiletype.TempWall);
                    //floor    
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

    public List<Tile> checkIfUnited(List<Tile> tiles) {
        List<Tile> searched = new ArrayList<>();
        tiles.forEach(searchtile -> {
            for (int x = searchtile.X() - 1; x <= searchtile.X() + 1; x++) {
                for (int y = searchtile.Y() - 1; y <= searchtile.Y() + 1; y++) {
                    Tile tile = getTile(x, y);
                    if (tile != null && tile.getType() == Tiletype.Floor) {
                        tile.setType(Tiletype.CheckedFloor);
                        searched.add(tile);
                    } else if (tile != null && tile.getType() == Tiletype.TempFloor) {
                        tile.setType(Tiletype.CheckedTempFloor);
                        searched.add(tile);
                    } else if (tile != null && tile.getType() == Tiletype.Door) {
                        tile.setType(Tiletype.CheckedDoor);
                        searched.add(tile);
                    }
                }
            }
        });

        return searched;
    }

    public void createLevel() {

        //Makes all tiles Void
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = getTile(x, y);
                tile.setType(Tiletype.Void);
                tile.setCharacter(null);
            }
        }
        
        enemies.removeAll(enemies);
        
        
        // creates rooms
        int roomscreated = 0;
        int roomswanted = random.nextInt(7) + 10;
        
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
//        // creates stairs up and down
//        Tile stairs = getRandomTile(Tiletype.TempFloor);
//        stairs.setType(Tiletype.StairsUp);
//        
//        int stairsDown = random.nextInt(3);
//        for (int s = 0; s <= stairsDown; s++ ) {
//            getRandomTile(Tiletype.TempFloor).setType(Tiletype.StairsDown);
//        }
        

        boolean floorIsUnited = false;
        
        // creates hallways
        while (!floorIsUnited) {

            List<Tile> floorTiles = new ArrayList<>();
            floorTiles.add(getRandomTile(Tiletype.TempFloor));

            //Creates hallway
            Tile randomtile = getRandomTile(Tiletype.TempWall);
            Direction dir = Direction.DOWN;
            if (getTile(randomtile.X(), randomtile.Y() + 1).getType() == Tiletype.TempFloor) {
                dir = Direction.UP;
            } else if (getTile(randomtile.X() + 1, randomtile.Y()).getType() == Tiletype.TempFloor) {
                dir = Direction.LEFT;
            } else if (getTile(randomtile.X() - 1, randomtile.Y()).getType() == Tiletype.TempFloor) {
                dir = Direction.RIGHT;
            }

            Tile secondrandomtile = getRandomTile(Tiletype.TempWall, randomtile, dir);
            if (secondrandomtile != null) {
                createHallway(randomtile, secondrandomtile);
            }

            //Then checks if all the floor tiles are connected (meaning that every room can be accessed)
            // Makes floor tiles from floor-tiles into checkedfloor-tiles
            for (int i = 0; i < 5000; i++) {
                floorTiles = checkIfUnited(floorTiles);
            }

            //if there are no floor tiles on the map the level structure is done and both Tiles are null
            Tile floor = getRandomTile(Tiletype.Floor);
            Tile TempFloor = getRandomTile(Tiletype.TempFloor);

            // turns all checked tiles back to normals
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    Tile tile = getTile(x, y);
                    if (tile.getType() == Tiletype.CheckedFloor) {
                        tile.setType(Tiletype.Floor);
                    } else if (tile.getType() == Tiletype.CheckedDoor) {
                        tile.setType(Tiletype.Door);
                    } else if (tile.getType() == Tiletype.CheckedTempFloor) {
                        tile.setType(Tiletype.TempFloor);
                    }
                }
            }

            // if both tiles are null ends the Level creation
            if (floor == null && TempFloor == null) {
                floorIsUnited = true;
            }

        }
        
        // creates stairs up and down
        Tile stairs = getRandomTile(Tiletype.TempFloor);
        stairs.setType(Tiletype.StairsUp);
        
        int stairsDown = random.nextInt(3);
        for (int s = 0; s <= stairsDown; s++ ) {
            getRandomTile(Tiletype.TempFloor).setType(Tiletype.StairsDown);
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = getTile(x, y);
                switch (tile.getType()) {
                    case TempFloor:
                        tile.setType(Tiletype.Floor);
                        break;
                    case TempWall:
                        tile.setType(Tiletype.Wall);
                        break;
                    case Corner:
                        tile.setType(Tiletype.Wall);
                        break;
                    case CheckedDoor:
                        tile.setType(Tiletype.Door);
                        break;
                    default:
                        break;
                }
            }
        }
        
        //Spawn enemies
        int enemies = roomswanted + 10 + random.nextInt(10);
        for(int e = 0; e <= enemies; e++) {
            spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));
            
        }

    }
    
    public void goDown() {
        this.Level++;
        createLevel();
        Tile stairs = getRandomTile(Tiletype.StairsUp);
        player.setX(stairs.X());
        player.setY(stairs.Y());
    }
    
    public void goUp() {
        this.Level--;
        createLevel();
        Tile stairs = getRandomTile(Tiletype.StairsDown);
        player.setX(stairs.X());
        player.setY(stairs.Y());
    }

}
