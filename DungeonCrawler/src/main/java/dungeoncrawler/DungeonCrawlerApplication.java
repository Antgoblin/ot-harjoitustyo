/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.WeaponType;
import dungeoncrawler.MovementHandler.State;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

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

    public void save() throws IOException {
        File file = new File("save.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(player.getCurrentHp() + "\r\n");
            fr.write(player.getMaxHp() + "\r\n");
            fr.write(player.currentMana() + "\r\n");
            fr.write(player.maxMana() + "\r\n");
            fr.write(player.getLvl() + "\r\n");
            fr.write(player.getExp() + "\r\n");
            fr.write(player.getGold() + "\r\n");
            fr.write(player.x() + "\r\n");
            fr.write(player.y() + "\r\n");
            if (player.getWeapon() != null) {
                fr.write(player.getWeapon().getWeaponType().getName() + "\r\n");
            }
            if (player.getWeapon2() != null) {
                fr.write(player.getWeapon2().getWeaponType().getName() + "\r\n");
            }
            fr.write("-Items- \r\n");
            fr.write(player.inventory().size() + "\r\n");
            for (int i = 0; i < player.inventory().size(); i++) {
                String name = player.inventory().get(i).getName();
                fr.write(name + "\r\n");
            }
            fr.write("-Enemies- \r\n");
            for (int i = 0; i < map.getEnemies().size(); i++) {
                Enemy enemy = map.getEnemies().get(i);
                fr.write(enemy.getName() + "\r\n");
                fr.write(enemy.getCurrentHp() + "\r\n");
                fr.write(enemy.x() + "\r\n");
                fr.write(enemy.y() + "\r\n");
            }
            fr.write("-Map- \r\n");
            for (int y = 0; y < map.getSize(); y++) {
                for (int x = 0; x < map.getSize(); x++) {
                    Tile tile = map.getTile(x, y);
                    fr.write(tile.getType().getName() + "\r\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream("save.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        List<String> lines = new ArrayList<>();

        while (line != null) {
            lines.add(line);
            line = buf.readLine();
        }

        lines.forEach(l -> {
            System.out.println(l);
        });
        player.setHp(Integer.parseInt(lines.get(0)));
        player.setMaxHp(Integer.parseInt(lines.get(1)));
        player.setCurrentMana(Integer.parseInt(lines.get(2)));
        player.setMaxMana(Integer.parseInt(lines.get(3)));
        player.setLvl(Integer.parseInt(lines.get(4)));
        player.setExp(Integer.parseInt(lines.get(5)));
        player.setGold(Integer.parseInt(lines.get(6)));
        player.setX(Integer.parseInt(lines.get(7)));
        player.setY(Integer.parseInt(lines.get(8)));
        int i = 0;
        if (!lines.get(9).equals("-Items-")) {
            player.setWeapon(new Weapon(WeaponType.DAGGER.getWeapon(lines.get(9))));
            if (!lines.get(10).equals("-Items-")) {
                player.setWeapon2(new Weapon(WeaponType.DAGGER.getWeapon(lines.get(10))));
                i = 11;
            } else {
                i = 10;
            }
        } else {
            i = 9;
        }
        for (int x = i; x < Integer.parseInt(lines.get(i + 1)); x++) {
            
        }
        mapDrawer.drawAll();
        updateCamera();
    }

    public void l() {
        Path path = Paths.get("save.txt");

    }

    public void init() {
//
        player = new Player(11, 11, PlayerClass.Warrior);
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

        charactercreation.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player = new Player(11, 11, PlayerClass.Warrior);
                    map.setplayer(player);
                    updateStatScreen();
                    stage.setScene(game);

                    break;
                case R:
                    player = new Player(11, 11, PlayerClass.Ranger);
                    map.setplayer(player);
                    updateStatScreen();
                    stage.setScene(game);
                    break;
                case M:
                    player = new Player(11, 11, PlayerClass.Mage);
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

                case D:
                    textArea.appendText("Choose Direction \n");
                    mh.setState(State.OpeningDoor);
                    break;

                case P:
                    mh.pickUp(player);
                    mapDrawer.drawAll();
                    break;

                case S:
                    if (player.getRange() > 1) {
                        textArea.appendText("Choose Direction \n");
                        mh.setState(State.Shooting);
                    } else {
                        textArea.appendText("You dont have anything to shoot with \n");
                    }
                    break;

                case TAB:
                    player.switchWeapons();
                    break;

                case ENTER:
                    if (mh.getState() == State.Inventory) {
                        if (mh.getChooser().getX() == 1) {
                            mh.dropItem(player, mh.getChooser().getY());
                        } else if (mh.getChooser().getX() == 2) {
                            mh.Action(player);
                        }
                    } else if (map.getTile(player.x(), player.y()).getType() == Tiletype.StairsDown) {
                        map.goDown();
                        mapDrawer.drawAll();
                        updateCamera();
                    } else if (map.getTile(player.x(), player.y()).getType() == Tiletype.StairsUp) {
                        map.goUp();
                        mapDrawer.drawAll();
                        updateCamera();
                    } else if (mh.getState() == State.Spells && mh.getChooser().getX() == 1) {
                        mh.castSpell(mh.getChooser().getY());
                        player.setActed(true);
                        mh.setState(State.Normal);
                    }
                    break;

                case I:
                    mh.setState(State.Inventory);
                    canvas.setTranslateX(0);
                    canvas.setTranslateY(0);
                    mapDrawer.drawInventory(mh.getChooser());
                    break;

                case C:
                    mh.setState(State.Spells);
                    canvas.setTranslateX(0);
                    canvas.setTranslateY(0);
                    mapDrawer.drawSpells(mh.getChooser());
                    break;

                case H:
                    mh.setState(State.Help);
                    canvas.setTranslateX(0);
                    canvas.setTranslateY(0);
                    mapDrawer.drawHelpScreen();
                    break;

                case ESCAPE:
                    mh.getChooser().setX(0);
                    mh.getChooser().setY(0);
                    mh.setState(State.Normal);
                    updateCamera();
                    mapDrawer.drawAll();
                    break;

                case O:
                    try {
                        save();
                    } catch (IOException ex) {
                        Logger.getLogger(DungeonCrawlerApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case L:
                    try {
                        load();
                    } catch (IOException ex) {
                        System.out.println("ITS BROKEN");
                    }
                default:
                    break;

            }
            updateStatScreen();
            if (player.hasActed()) {
                updateCamera();
                if (mh.getState() == State.Inventory) {
                    mh.setState(State.Normal);
                }
                endTurn();
            } else if (mh.getState() == State.Inventory) {
                mapDrawer.drawInventory(mh.getChooser());
            } else if (mh.getState() == State.Spells) {
                mapDrawer.drawSpells(mh.getChooser());
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
        );

        if (player.getWeapon() != null) {
            statscreen.appendText("Weapon : " + player.getWeapon().getName() + "\n");
        } else {
            statscreen.appendText("Weapon : - \n");
        }

        if (player.getWeapon2() != null) {
            statscreen.appendText("Weapon2 : " + player.getWeapon2().getName());
        } else {
            statscreen.appendText("Weapon2 : -");
        }
        statscreen.appendText(" \n \n \n \n Level: " + map.level());
        statscreen.appendText(" \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n H for Help");

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
            mh.act(enemy);
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
        player.checkIfRegenerates();
        player.checkIfLevelUp();
        updateStatScreen();
        player.hasNotAttacked();
        player.setActed(false);
        //draws what happened
        mapDrawer.drawAll();

    }
}
