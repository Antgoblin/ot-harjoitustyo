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

    /**
     * Metodi piirtää ruudukon, ruudut, pelaajan, vhiholliset ja esineet
     */
    public void drawAll() {
        drawTiles();
        drawGrid();
        drawPlayer();
        drawEnemies();
        drawItems();
    }

    /**
     * Metodi piirtää canvastaululle mustia viivoja kartan ruutujen verran.
     * Viivojen väli on kartassa oleva ruudunkoko
     */
    public void drawGrid() {

        gc.setStroke(Color.BLACK);
        for (int i = 0; i < map.getSize() + 1; i++) {
            gc.strokeLine(0, i * map.getTileSize(), map.getSize() * map.getTileSize(), i * map.getTileSize());
        }
        for (int j = 0; j < map.getSize() + 1; j++) {
            gc.strokeLine(j * map.getTileSize(), 0, j * map.getTileSize(), map.getSize() * map.getTileSize());
        }

    }

    /**
     * Metodi maalaa neliöitä canvastaululle. neliön sivun koko on sama kuin
     * kartan ruudunkoko ja neliön värin riippuu kartassa olevien ruutujen
     * tyypistä.
     */
    public void drawTiles() {

        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {

                gc.setFill(map.getTile(x, y).getType().getColor());
                gc.fillRect(x * map.getTileSize(), y * map.getTileSize(), map.getTileSize(), map.getTileSize());
            }
        }
    }

    /**
     * Metodi piirtää canvastaululle pienen mustan neliön pelaajan
     * koordinaattien kohtaan
     */
    public void drawPlayer() {

        gc.setFill(Color.BLACK);
        gc.fillRect(map.getPlayer().x() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                map.getPlayer().y() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                map.getCreatureSize(),
                map.getCreatureSize());

    }

    /**
     * Metodi piitää pienen ympyrän jokaiseen kartalla olevan vihollisen
     * koordinaatteihin. ympyrän väri riippuu vihollisen tyypistä
     */
    public void drawEnemies() {

        map.getEnemies().forEach(enemy -> {
            gc.setFill(enemy.color());
            gc.fillOval(enemy.x() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                    enemy.y() * map.getTileSize() + (map.getTileSize() - map.getCreatureSize()) / 2,
                    map.getCreatureSize(),
                    map.getCreatureSize());
        });

    }

    /**
     * Metodi piirtää canvastaululle vinottaisen viivan jokaiseen kartan ruutuun
     * jossa on yksi tai useampi esine.
     */
    public void drawItems() {
        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
                if (map.getTile(x, y).containsItem()) {
                    gc.setFill(Color.BLACK);
                    gc.strokeLine(x * map.getTileSize() + map.getTileSize() / 4, y * map.getTileSize() + map.getTileSize() / 4, (x + 1) * map.getTileSize() - map.getTileSize() / 4, (y + 1) * map.getTileSize() - map.getTileSize() / 4);
                }
            }
        }

    }

    /**
     * Metodi pyyhkii canvastaulun ja piirtää tekstinä jokaisen kartan pelaajan
     * esineen. Metodi piitää myös kehän tekstin ympärille jossa sille annetun
     * valitsijan koordinaatit ovat. Lisäksi valitsija viereen piirtyy tekstit
     * mitä esineelle voi tehdä
     *
     * @param chooser valitsija joka kertoo missä kohtaa esine listää mennään
     */
    public void drawInventory(Chooser chooser) {

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, map.getSize() * map.getTileSize(), map.getSize() * map.getTileSize());
        gc.strokeText("Inventory:  (esc to exit) ", 10, 20, 1000);
        List<Item> items = map.getPlayer().inventory();
        if (!items.isEmpty()) {
            for (int i = 0; i < map.getPlayer().inventory().size(); i++) {
                String text = " - " + items.get(i).getName();
                gc.strokeText(text, 10, i * 20 + 40);
            }
        }
        gc.strokeRect(chooser.getX() * 170, chooser.getY() * 20 + 25, 170, 20);
        gc.strokeText("Drop", 230, chooser.getY() * 20 + 40);
        if (!items.isEmpty()) {
            gc.strokeText(items.get(chooser.getY()).getAction(), 400, chooser.getY() * 20 + 40);

        }
    }

    public void drawSpells(Chooser chooser) {

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, map.getSize() * map.getTileSize(), map.getSize() * map.getTileSize());
        gc.strokeText("Known Spells:  (esc to exit) ", 10, 20, 1000);
        List<Spell> spells = map.getPlayer().spells();
        for (int i = 0; i < map.getPlayer().inventory().size(); i++) {
            String text = " - " + spells.get(i).getName();
            gc.strokeText(text, 10, i * 20 + 40);
        }
        gc.strokeRect(chooser.getX() * 170, chooser.getY() * 20 + 25, 170, 20);
        gc.strokeText("Cast (" + spells.get(chooser.getX()).getMana() + ")", 230, chooser.getY() * 20 + 40);
    }

    /**
     * Metodi pyyhkii canvastaulun ja piirtää tekstinä ohjeet mitä kaikki
     * näppäimet tekevät pelissä
     */
    public void drawHelpScreen() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, map.getSize() * map.getTileSize(), map.getSize() * map.getTileSize());
        gc.strokeText("Controls: (esc to exit)", 10, 20);
        gc.strokeText("ArrowKeys: Move", 10, 40);
        gc.strokeText("SPACE: Wait a turn", 10, 60);
        gc.strokeText("I: Open Inventory ( for dropping and using Items ) ", 10, 80);
        gc.strokeText("S: Shoot ( requires ranged weapon )", 10, 100);
        gc.strokeText("P: PickUp Items", 10, 120);
        gc.strokeText("C: Close Door", 10, 140);
        gc.strokeText("TAB: For Switching weapons fast", 10, 160);
        gc.strokeText("ENTER: portaita ylös ja alas menemiseen", 10, 180);

    }
}
