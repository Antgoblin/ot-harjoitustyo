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
        map.createRoom(20, 5, 26, 7);
        map.createRoom(26, 3, 32, 21);
        map.createDoor(26, 6);
        map.createDoor(20, 6);
    }

    @Override
    public void start(Stage Dungeon) {

        initializeLayout();
        initializeMapDrawer();
        initializeTextArea();
        initializeStatScreen();
        MovementHandler mh = new MovementHandler(map, textArea);
        map.addEnemy(new Enemy("Rat", 15, 5, 10, 5, 10, 20, 10, player));

        int cameraMaxX = WIDTH / (2 * tileSize) + 1;
        int cameraMaxY = HEIGHT / (2 * tileSize) + 1;

        mapDrawer.drawAll();

        Scene game = new Scene(screen, WIDTH + 300, HEIGHT + 200);

        game.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                mh.move(player, Direction.UP);
//                mh.moveUp(player);
                    if (player.getIfMoved() == true) {
                        if (player.getY() >= cameraMaxY - 1) {
                            canvas.setTranslateY(canvas.getTranslateY() + tileSize);
                        }
                        
                    }

            } else if (event.getCode() == KeyCode.DOWN) {
                mh.move(player, Direction.DOWN);
//                mh.moveDown(player);
                    if (player.getIfMoved() == true) {
                        if (player.getY() >= cameraMaxY) {
                            canvas.setTranslateY(canvas.getTranslateY() - tileSize);
                        }
                        
                    }

            } else if (event.getCode() == KeyCode.RIGHT) {
                mh.move(player, Direction.RIGHT);
//                mh.moveRight(player);
                    if (player.getIfMoved() == true) {
                        if (player.getX() >= cameraMaxX) {
                            canvas.setTranslateX(canvas.getTranslateX() - tileSize);
                        }                        
                    }


            } else if (event.getCode() == KeyCode.LEFT) {
                mh.move(player, Direction.LEFT);
//                mh.moveLeft(player);
                    if (player.getIfMoved() == true) {
                        if (player.getX() >= cameraMaxX - 1) {
                            canvas.setTranslateX(canvas.getTranslateX() + tileSize);
                        }                        
                    } 
                
            } else if (event.getCode() == KeyCode.SPACE) {

            }
            endTurn();
            updateStatScreen();

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
                "Exp( " + player.getExp() + " )    Gold( "+player.getGold()+" )\n" +
                "Hp: " + player.getCurrentHp() + " / " + player.getMaxHp() + "\n" 
        );
    }

    private void endTurn() {
        
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
                textArea.appendText("You killed " + dead.getName() +" \n");
                player.gainExp(dead.getExp());
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
        
        player.noAttack();
        player.doNotMove();
        
        //draws what happened
        mapDrawer.drawAll();

    }
}
