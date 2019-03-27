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
import javafx.stage.Screen;

/**
 *
 * @author jy
 */
public class DungeonCrawlerSovellus extends Application {

    public static void main(String[] args) {
        launch();
    }

    //Jotkin muuttujat täällä että niitä helppo muuttaa.
    public static int tileSize = 40;
    public static int WIDTH = tileSize * 23;   // 950?
    public static int HEIGHT = tileSize * 19;   // 750?
//    public static int playerSize = tileSize*2/5;
    private Map map;
    int mapSize = 100;
    private Player player;
    private GridPane grid;
    private Canvas canvas;
    private MapDrawer mapDrawer;
    private TextArea textArea;
    private TextArea statscreen;
    private MovementHandler mh;

    public void init() {

        player = new Player(5, 5, 100);
        map = new Map(mapSize, tileSize, player);

        map.createRoom(1, 1, 20, 16);
        map.createRoom(20, 5, 26, 7);
        map.createRoom(26, 3, 32, 21);
        map.createDoor(26, 6);
        map.createDoor(20, 6);

    }

    @Override
    public void start(Stage stage) {

        initializeLayout();
        initializeMapDrawer();
        initializeTextArea();
        initializeStatScreen();
        mh = new MovementHandler(map, textArea);
        map.addEnemy(EnemyList.RAT.spawn(5, 15, player));

        mapDrawer.drawAll();
        
        Scene game = new Scene(grid, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 -10);
        
        game.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    mh.handle(player, Direction.UP);
                    break;
                    
                case DOWN:
                    mh.handle(player, Direction.DOWN);
                    break;
                    
                case RIGHT:
                    mh.handle(player, Direction.RIGHT);
                    break;
                    
                case LEFT:
                    mh.handle(player, Direction.LEFT);
                    break;
                
                case SPACE:
                    player.acted(true);
                    break;
                    
                case C:
                    textArea.appendText("Choose Direction \n");
                    mh.setState(1);
                    break;
                
                case S:
                    if(player.getRange() > 1) {
                        textArea.appendText("Choose Direction \n");
                        mh.setState(2);
                    } else {
                        textArea.appendText("You dont have anything to shoot with \n");
                    }
                    break;
                    
                default:
                    break;

            }
            updateCamera();
            if (player.ifActed()) {
                endTurn();
                updateStatScreen();               
            }
        });

        stage.setTitle("DungeonCrawler");
        stage.setScene(game);
        stage.setResizable(false);
        stage.show();

    }

    private void initializeLayout() {

        //Layout of the game
        grid = new GridPane();
        grid.setPrefSize(WIDTH, HEIGHT);
        grid.setHgap(0);
        grid.setVgap(0);
//        screen.setPadding(new Insets(5, 5, 5, 5));

    }

    private void initializeMapDrawer() {

        canvas = new Canvas(mapSize * tileSize, mapSize * tileSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        mapDrawer = new MapDrawer(map, gc);
        updateCamera();
        grid.add(canvas, 1, 1, 1, 1); //1,1

    }

    private void initializeTextArea() {

        //TextArea (Only display)
        textArea = new TextArea();
        textArea.setMinSize(WIDTH + tileSize * 5, tileSize * 5);
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setMouseTransparent(true);
        grid.add(textArea, 0, 0, 2, 1); //0,0

    }
    
    public void updateCamera() {
        int x = player.X()*tileSize - 11*tileSize;
        if(x < 0) {
            x = 0;
        }
        int y = player.Y()*tileSize - 9*tileSize;
        if(y < 0) {
            y = 0;
        }
        
        canvas.setTranslateX(-x);
        canvas.setTranslateY(-y);
    }

    private void initializeStatScreen() {

        //Area for characters stats & such
        statscreen = new TextArea();
        statscreen.setMinSize(tileSize * 5, HEIGHT + tileSize * 5);
        statscreen.setEditable(false);
        statscreen.setFocusTraversable(false);
        statscreen.setMouseTransparent(true);
        grid.add(statscreen, 0, 1, 1, 1);
        updateStatScreen();

    }

    private void updateStatScreen() {
        statscreen.setText(player.getPlayerClass() + "   Lvl:" + player.getLvl() + "\n"
                + "Exp( " + player.getExp() + " )    Gold( " + player.getGold() + " )\n"
                + "Hp: " + player.getCurrentHp() + " / " + player.getMaxHp() + "\n"
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
                textArea.appendText("You killed " + dead.getName() + " \n");
                player.gainExp(dead.getExp());
                map.removeEnemy(dead);
            });

        }

        //Enemies turn
        map.getEnemies().forEach(enemy -> {
            enemy.noAttack();
            mh.move(enemy);
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
                textArea.appendText(dead.getName() + " died \n");
                map.removeEnemy(dead);
            });

        }

        player.noAttack();
        player.acted(false);

        //draws what happened
        mapDrawer.drawAll();

    }
}
