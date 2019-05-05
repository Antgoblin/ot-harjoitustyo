package dungeoncrawler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dungeoncrawler.logic.Direction;
import dungeoncrawler.logic.Enemy;
import dungeoncrawler.logic.EnemyType;
import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.WeaponType;
import dungeoncrawler.logic.Map;
import dungeoncrawler.logic.MovementHandler;
import dungeoncrawler.logic.MovementHandler.State;
import dungeoncrawler.logic.Player;
import dungeoncrawler.logic.PlayerClass;
import dungeoncrawler.logic.Tile;
import dungeoncrawler.logic.Tiletype;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
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
    private Pane classSelection;
    private Pane startGame;
    private Pane gameover;

    /**
     * Tallentaa pelaajan ja kartan tiedot tiedostoon save.txt jos tiedostoa ei
     * ole luodaan se ensiksi
     *
     * @throws IOException heittää virheen
     */
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
            } else {
                fr.write("Empty \r\n");
            }
            if (player.getWeapon2() != null) {
                fr.write(player.getWeapon2().getWeaponType().getName() + "\r\n");
            } else {
                fr.write("Empty \r\n");
            }
            fr.write("-Items- \r\n");
            fr.write(player.inventory().size() + "\r\n");
            for (int i = 0; i < player.inventory().size(); i++) {
                String name = player.inventory().get(i).getName();
                fr.write(name + "\r\n");
            }
            fr.write("-Enemies- \r\n");
            fr.write(map.getEnemies().size() + "\r\n");
            for (int i = 0; i < map.getEnemies().size(); i++) {
                Enemy enemy = map.getEnemies().get(i);
                fr.write(enemy.getName() + "\r\n");
                fr.write(enemy.getCurrentHp() + "\r\n");
                fr.write(enemy.x() + "\r\n");
                fr.write(enemy.y() + "\r\n");
            }
            fr.write("-Map- \r\n");
            fr.write(map.getSize() + "\r\n");
            for (int y = 0; y < map.getSize(); y++) {
                for (int x = 0; x < map.getSize(); x++) {
                    Tile tile = map.getTile(x, y);
                    fr.write(tile.getType().getName() + "\r\n");
                }
            }
            fr.write("-Items on ground- \r\n");
            fr.write(map.getItemCount() + "\r\n");
            for (int y = 0; y < map.getSize(); y++) {
                for (int x = 0; x < map.getSize(); x++) {
                    Tile tile = map.getTile(x, y);
                    if (tile.containsItem()) {
                        for (int i = 0; i < tile.getItems().size(); i++) {
                            fr.write(tile.x() + "\r\n");
                            fr.write(tile.y() + "\r\n");
                            fr.write(tile.getItems().get(i).getName() + "\r\n");
                        }
                    }
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

    /**
     * Lukee tiedoston save.txt ja muuttaa kartan ja pelaajan arvot sen mukaan
     *
     * @throws FileNotFoundException jos tiedostoa ei ole
     * @throws IOException
     */
    public void load() throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream("save.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        List<String> lines = new ArrayList<>();

        while (line != null) {
            lines.add(line);
            line = buf.readLine();
        }
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
        if (WeaponType.DAGGER.getWeapon(lines.get(9)) != null) {
            player.setWeapon(new Weapon(WeaponType.DAGGER.getWeapon(lines.get(9))));
        }
        if (WeaponType.DAGGER.getWeapon(lines.get(10)) != null) {
            player.setWeapon2(new Weapon(WeaponType.DAGGER.getWeapon(lines.get(10))));
        }
        i = 13;
        //Items
        player.inventory().clear();
        for (int x = i; x < i + Integer.parseInt(lines.get(12)); x++) {
            player.addItem(lines.get(x));
        }
        i += 2 + Integer.parseInt(lines.get(12));
        //Enemies
        map.getEnemies().clear();
        for (int x = i; x < i + Integer.parseInt(lines.get(i - 1)) * 4; x += 4) {
            Enemy enemy = EnemyType.RAT.getEnemy(lines.get(x), player);
            enemy.setHp(Integer.parseInt(lines.get(x + 1)));
            enemy.setX(Integer.parseInt(lines.get(x + 2)));
            enemy.setY(Integer.parseInt(lines.get(x + 3)));
            map.spawnEnemy(enemy);
        }
        i += 2 + Integer.parseInt(lines.get(i - 1)) * 4;
        //Tiles
        map.clearTiles();

        for (int y = 0; y < map.getSize(); y++) {
            for (int x = 0; x < map.getSize(); x++) {
                Tile tile = map.getTile(x, y);
                tile.setType(Tiletype.Void.getTile(lines.get(i)));
                i++;
            }
        }
        map.clearItems();
        i += 2;
        for (int x = i; x < i + Integer.parseInt(lines.get(i - 1)) * 3; x += 3) {
            Tile tile = map.getTile(Integer.parseInt(lines.get(x)), Integer.parseInt(lines.get(x + 1)));
            tile.addItem(lines.get(x + 2));
        }
        update();
    }

    /**
     * Alustaa pelaajan ja kartan
     */
    public void init() {
        player = new Player(11, 11, PlayerClass.Warrior);
        map = new Map(mapSize, tileSize, player);

        map.createRoom(10, 10, 14, 14);
        map.getTile(12, 12).setType(Tiletype.StairsDown);
    }

    @Override
    public void start(Stage stage) {

        //Game Scene
        initializeLayout();
        initializeMapDrawer();
        initializeTextArea();
        initializeStatScreen();
        initializeStartGameScreen();
        initializeGameoverScreen();
        initializeClassSelection();

        mh = new MovementHandler(map, textArea);

        Scene gameoverScreen = new Scene(gameover, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 - 10);
        Scene startScreen = new Scene(startGame, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 - 10);
        Scene charactercreation = new Scene(classSelection, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 - 10);
        Scene game = new Scene(grid, WIDTH + tileSize * 5 - 10, HEIGHT + tileSize * 5 - 10);

        gameoverScreen.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    stage.setScene(charactercreation);
                    break;
                case L: {
                    try {
                        load();
                        stage.setScene(game);
                    } catch (IOException ex) {
//                            Logger.getLogger(DungeonCrawlerApplication.class.getName()).log(Level.SEVERE, null, ex);
                        stage.setScene(charactercreation);
                    }
                }
                break;
            }
        });

        startScreen.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    stage.setScene(charactercreation);
                    break;
                case L: {
                    try {
                        load();
                        stage.setScene(game);
                    } catch (IOException ex) {
//                            Logger.getLogger(DungeonCrawlerApplication.class.getName()).log(Level.SEVERE, null, ex);
                        stage.setScene(charactercreation);
                    }
                }
                break;
            }
        });

        charactercreation.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player = new Player(11, 11, PlayerClass.Warrior);
                    map.generateStartingRoom();
                    map.setplayer(player);
                    mapDrawer.drawAll();
                    updateStatScreen();
                    updateCamera();
                    stage.setScene(game);

                    break;
                case R:
                    player = new Player(11, 11, PlayerClass.Ranger);
                    map.generateStartingRoom();
                    map.setplayer(player);
                    mapDrawer.drawAll();
                    updateStatScreen();
                    updateCamera();
                    stage.setScene(game);
                    break;
                case M:
                    player = new Player(11, 11, PlayerClass.Mage);
                    map.generateStartingRoom();
                    map.setplayer(player);
                    mapDrawer.drawAll();
                    updateStatScreen();
                    updateCamera();
                    stage.setScene(game);
                    break;
                default:
                    break;

            }
        });

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
                    if (player.inventory().size() <= 35) {
                        mh.pickUp(player);
                        mapDrawer.drawAll();
                    } else {
                        textArea.appendText("Your inventory is full \n");
                    }
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
                    } else if (mh.getState() == State.Options) {
                        if (mh.getChooser().getY() == 0) {
                            try {
                                save();
                                textArea.appendText("Game saved \n");
                            } catch (IOException ex) {
                                Logger.getLogger(DungeonCrawlerApplication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (mh.getChooser().getY() == 1) {
                            try {
                                mh.setState(State.Normal);
                                mh.getChooser().setX(0);
                                mh.getChooser().setY(0);
                                load();
                                textArea.appendText("Game loaded \n");
                                update();
                            } catch (IOException ex) {
                                textArea.appendText("There is nothing to load \n");
                                update();
                            }
                        }

                    } else if (mh.getState() == State.Spells && mh.getChooser().getX() == 1) {
                        mh.castSpell(mh.getChooser().getY());
                        player.setActed(true);
                        mh.setState(State.Normal);
                    } else if (map.getTile(player.x(), player.y()).getType() == Tiletype.StairsDown) {
                        map.goDown();
                        update();
                    } else if (map.getTile(player.x(), player.y()).getType() == Tiletype.StairsUp) {
                        map.goUp();
                        update();
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
                    update();
                    break;

                case O:
                    canvas.setTranslateX(0);
                    canvas.setTranslateY(0);
                    mh.setState(State.Options);
                    mapDrawer.drawOptionScreen(mh.getChooser());
                    break;
                default:
                    break;

            }
            updateStatScreen();
            if (player.hasActed()) {
                updateCamera();
                if (mh.getState() == State.Inventory) {
                    mh.setState(State.Normal);
                }
                mh.endTurn();
                update();

            } else if (mh.getState() == State.Inventory) {
                mapDrawer.drawInventory(mh.getChooser());
            } else if (mh.getState() == State.Spells) {
                mapDrawer.drawSpells(mh.getChooser());
            } else if (mh.getState() == State.Options) {
                mapDrawer.drawOptionScreen(mh.getChooser());
            }
            if (player.checkIfDead()) {
                stage.setScene(gameoverScreen);
            }
        });

        stage.setTitle("DungeonCrawler");
        stage.setScene(startScreen);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Alustaa pelin näkymän
     */
    private void initializeLayout() {

        //Layout of the game
        grid = new GridPane();
        grid.setPrefSize(WIDTH, HEIGHT);
        grid.setHgap(0);
        grid.setVgap(0);

    }

    /**
     * Alustaa pelin alku näkymän
     */
    private void initializeStartGameScreen() {
        startGame = new Pane();
        Label dungeonCrawler = new Label("DungeonCrawler");
        dungeonCrawler.setTranslateX(500);
        dungeonCrawler.setTranslateY(300);
        dungeonCrawler.setScaleX(3);
        dungeonCrawler.setScaleY(3);
        Label newGame = new Label(" Enter >  New Game");
        newGame.setTranslateX(480);
        newGame.setTranslateY(400);
        newGame.setScaleX(1.5);
        newGame.setScaleY(1.5);
        Label loadGame = new Label("L >   Load Game");
        loadGame.setTranslateX(510);
        loadGame.setTranslateY(440);
        loadGame.setScaleX(1.5);
        loadGame.setScaleY(1.5);
        startGame.getChildren().add(newGame);
        startGame.getChildren().add(loadGame);
        startGame.getChildren().add(dungeonCrawler);
    }

    /**
     * Alustaa pelin kuolema näkymän
     */
    private void initializeGameoverScreen() {
        gameover = new Pane();
        Label dungeonCrawler = new Label("Gameover");
        dungeonCrawler.setTranslateX(500);
        dungeonCrawler.setTranslateY(300);
        dungeonCrawler.setScaleX(3);
        dungeonCrawler.setScaleY(3);
        Label newGame = new Label(" Enter >  New Game");
        newGame.setTranslateX(480);
        newGame.setTranslateY(400);
        newGame.setScaleX(1.5);
        newGame.setScaleY(1.5);
        Label loadGame = new Label("L >   Load Game");
        loadGame.setTranslateX(510);
        loadGame.setTranslateY(440);
        loadGame.setScaleX(1.5);
        loadGame.setScaleY(1.5);
        gameover.getChildren().add(newGame);
        gameover.getChildren().add(loadGame);
        gameover.getChildren().add(dungeonCrawler);
    }

    /**
     * Alustaa hahmo valikon
     */
    private void initializeClassSelection() {
        classSelection = new Pane();
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
        classSelection.getChildren().addAll(choose, warrior, ranger, mage);
    }

    /**
     * Alustaa kartan piirtäjän
     */
    private void initializeMapDrawer() {

        canvas = new Canvas(mapSize * tileSize, mapSize * tileSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        mapDrawer = new MapDrawer(map, gc);
        updateCamera();
        grid.add(canvas, 1, 1, 1, 1); //1,1

    }

    /**
     * Alustaa tekstikentän
     */
    private void initializeTextArea() {

        //TextArea (Only display)
        textArea = new TextArea();
        textArea.setMinSize(WIDTH + tileSize * 5, tileSize * 5);
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setMouseTransparent(true);
        grid.add(textArea, 0, 0, 2, 1); //0,0

    }

    /**
     * Kohdistaa ruudun että pelaaja on sen keskellä, paitsi jos pelaaja on
     * liian lähellä kentän reunoja
     */
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

    /**
     * Alustaa tekstikentän jossa näkyy pelaajan arvot
     */
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

    /**
     * päivittää tekstikentän, keskittää kameran ja piirtää näkymän uusiksi
     */
    private void update() {
        mapDrawer.drawAll();
        updateCamera();
        updateStatScreen();
    }

    /**
     * lisää tekstikentälle kaikki pelaajan arvot
     */
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

    /**
     * Tarkistaa onko vihollisia kuollut, jos on ne poistetaan. Seuraavaksi
     * liikuttaa vihollisia. jonka jälkeen tarkistaa uusiksi onko vihollisia
     * kuollut. Lopuksi päivittää kaikki näkymät
     */
//    private void endTurn() {
//
//        //checks if there are dead enemies
//        List<Enemy> deadEnemies = new ArrayList<>();
//        map.getEnemies().forEach(enemy -> {
//            if (enemy.checkIfDead()) {
//                deadEnemies.add(enemy);
//            }
//        });
//
//        //deletes dead enemies
//        if (deadEnemies.isEmpty() == false) {
//            deadEnemies.forEach(dead -> {
//                textArea.appendText("You killed " + dead.getName() + " \n");
//                player.gainExp(dead.getExp());
//                map.getTile(dead.x(), dead.y()).setCharacter(null);
//                map.removeEnemy(dead);
//            });
//
//        }
//
//        //Enemies turn
//        map.getEnemies().forEach(enemy -> {
//            enemy.hasNotAttacked();
//            mh.act(enemy);
//            if (enemy.hasAttacked()) {
//                textArea.appendText(enemy.getName() + " hit you for " + enemy.getLastDamage() + " damage \n");
//            }
//        });
//        if (player.checkIfDead()) {
//            textArea.appendText("You died \n");
//        }
//
//        //checks if there are dead enemies again (if enemies kill each other)
//        deadEnemies.clear();
//        map.getEnemies().forEach(enemy -> {
//            if (enemy.checkIfDead()) {
//                deadEnemies.add(enemy);
//            }
//        });
//
//        //deletes dead enemies
//        if (deadEnemies.isEmpty() == false) {
//            deadEnemies.forEach(dead -> {
//                textArea.appendText(dead.getName() + " died \n");
//                map.getTile(dead.x(), dead.y()).setCharacter(null);
//                map.removeEnemy(dead);
//            });
//
//        }
//        player.checkIfRegenerates();
//        player.checkIfLevelUp();
//        updateStatScreen();
//        player.hasNotAttacked();
//        player.setActed(false);
//        //draws what happened
//        mapDrawer.drawAll();
//
//    }
}
