/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.logic;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author jy
 */
public enum Tiletype {
    Void(Color.GRAY, "Void"),
    Floor(Color.WHITE, "Floor"),
    Wall(Color.BLACK, "Wall"),
    Door(Color.SIENNA, "Door"),
    OpenDoor(Color.BURLYWOOD, "OpenDoor"),
    StairsUp(Color.AQUAMARINE, "StairsUp"),
    StairsDown(Color.VIOLET, "StairsDown"),
    TempFloor(Color.RED, "Temporary Floor"),
    TempWall(Color.BLUE, "Temporary Wall"),
    OutOfMap(Color.BLACK, "Out of The Map"),
    Corner(Color.BLACK, "Corner"),
    CheckedFloor(Color.ORANGE, "Checked Floor"),
    CheckedDoor(Color.SIENNA, "Checked Door"),
    CheckedTempFloor(Color.RED, "Checked Temporary Floor");

    private Color color;
    private String name;

    private Tiletype(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Metodi luo listan jossa on kaikki ruututyypit joita esiintyy pelissä.
     * Listalla ei ole tyyppejä joita hyödynnetään ainoastaa luolaston
     * rakennuksessa
     *
     * @return listan kaikista ruututyypristä
     */
    public List<Tiletype> getAll() {
        List<Tiletype> all = new ArrayList<>();
        all.add(Floor);
        all.add(Wall);
        all.add(Door);
        all.add(Void);
        all.add(OpenDoor);
        all.add(StairsUp);
        all.add(StairsDown);
        return all;
    }

    /**
     * Metodi etsii ruudun sille annetun nime avulla
     *
     * @param name minkä nimistä etsitään
     * @return ruututyypin jolla etsitty nimi
     */
    public Tiletype getTile(String name) {
        List<Tiletype> all = getAll();
        Tiletype type = all.get(0);
        for (int i = 0; i < all.size(); i++) {
            type = all.get(i);
            if (type.name.equals(name)) {
                break;
            }
        }
        return type;
    }

}
