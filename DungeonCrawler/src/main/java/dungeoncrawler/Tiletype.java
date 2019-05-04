/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

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

}
