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

                gc.setFill(map.getTile(x, y).getType().getColor());
                gc.fillRect(x * map.getTileSize(), y * map.getTileSize(), map.getTileSize(), map.getTileSize());
            }
        }
    }

    public void drawPlayer() {

        gc.setFill(Color.BLACK);
        gc.fillRect(map.getPlayer().x() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                map.getPlayer().y() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                map.getCreatureSize(),
                map.getCreatureSize());

    }

    public void drawEnemies() {

        map.getEnemies().forEach(enemy -> {
            gc.setFill(enemy.color());
            gc.fillOval(enemy.x() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                    enemy.y() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                    map.getCreatureSize(),
                    map.getCreatureSize());
        });

    }
}
