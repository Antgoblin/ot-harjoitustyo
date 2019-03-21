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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author jy
 */
public class DungeonCrawlerSovellus extends Application {
    
    public static void main(String[] args) {
        launch();
    }
    
    public static int WIDTH = 1000;
    public static int HEIGHT = 750;

    @Override
    public void start(Stage Dungeon) throws Exception {
        
        //BorderPane layout = new BorderPane();
        //layout.setPrefSize(WIDTH*50, HEIGHT*50);
        
        //Pane text = new Pane();
        //text.setPrefSize(WIDTH*50, HEIGHT*5);
        
        //Pane side = new Pane();
        //side.setPrefSize(WIDTH*10, HEIGHT*55);

        Pane ruutu = new Pane();
        ruutu.setPrefSize(WIDTH, HEIGHT);
        
        
        int mapSize = 100;
        Map map = new Map(mapSize);
        List<Tile> tiles = map.getTiles();
        
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
//            tile.setTranslateX(50 * (i % map.getSize()));
//            tile.setTranslateY(50 * (i / map.getSize()));
            System.out.println(tile.getTranslateX() + "&&" + tile.getTranslateY());
            if(tile.getTranslateX() == 100) {
                tile.wall();
            }
            tile.render();
            ruutu.getChildren().add(tile);
        }
        
        Character player = new Character(new Polygon(-10, -10, 10, -10, 10, 10, -10, 10),475,325,1);
        ruutu.getChildren().add(player.getShape());
        
        Scene game = new Scene(ruutu);
        
        game.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP) {
                player.moveUp();
                ruutu.setTranslateY(ruutu.getTranslateY() + 50);
            } else if(event.getCode() == KeyCode.DOWN) {
                player.moveDown();
                ruutu.setTranslateY(ruutu.getTranslateY() - 50);
            } else if(event.getCode() == KeyCode.RIGHT) {
                player.moveRight();
                ruutu.setTranslateX(ruutu.getTranslateX() - 50);
            } else if(event.getCode() == KeyCode.LEFT) {
                player.moveLeft();
                ruutu.setTranslateX(ruutu.getTranslateX() + 50);
                
            }    
        });
        
        Dungeon.setTitle("DungeonCrawler");
        Dungeon.setScene(game);
        Dungeon.setResizable(true);
        Dungeon.show();
        
    }
    
}
