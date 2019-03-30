/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author jy
 */
public class MapDrawer {
    private Map map;
    private GraphicsContext gc;
    
    public MapDrawer(Map map, GraphicsContext gc) {
        this.map = map;
        this.gc = gc;
        
    }
    
    public void drawAll() {
        drawTiles();
        drawGrid();
        drawPlayer();
        drawEnemies();
    }
    
    public void drawGrid() {
        
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < map.getSize() + 1; i++) {
            gc.strokeLine(0, i * map.getTileSize(), map.getSize() * map.getTileSize(), i * map.getTileSize());
        }
        for (int j = 0; j < map.getSize() + 1; j++) {
            gc.strokeLine(j * map.getTileSize(), 0, j * map.getTileSize(), map.getSize() * map.getTileSize());
        }

    }
    
    public void drawTiles() {
        
        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
               
                switch (map.getTile(x, y).getType()) {
                    case Floor:
                        gc.setFill(Color.WHITE);
                        break;
                        
                    case Door:                        
                        gc.setFill(Color.SIENNA);
                        break;
                        
                    case OpenDoor:
                        gc.setFill(Color.WHITE);
                        break;
                    
                    case Wall:
                        gc.setFill(Color.BLACK);
                        break;
                    case TempFloor:
                        gc.setFill(Color.LIGHTBLUE);
                        break;
                    case TempWall:
                        gc.setFill(Color.RED);
                        break;
                    case StairsUp:
                        gc.setFill(Color.BLUEVIOLET);
                        break;
                    case Corner:
                        gc.setFill(Color.AQUAMARINE);
                        break;
                    case CheckedFloor:
                        gc.setFill(Color.ORANGE);
                        break;
                    case CheckedDoor:
                        gc.setFill(Color.CHARTREUSE);
                        break;    
                    default:
                        gc.setFill(Color.GRAY);
                        break;
                }
                gc.fillRect(x * map.getTileSize(), y * map.getTileSize(), map.getTileSize(), map.getTileSize());
            }
        }
    }
    
    public void drawPlayer() {
        
        gc.setFill(Color.BLACK);
        gc.fillRect(map.getPlayer().X() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                map.getPlayer().Y() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                map.getCreatureSize(),
                map.getCreatureSize());
        
    }

    public void drawEnemies() {
        
        map.getEnemies().forEach( enemy -> {
            gc.setFill(enemy.color());
            gc.fillOval(enemy.X() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                    enemy.Y() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                    map.getCreatureSize(),
                    map.getCreatureSize());
        });
        
    }
}
