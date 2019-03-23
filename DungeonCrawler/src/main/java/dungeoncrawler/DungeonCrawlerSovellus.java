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

    public static int WIDTH = 950;   // 950?
    public static int HEIGHT = 750;   // 750?
    public static int tileSize = 40;
    public static int playerSize = tileSize*2/5;
    private Map map;
    int mapSize = 100;
    private Player player;
    private GridPane screen;
    private TextArea textArea;
    private TextArea statscreen;
    private List<Enemy> enemies;

    public void init() {
        map = new Map(mapSize);

        player = new Player(5, 4, 100);

        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
                Tile tile = map.getTile(x, y);

                if (x == 1 || y == 1 || x == 17 || y == 17) {
                    tile.setWall();
                }
            }
        }
    }

    @Override
    public void start(Stage Dungeon) {

        initializeLayout();
        enemies = new ArrayList<>();
        enemies.add(new Enemy("Rat",15,15,10,10,20,player));
        
        Canvas canvas = new Canvas(mapSize * tileSize, mapSize * tileSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int cameraMaxX = WIDTH / (2 * tileSize) + 1;
        int cameraMaxY = HEIGHT / (2 * tileSize) + 1;

        drawTiles(gc);
        drawGrid(gc);
        drawPlayer(gc);
        drawEnemies(gc);
        
        screen.add(canvas, 1, 1, 1, 1); //1,1
        initializeTextArea();
        initializeStatScreen();
        Scene game = new Scene(screen, WIDTH + 300, HEIGHT + 200);

        game.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (map.getTile(player.getX(), player.getY() - 1).isWall() == false) {
                    player.moveUp();
                    if (player.getY() >= cameraMaxY - 1) {
                        canvas.setTranslateY(canvas.getTranslateY() + tileSize);
                    }
                }

            } else if (event.getCode() == KeyCode.DOWN) {
                if (map.getTile(player.getX(), player.getY() + 1).isWall() == false) {
                    player.moveDown();
                    if (player.getY() >= cameraMaxY) {
                        canvas.setTranslateY(canvas.getTranslateY() - tileSize);
                    }

                }

            } else if (event.getCode() == KeyCode.RIGHT) {
                if (map.getTile(player.getX() + 1, player.getY()).isWall() == false) {
                    player.moveRight();
                    if (player.getX() >= cameraMaxX) {
                        canvas.setTranslateX(canvas.getTranslateX() - tileSize);
                    }

                }

            } else if (event.getCode() == KeyCode.LEFT) {
                if (map.getTile(player.getX() - 1, player.getY()).isWall() == false) {
                    player.moveLeft();
                    if (player.getX() >= cameraMaxX - 1) {
                        canvas.setTranslateX(canvas.getTranslateX() + tileSize);
                    }

                }
            } else if (event.getCode() == KeyCode.SPACE) {
                
            }
            enemies.forEach(enemy -> {
                enemy.act();
                if (enemy.getIfAttacked()) {
                    textArea.appendText(enemy.getName() + " hit you for " + enemy.getDamageDealt() + " damage \n");                    
                }
            });
            drawTiles(gc);
            drawGrid(gc);
            drawPlayer(gc);
            drawEnemies(gc);
            initializeStatScreen();

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
    
    private void initializeTextArea() {
        
        //TextArea (Only display)
        textArea = new TextArea();
        textArea.setMinSize(WIDTH * 5/4, HEIGHT / 4);
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
        String hp = "hp: " + player.getCurrentHp() + " / " +player.getMaxHp();
        statscreen.appendText(hp);
        screen.add(statscreen, 0, 1, 1, 1);
        
    }

    private void drawGrid(GraphicsContext gc) {
        
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < map.getSize() + 1; i++) {
            gc.strokeLine(0, i * tileSize, map.getSize() * tileSize, i * tileSize);
        }
        for (int j = 0; j < map.getSize() + 1; j++) {
            gc.strokeLine(j * tileSize, 0, j * tileSize, map.getSize() * tileSize);
        }

    }

    private void drawPlayer(GraphicsContext gc) {
        
        gc.setFill(Color.BLACK);
        gc.fillRect(player.getX() * tileSize + (tileSize - playerSize) / 2, player.getY() * tileSize + (tileSize - playerSize) / 2, playerSize, playerSize);
        
    }

    private void drawTiles(GraphicsContext gc) {
        
        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
                if (map.getTile(x, y).isWall()) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }
    
    private void drawEnemies(GraphicsContext gc) {
        
        enemies.forEach( enemy -> {
            gc.fillOval(enemy.getX() * tileSize + (tileSize - playerSize) / 2, enemy.getY() * tileSize + (tileSize - playerSize) / 2, playerSize, playerSize);
        });
        
    }

}
