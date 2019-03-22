/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import dungeoncrawler.Tile;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 *
 * @author jy
 */
public class DungeonCrawlerSovellus extends Application {
    
    public static void main(String[] args) {
        launch();
    }
    
    public static int WIDTH = 950;
    public static int HEIGHT = 750;
    public static int tileSize = 50;

    @Override
    public void start(Stage Dungeon) throws Exception {
        
        //BorderPane layout = new BorderPane();
        //layout.setPrefSize(WIDTH*50, HEIGHT*50);
        
        Pane screen = new Pane();
        screen.setPrefSize(WIDTH, HEIGHT);
        
        //Pane side = new Pane();
        //side.setPrefSize(WIDTH*10, HEIGHT*55);
        
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Pane camera = new Pane();
        camera.setPrefSize(WIDTH, HEIGHT);
        double cameraMaxX = 475.0;
        double cameraMaxY = 375.0;
        
        int mapSize = 100;
        Map map = new Map(mapSize);
        Tile[][] tiles = map.getTiles();
        
        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
                Tile tile = tiles[x][y];
                
    //            tile.setTranslateX(50 * (i % map.getSize()));
    //            tile.setTranslateY(50 * (i / map.getSize()));
                if(tile.getTranslateX() == 100 || tile.getTranslateY() == 100) {
                    tile.setWall();
                }
                camera.getChildren().add(tile);
                
            }
        }
        
//        gc.setStroke(Color.BLACK);
        for (int i = 0; i < map.getSize() +1 ; i++) {            
            gc.strokeLine(0, i * tileSize, map.getSize() * tileSize, i * tileSize);
        }
        for (int j = 0; j < map.getSize() +1 ; j++) {            
            gc.strokeLine(j * tileSize, 0, j * tileSize, map.getSize() * tileSize);
        }
        
        
        Character player = new Character(new Polygon(-10, -10, 10, -10, 10, 10, -10, 10),475,375,1);
        camera.getChildren().add(player.getShape());
        
        screen.getChildren().add(canvas);
        Scene game = new Scene(screen);
        
        game.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP) {
                player.moveUp();
                if (player.getShape().getTranslateY() >= cameraMaxY) {
                    camera.setTranslateY(camera.getTranslateY() + 50);
                }
                
            } else if(event.getCode() == KeyCode.DOWN) {
                player.moveDown();
                if (player.getShape().getTranslateY() > cameraMaxY) {
                    camera.setTranslateY(camera.getTranslateY() - 50);
                }
                
            } else if(event.getCode() == KeyCode.RIGHT) {
                player.moveRight();
                if (player.getX() > cameraMaxX) {
                    camera.setTranslateX(camera.getTranslateX() - 50);
                }
                
            } else if(event.getCode() == KeyCode.LEFT) {
                player.moveLeft();
                if (player.getX() >= cameraMaxX) {
                    camera.setTranslateX(camera.getTranslateX() + 50);
                }
            }    
        });
        
        Dungeon.setTitle("DungeonCrawler");
        Dungeon.setScene(game);
        Dungeon.setResizable(true);
        Dungeon.show();
        
    }
    
}
