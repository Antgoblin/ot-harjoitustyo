/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
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
    
    public Map(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                tiles[x][y] = new Tile();
            }
//            tiles.add(new Tile(50 * (i % size), 50 * (i / size)));
            
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
    
//    public Boolean getSolid(double x, double y) {
//        List<Tile> memes = new ArrayList<>(); 
//        tiles.forEach(tile -> {
//            if(tile.getTranslateX() == x && tile.getTranslateY() == y) {
//                memes.add(tile);
//            }
//            
//        });
//        System.out.println(memes.get(0).getSolid());
//        return memes.get(0).getSolid();
//    }
//    public class Tile extends StackPane {
//        public Tile() {
//            Rectangle border = new Rectangle(50, 50);
//            border.setFill(null);
//            border.setStroke(Color.BLACK);
//            
//            setAlignment(Pos.CENTER);
//            getChildren().addAll(border);
//        }
//    }
}


