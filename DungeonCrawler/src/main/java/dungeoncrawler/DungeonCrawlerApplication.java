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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Screen;

/**
 *
 * @author jy
 */
public class DungeonCrawlerApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    //Jotkin muuttujat täällä että niitä helppo muuttaa.
    public static int tileSize = 40; //40
    public static int WIDTH = tileSize * 23;   // 950?
    public static int HEIGHT = tileSize * 19;   // 750?
//    public static int playerSize = tileSize*2/5;
    private Map map;
    int mapSize = 80;
    private Player player;
    private GridPane grid;
    private Canvas canvas;
    private MapDrawer mapDrawer;
    private TextArea textArea;
    private TextArea statscreen;
    private MovementHandler mh;

    public void init() {
//
        player = new Player(11, 11, Class.Warrior);
        map = new Map(mapSize, tileSize, player);

        map.createRoom(10, 10, 14, 14);
        map.getTile(12, 12).setType(Tiletype.StairsDown);
    }

    @Override
    public void start(Stage stage) {

        //Character creation scene
        Pane pane = new Pane();
        Label choose = new Label("Choose Class");
        choose.setTranslateX(500);
        choose.setTranslateY(300);
        choose.setScaleX(3);
        choose.setScaleY(3);
        Label warrior = new Label("W = Warrior");
        warrior.setTranslateX(500);
        warrior.setTranslateY(400);
        warrior.setScaleX(1.5);
        warrior.setScaleY(1.5);
        Label ranger = new Label("R = Ranger");
        ranger.setTranslateX(500);
        ranger.setTranslateY(420);
        ranger.setScaleX(1.5);
        ranger.setScaleY(1.5);
        Label mage = new Label("M = mage");
        mage.setTranslateX(500);
        mage.setTranslateY(440);
        mage.setScaleX(1.5);
        mage.setScaleY(1.5);
        pane.getChildren().addAll(choose, warrior, ranger, mage);
        Scene charactercreation = new Scene(pane, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 - 10);

        //Game Scene
        initializeLayout();
        initializeMapDrawer();
        initializeTextArea();
        initializeStatScreen();
        mh = new MovementHandler(map, textArea);
        Scene game = new Scene(grid, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 - 10);
//        map.spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));
//        map.spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));
//        map.spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));
//        map.spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));
//        map.spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));
//        map.spawnEnemyRandom(EnemyList.RAT.Randomize().spawn(player));

        charactercreation.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player = new Player(11, 11, Class.Warrior);
                    map.setplayer(player);
                    updateStatScreen();
                    stage.setScene(game);

                    break;
                case R:
                    player = new Player(11, 11, Class.Ranger);
                    map.setplayer(player);
                    updateStatScreen();
                    stage.setScene(game);
                    break;
                case M:
                    player = new Player(11, 11, Class.Mage);
                    map.setplayer(player);
                    updateStatScreen();
                    stage.setScene(game);
                    break;
                default:
                    break;

            }
        });

        mapDrawer.drawAll();

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
                    player.setActed(true);
                    break;

                case C:
                    textArea.appendText("Choose Direction \n");
                    mh.setState(1);
                    break;

                case S:
                    if (player.getRange() > 1) {
                        textArea.appendText("Choose Direction \n");
                        mh.setState(2);
                    } else {
                        textArea.appendText("You dont have anything to shoot with \n");
                    }
                    break;

                case Q:
                    if (map.getEnemies().isEmpty()) {
                        map.spawnEnemy(EnemyType.RAT.spawn(3, 15, player));
                    }
                    player.setActed(true);
                    break;

                case TAB:
                    player.switchWeapons();
                    break;

                case ENTER:
                    if (map.getTile(player.x(), player.y()).getType() == Tiletype.StairsDown) {
                        map.goDown();
                        mapDrawer.drawAll();
                    } else if (map.getTile(player.x(), player.y()).getType() == Tiletype.StairsUp) {
                        map.goUp();
                        mapDrawer.drawAll();
                    }
                    break;

                case I:
                    openInventory(stage);
                    break;
                default:
                    break;

            }
            updateCamera();
            updateStatScreen();
            if (player.hasActed()) {
                endTurn();
            }
        });

        stage.setTitle("DungeonCrawler");
        stage.setScene(charactercreation);
        stage.setResizable(false);
        stage.show();

    }

    private void initializeLayout() {

        //Layout of the game
        grid = new GridPane();
        grid.setPrefSize(WIDTH, HEIGHT);
        grid.setHgap(0);
        grid.setVgap(0);

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
        int x = (player.x() - 11) * tileSize;
        if (x < 0) {
            x = 0;
        } else if (x > map.getSize() * tileSize - WIDTH) {
            x = map.getSize() * tileSize - WIDTH;
        }
        int y = (player.y() - 9) * tileSize;
        if (y < 0) {
            y = 0;
        } else if (y > map.getSize() * tileSize - HEIGHT) {
            y = map.getSize() * tileSize - HEIGHT;
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
                + "Mana: " + player.currentMana() + " / " + player.maxMana() + "\n \n"
                + "Weapon1 : " + player.getWeapon().name() + "\n"
        );

        if (player.getWeapon2() != null) {
            statscreen.appendText("Weapon2 : " + player.getWeapon2().name());
        } else {
            statscreen.appendText("Weapon2 : -");
        }
        statscreen.appendText(" \n \n \n \n Level: " + map.level());

    }

    private void openInventory(Stage stage) {
        Stage inventoryscreen = new Stage();
        inventoryscreen.setTitle("Inventory");
        inventoryscreen.setHeight(400);
        inventoryscreen.setWidth(200);

        VBox inventory = new VBox();
        List<Item> items = player.inventory();

        items.forEach(i -> {
            String item = i.name();
            Label label = new Label(item);
            inventory.getChildren().add(label);
            System.out.println(item);
        });

        Scene scene = new Scene(inventory);
        inventoryscreen.setScene(scene);
        inventoryscreen.setResizable(false);
        inventoryscreen.setAlwaysOnTop(true);
        inventoryscreen.showAndWait();

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
                map.getTile(dead.x(), dead.y()).setCharacter(null);
                map.removeEnemy(dead);
            });

        }

        //Enemies turn
        map.getEnemies().forEach(enemy -> {
            enemy.hasNotAttacked();
            mh.move(enemy);
            if (enemy.hasAttacked()) {
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
                map.getTile(dead.x(), dead.y()).setCharacter(null);
                map.removeEnemy(dead);
            });

        }

        updateStatScreen();
        player.hasNotAttacked();
        player.setActed(false);

        //draws what happened
        mapDrawer.drawAll();

    }
}
