/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import dungeoncrawler.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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

    //Jotkin muuttujat täällä että niitä helppo muuttaa.
    public static int WIDTH = 950;   // 950?
    public static int HEIGHT = 750;   // 750?
    public static int tileSize = 40;
//    public static int playerSize = tileSize*2/5;
    private Map map;
    int mapSize = 100;
    private Player player;
    private GridPane screen;
    private Canvas canvas;
    private MapDrawer mapDrawer;
    private TextArea textArea;
    private TextArea statscreen;

    public void init() {

        player = new Player(5, 4, 100);
        map = new Map(mapSize, tileSize, player);
        
        map.createRoom(1, 1, 20, 16);

//        for (int y = 0; y < map.getSize(); y++) {
//            for (int x = 0; x < map.getSize(); x++) {
//                Tile tile = map.getTile(x, y);
//
//                if (x == 1 || y == 1 || x == 17 || y == 17) {
//                    tile.setWall();
//                }
//            }
//        }
    }

    @Override
    public void start(Stage Dungeon) {

        initializeLayout();
        initializeMapDrawer();
        initializeTextArea();
        initializeStatScreen();

        map.addEnemy(new Enemy("Rat", 15, 15, 10, 5, 10, 20, player));

        int cameraMaxX = WIDTH / (2 * tileSize) + 1;
        int cameraMaxY = HEIGHT / (2 * tileSize) + 1;

        mapDrawer.drawAll();

        Scene game = new Scene(screen, WIDTH + 300, HEIGHT + 200);

        game.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (map.getTile(player.getX(), player.getY() - 1).getType() != Tiletype.Wall) {
                    map.getEnemies().forEach(enemy -> {
                        if (enemy.getX() == player.getX() && enemy.getY() == player.getY() - 1) {
                            player.attack(enemy);
                            textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");

                        }
                    });
                    if (player.getIfAttacked() == false) {
                        player.moveUp();
                        if (player.getY() >= cameraMaxY - 1) {
                            canvas.setTranslateY(canvas.getTranslateY() + tileSize);
                        }
                        
                    }
                }

            } else if (event.getCode() == KeyCode.DOWN) {
                if (map.getTile(player.getX(), player.getY() + 1).getType() != Tiletype.Wall) {
                    map.getEnemies().forEach(enemy -> {
                        if (enemy.getX() == player.getX() && enemy.getY() == player.getY() + 1) {
                            player.attack(enemy);
                            textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");

                        }
                    });
                    if (player.getIfAttacked() == false) {
                        player.moveDown();
                        if (player.getY() >= cameraMaxY) {
                            canvas.setTranslateY(canvas.getTranslateY() - tileSize);
                        }
                        
                    }

                }

            } else if (event.getCode() == KeyCode.RIGHT) {
                if (map.getTile(player.getX() + 1, player.getY()).getType() != Tiletype.Wall) {
                    map.getEnemies().forEach(enemy -> {
                        if (enemy.getX() == player.getX() + 1 && enemy.getY() == player.getY()) {
                            player.attack(enemy);
                            textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");

                        }
                    });
                    if (player.getIfAttacked() == false) {
                        player.moveRight();
                        if (player.getX() >= cameraMaxX) {
                            canvas.setTranslateX(canvas.getTranslateX() - tileSize);
                        }                        
                    }

                }

            } else if (event.getCode() == KeyCode.LEFT) {
                if (map.getTile(player.getX() - 1, player.getY()).getType() != Tiletype.Wall) {
                    map.getEnemies().forEach(enemy -> {
                        if (enemy.getX() == player.getX() - 1 && enemy.getY() == player.getY()) {
                            player.attack(enemy);
                            textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");

                        }
                    });
                    if (player.getIfAttacked() == false) {
                        player.moveLeft();
                        if (player.getX() >= cameraMaxX - 1) {
                            canvas.setTranslateX(canvas.getTranslateX() + tileSize);
                        }                        
                    } 
                }
                
            } else if (event.getCode() == KeyCode.SPACE) {

            }
            endTurn();
            updateStatScreen();
//            initializeStatScreen(); 

        });

        Dungeon.setTitle("DungeonCrawler");
        Dungeon.setScene(game);
        Dungeon.setResizable(false);
        Dungeon.show();

    }

    private void initializeLayout() {

        //Layout of the game
        screen = new GridPane();
        screen.setPrefSize(WIDTH, HEIGHT);
        screen.setHgap(0);
        screen.setVgap(0);
//        screen.setPadding(new Insets(5, 5, 5, 5));

    }

    private void initializeMapDrawer() {

        canvas = new Canvas(mapSize * tileSize, mapSize * tileSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        mapDrawer = new MapDrawer(map, gc);
        screen.add(canvas, 1, 1, 1, 1); //1,1

    }

    private void initializeTextArea() {

        //TextArea (Only display)
        textArea = new TextArea();
        textArea.setMinSize(WIDTH * 5 / 4, HEIGHT / 4);
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setMouseTransparent(true);
        screen.add(textArea, 0, 0, 2, 1); //0,0

    }

    private void initializeStatScreen() {
        
        //Area for characters stats & such
        statscreen = new TextArea();
        statscreen.setMinSize(WIDTH / 4, HEIGHT);
        statscreen.setEditable(false);
        statscreen.setFocusTraversable(false);
        statscreen.setMouseTransparent(true);
        screen.add(statscreen, 0, 1, 1, 1);
        updateStatScreen();

    }
    
    private void updateStatScreen() {
        statscreen.setText(player.getPlayerClass() +"   Lvl:"+player.getLvl() + "\n" +
                "Hp: " + player.getCurrentHp() + " / " + player.getMaxHp() + "\n" 
        );
    }

    private void endTurn() {
        
        player.noAttack();
        
        //checks if there are dead enemies
        List<Enemy> deadEnemies = new ArrayList<>();
        map.getEnemies().forEach(enemy -> {
            if (enemy.checkIfDead()) {
                deadEnemies.add(enemy);
            }
        });
        
        //deletes dead enemies
        if (deadEnemies.isEmpty() == false) {
            deadEnemies.forEach(dead -> {
                textArea.appendText("You killed " + dead.getName() +"\n");
                map.removeEnemy(dead);
            });
            
        }
        
        //Enemies turn
        map.getEnemies().forEach(enemy -> {
            enemy.noAttack();
            enemy.act();
            if (enemy.getIfAttacked()) {
                textArea.appendText(enemy.getName() + " hit you for " + enemy.getLastDamage() + " damage \n");
            }
        });
        if (player.checkIfDead()) {
            textArea.appendText("You died \n");
        }
        
        //checks if there are dead enemies again (if enemies kill each other)
        deadEnemies.clear();
        map.getEnemies().forEach(enemy -> {
            if (enemy.checkIfDead()) {
                deadEnemies.add(enemy);
            }
        });
        
        //deletes dead enemies
        if (deadEnemies.isEmpty() == false) {
            deadEnemies.forEach(dead -> {
                textArea.appendText(dead.getName() +" died \n");
                map.removeEnemy(dead);
            });
            
        }
        
        //draws what happened
        mapDrawer.drawAll();

    }
}
