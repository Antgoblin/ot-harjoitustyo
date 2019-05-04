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
    private int level = 0;

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
        int minX = 2;
        int minY = 2;
        int maxX = size - 2;
        int maxY = size - 2;

        //Direction where to search
        // +2/-2 to not get the other tile from too close
        if (dir == Direction.UP) {
            maxY = limit.y() - 2;
        } else if (dir == Direction.DOWN) {
            minY = limit.y() + 2;
        } else if (dir == Direction.RIGHT) {
            minX = limit.x() + 2;
        } else if (dir == Direction.LEFT) {
            maxX = limit.x() - 2;
        }

        return getRandomTile(minX, maxX, minY, maxY, type, dir);

    }

    public Tile getRandomTile(int minX, int maxX, int minY, int maxY, Tiletype type, Direction dir) {
        List<Tile> tiles = new ArrayList<>();
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Tile tile = getTile(x, y);
                // Making sure the tile is not on the same side of room as the first tile 
                // for example if both tiles where on the Right side of different rooms the hallway would look weird
                if (tile.getType() == type && (getTile(tile.x() - dir.x(), tile.y() - dir.y()).getType() != Tiletype.TempFloor)) {
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

    public Tile getEmptyTile() {
        Tile tile = getRandomTile(Tiletype.Floor);
        while (tile.occupied()) {
            tile = getRandomTile(Tiletype.Floor);
        }
        return tile;
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

    public int level() {
        return this.level;
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
        Tile tile = getTile(enemy.x(), enemy.y());
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
        enemy.setX(tile.x());
        enemy.setY(tile.y());
        this.enemies.add(enemy);
        tile.setCharacter(enemy);
    }

    public void spawnItemRandom(Item item) {
        Tile tile = getRandomTile(Tiletype.Floor);
        tile.setItem(item);
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
            if (enemy.y() == y && enemy.x() == x) {
                enemiesInTile.add(enemy);
            }

        });
        return enemiesInTile.get(0);
    }

    public Enemy getClosestEnemy() {
        Enemy closestEnemy = enemies.get(0);
        int distance = Math.max(Math.abs(player.x() - closestEnemy.x()), Math.abs(player.y() - closestEnemy.y()));
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            int d = Math.max(Math.abs(player.x() - e.x()), Math.abs(player.y() - e.y()));
            if (d < distance) {
                distance = d;
                closestEnemy = e;
            }
        }
        return closestEnemy;
    }

    public void createRoom(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        for (int x = topLeftX; x <= bottomRightX; x++) {
            for (int y = topLeftY; y <= bottomRightY; y++) {
                if (x == topLeftX || x == bottomRightX || y == topLeftY || y == bottomRightY) {
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
        if (getTile(tile1.x(), tile1.y() + 1).getType() == Tiletype.TempFloor) {
            dir = Direction.UP;
        } else if (getTile(tile1.x(), tile1.y() - 1).getType() == Tiletype.TempFloor) {
            dir = Direction.DOWN;
        } else if (getTile(tile1.x() + 1, tile1.y()).getType() == Tiletype.TempFloor) {
            dir = Direction.LEFT;
        } else if (getTile(tile1.x() - 1, tile1.y()).getType() == Tiletype.TempFloor) {
            dir = Direction.RIGHT;
        }

        if (random.nextInt(3) < 3) {
            tile1.setType(Tiletype.Door);
        } else {
            tile1.setType(Tiletype.Floor);
        }

        tile1 = getTile(tile1.x() + dir.x(), tile1.y() + dir.y());
        tile1.setType(Tiletype.Floor);

        for (int x = tile1.x() - 1; x <= tile1.x() + 1; x++) {
            for (int y = tile1.y() - 1; y <= tile1.y() + 1; y++) {
                Tile tile = getTile(x, y);
                if (tile.getType() != Tiletype.Floor && tile.getType() != Tiletype.StairsUp && tile.getType() != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Tile 2
        if (getTile(tile2.x(), tile2.y() + 1).getType() == Tiletype.TempFloor) {
            dir = Direction.UP;
        } else if (getTile(tile2.x(), tile2.y() - 1).getType() == Tiletype.TempFloor) {
            dir = Direction.DOWN;
        } else if (getTile(tile2.x() + 1, tile2.y()).getType() == Tiletype.TempFloor) {
            dir = Direction.LEFT;
        } else if (getTile(tile2.x() - 1, tile2.y()).getType() == Tiletype.TempFloor) {
            dir = Direction.RIGHT;
        }

        if (random.nextInt(3) < 3) {
            tile2.setType(Tiletype.Door);
        } else {
            tile2.setType(Tiletype.Floor);
        }
        tile2 = getTile(tile2.x() + dir.x(), tile2.y() + dir.y());
        tile2.setType(Tiletype.Floor);

        for (int x = tile2.x() - 1; x <= tile2.x() + 1; x++) {
            for (int y = tile2.y() - 1; y <= tile2.y() + 1; y++) {
                Tile tile = getTile(x, y);
                if (tile.getType() != Tiletype.Floor && tile.getType() != Tiletype.StairsUp && tile.getType() != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Horizontal Part
        for (int x = Math.min(tile1.x(), tile2.x()); x <= Math.max(tile1.x(), tile2.x()); x++) {
            for (int y = tile1.y() - 1; y <= tile1.y() + 1; y++) {
                Tile tile = getTile(x, y);
                Tiletype type = tile.getType();
                if (y == tile1.y() && type != Tiletype.StairsUp && type != Tiletype.Door) {
                    tile.setType(Tiletype.Floor);
                } else if (type != Tiletype.StairsUp && type != Tiletype.Floor && type != Tiletype.TempFloor && type != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Vertical Part
        for (int x = tile2.x() - 1; x <= tile2.x() + 1; x++) {
            for (int y = Math.min(tile1.y(), tile2.y()); y <= Math.max(tile1.y(), tile2.y()); y++) {
                Tile tile = getTile(x, y);
                Tiletype type = tile.getType();
                if (x == tile2.x() && type != Tiletype.StairsUp && type != Tiletype.StairsDown && type != Tiletype.Door) {
                    tile.setType(Tiletype.Floor);
                } else if (type != Tiletype.StairsUp && type != Tiletype.StairsDown && type != Tiletype.Floor && type != Tiletype.TempFloor && type != Tiletype.Door) {
                    tile.setType(Tiletype.Wall);
                }
            }
        }

        //Corner piece
        Tile tile = null;
        if (tile1.y() > tile2.y() && tile1.x() > tile2.x()) {
            tile = getTile(tile2.x() - 1, tile1.y() + 1);
        } else if (tile1.y() > tile2.y() && tile1.x() < tile2.x()) {
            tile = getTile(tile2.x() + 1, tile1.y() + 1);
        } else if (tile1.y() < tile2.y() && tile1.x() > tile2.x()) {
            tile = getTile(tile2.x() - 1, tile1.y() - 1);
        } else if (tile1.y() < tile2.y() && tile1.x() < tile2.x()) {
            tile = getTile(tile2.x() + 1, tile1.y() - 1);
        }

        if (tile != null) {
            if (tile.getType() != Tiletype.Floor && tile.getType() != Tiletype.TempFloor) {
                tile.setType(Tiletype.Wall);
            }

        }

    }

    public void createTempRoom(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        for (int x = topLeftX; x <= bottomRightX; x++) {
            for (int y = topLeftY; y <= bottomRightY; y++) {
                //corners
                if ((x == topLeftX && y == topLeftY) || (x == bottomRightX && y == bottomRightY) || (x == topLeftX && y == bottomRightY) || (x == bottomRightX && y == topLeftY)) {
                    getTile(x, y).setType(Tiletype.Corner);
                    //walls
                } else if (x == topLeftX || x == bottomRightX || y == topLeftY || y == bottomRightY) {
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

        for (int i = topLeft.x(); i <= topLeft.x() + x; i++) {
            for (int j = topLeft.y(); j <= topLeft.y() + y; j++) {
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
            for (int x = searchtile.x() - 1; x <= searchtile.x() + 1; x++) {
                for (int y = searchtile.y() - 1; y <= searchtile.y() + 1; y++) {
                    Tile tile = getTile(x, y);
                    if (checkTile(tile)) {
                        searched.add(tile);
                    }
//                    if (tile != null && tile.getType() == Tiletype.Floor) {
//                        tile.setType(Tiletype.CheckedFloor);
//                        searched.add(tile);
//                    } else if (tile != null && tile.getType() == Tiletype.TempFloor) {
//                        tile.setType(Tiletype.CheckedTempFloor);
//                        searched.add(tile);
//                    } else if (tile != null && tile.getType() == Tiletype.Door) {
//                        tile.setType(Tiletype.CheckedDoor);
//                        searched.add(tile);
//                    }
                }
            }
        });
        return searched;
    }

    public boolean checkTile(Tile tile) {
        switch (tile.getType()) {
            case Floor:
                tile.setType(Tiletype.CheckedFloor);
                return true;
            case TempFloor:
                tile.setType(Tiletype.CheckedTempFloor);
                return true;
            case Door:
                tile.setType(Tiletype.CheckedDoor);
                return true;
            default:
                return false;
        }
    }

    public void clearTiles() {
        //Makes all tiles Void
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = getTile(x, y);
                tile.setType(Tiletype.Void);
                tile.setCharacter(null);
            }
        }
    }

    public void createLevel() {

        clearTiles();
        enemies.removeAll(enemies);
        clearItems();

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
                createTempRoom(randomtile.x(), randomtile.y(), randomtile.x() + x, randomtile.y() + y);
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
            if (getTile(randomtile.x(), randomtile.y() + 1).getType() == Tiletype.TempFloor) {
                dir = Direction.UP;
            } else if (getTile(randomtile.x() + 1, randomtile.y()).getType() == Tiletype.TempFloor) {
                dir = Direction.LEFT;
            } else if (getTile(randomtile.x() - 1, randomtile.y()).getType() == Tiletype.TempFloor) {
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
            Tile tempFloor = getRandomTile(Tiletype.TempFloor);

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
            if (floor == null && tempFloor == null) {
                floorIsUnited = true;
            }

        }

        // creates stairs up and down
        Tile stairs = getRandomTile(Tiletype.TempFloor);
        stairs.setType(Tiletype.StairsUp);

        int stairsDown = random.nextInt(3);
        for (int s = 0; s <= stairsDown; s++) {
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
        // Destroys faulty doors
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (getTile(x, y).getType() == Tiletype.Door) {
                    if ((getTile(x + 1, y).getType() != Tiletype.Wall || getTile(x - 1, y).getType() != Tiletype.Wall) && (getTile(x, y + 1).getType() != Tiletype.Wall || getTile(x, y - 1).getType() != Tiletype.Wall)) {
                        getTile(x, y).setType(Tiletype.Floor);
                    }
                }
            }
        }

        //Spawn enemies
        int amount = roomswanted + random.nextInt(10);
        spawnEnemies(amount);
        spawnItems(amount);

    }

    public void spawnEnemies(int amount) {
        List<EnemyType> enemies = EnemyType.RAT.randomize(level, amount);

        enemies.forEach(enemy -> {
            spawnEnemyRandom(enemy.spawn(player));
        });
    }

    public void spawnItems(int amount) {
        //Spawn weapons
        List<WeaponType> weapons = WeaponType.DAGGER.randomize(level, amount / 2);
        weapons.forEach(w -> {
            spawnItemRandom(new Weapon(w));
        });

        //spawn potions
        List<PotionType> potions = PotionType.HP.randomize(level, amount / 2);
        potions.forEach(p -> {
            spawnItemRandom(new Potion(p));
        });

        //spawn spellbooks
        spawnItemRandom(new Spellbook(SpellbookType.FIREBOLTBOOK.Randomize()));
    }

    public void clearItems() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = getTile(x, y);
                tile.removeItems();
            }
        }
    }

    public void goDown() {
        this.level++;
        createLevel();
        Tile stairs = getRandomTile(Tiletype.StairsUp);
        player.setX(stairs.x());
        player.setY(stairs.y());
    }

    public void goUp() {
        this.level--;
        if (this.level == 0) {
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    Tile tile = getTile(x, y);
                    tile.setType(Tiletype.Void);
                    tile.setCharacter(null);
                }
            }

            enemies.removeAll(enemies);
            clearItems();
            createRoom(10, 10, 14, 14);
            getTile(12, 12).setType(Tiletype.StairsDown);

        } else {
            createLevel();
        }

        Tile stairs = getRandomTile(Tiletype.StairsDown);
        player.setX(stairs.x());
        player.setY(stairs.y());
    }

}
