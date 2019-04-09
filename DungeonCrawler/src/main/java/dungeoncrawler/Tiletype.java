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
    Void(Color.GRAY), 
    Floor(Color.WHITE),
    Wall(Color.BLACK),
    Door(Color.SIENNA),
    OpenDoor(Color.BURLYWOOD),
    StairsUp(Color.AQUAMARINE),
    StairsDown(Color.VIOLET),
    TempFloor(Color.RED),
    TempWall(Color.BLUE),
    OutOfMap(Color.BLACK),
    Corner(Color.BLACK),
    CheckedFloor(Color.ORANGE),
    CheckedDoor(Color.SIENNA),
    CheckedTempFloor(Color.RED);
    
    private Color color;
    
    private Tiletype(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }
            
    
}
