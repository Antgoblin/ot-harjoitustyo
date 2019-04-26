/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.Random;
import javafx.scene.control.TextArea;

/**
 *
 * @author jy
 */
public class MovementHandler {

    public enum State {
        Normal, OpeningDoor, Shooting, Inventory, Chooser, Help;
    }

    private Map map;
    private TextArea textArea;
    private State state;
    private Random random = new Random();
    private Chooser chooser = new Chooser();

    public MovementHandler(Map map) {
        this.map = map;
        this.state = State.Normal;
    }

    public MovementHandler(Map map, TextArea textArea) {
        this.map = map;
        this.textArea = textArea;
        this.state = State.Normal;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public Chooser getChooser() {
        return this.chooser;
    }

    public void handle(Player player, Direction dir) {
        switch (this.state) {
            case Normal:
                move(player, dir);
                break;
            case OpeningDoor:
                closeDoor(player, dir);
                break;
            case Shooting:
                shoot(player, dir);
                break;
            case Inventory:
                if (chooser.getY() < map.getPlayer().inventory().size() - 1 || dir != Direction.DOWN) {
                    chooser.move(dir);
                }
                break;
            default:
                break;
        }
    }

    public Direction randomDirection() {
        Direction dir = Direction.DOWN;
        switch (random.nextInt(4)) {
            case 0:
                dir = Direction.UP;
                break;
            case 1:
                dir = Direction.DOWN;
                break;
            case 2:
                dir = Direction.LEFT;
                break;
            case 3:
                dir = Direction.RIGHT;
                break;
            default:
                break;
        }
        return dir;
    }

    public void move(Player player, Direction dir) {
        //checks if enemies in way
        map.getEnemies().forEach(enemy -> {
            if (enemy.x() == player.x() + dir.x() && enemy.y() == player.y() + dir.y()) {
                attack(player, enemy);
            }
        });

        //checks what Tiletype
        if (!player.hasAttacked()) {
            Tile tile = map.getTile(player.x() + dir.x(), player.y() + dir.y());

            if (tile.getType() == Tiletype.Floor || tile.getType() == Tiletype.OpenDoor || tile.getType() == Tiletype.StairsDown || tile.getType() == Tiletype.StairsUp) {
                player.move(map, dir);
                player.hasNotAttacked();
                seeItem(player);

            } else if (tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.OpenDoor);
            }
        }
        player.setActed(true);
    }

    public void attack(Player player, Enemy enemy) {
        player.attack(enemy);
        if (enemy.sleeping()) {
            enemy.wakeUp();
            if (textArea != null) {
                textArea.appendText(enemy.getName() + " woke \n");
            }
        }
        if (textArea != null) {
            textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
        }
    }

    public void seeItem(Player player) {
        Tile tile = map.getTile(player.x(), player.y());
        if (tile.containsItem()) {
            textArea.appendText("There is " + tile.getItem().name() + " on the ground \n");
        }
    }

    public void pickUp(Player player) {
        Tile tile = map.getTile(player.x(), player.y());
        if (tile.containsItem()) {
            player.addItem(tile.getItem());
            if (textArea != null) {
                textArea.appendText("You picked up " + tile.getItem().name() + "\n");
            }
            tile.removeItem();
            seeItem(player);
        }
    }

    public void act(Enemy enemy) {
        if (enemy.sleeping()) {
            chanceToWake(enemy);
        } else {
            move(enemy);
        }
    }

    public void chanceToWake(Enemy enemy) {
        //If player is in range and enemy is sleeping 30% chance to wakeUp
        int distanceX = enemy.getTarget().x() - enemy.x();
        int distanceY = enemy.getTarget().y() - enemy.y();
        if (Math.max(Math.abs(distanceY), Math.abs(distanceX)) <= enemy.aggressionRange()) {
            int number = random.nextInt(10);
            if (number > 6) {
                enemy.wakeUp();
                if (textArea != null) {
                    textArea.appendText(enemy.getName() + " woke \n");
                }
            }
        }
    }

    public void move(Enemy enemy) {

        int distanceX = enemy.getTarget().x() - enemy.x();
        int distanceY = enemy.getTarget().y() - enemy.y();
        //Jos target vieressä iskee
        if (Math.abs(distanceX) + Math.abs(distanceY) == 1) {
            enemy.attack();

            //Jos target aggressionrangessa liikkuu päin
        } else if (Math.max(Math.abs(distanceY), Math.abs(distanceX)) <= enemy.aggressionRange()) {
            Direction upOrDown = null;
            Direction rightOrLeft = null;
            Tile uD = null;
            Tile rL = null;

            //Onko target ylä- vai alapuolella?
            if (distanceY > 0) {
                upOrDown = Direction.DOWN;
                uD = map.getTile(enemy.x(), enemy.y() + 1);
            } else {
                upOrDown = Direction.UP;
                uD = map.getTile(enemy.x(), enemy.y() - 1);
            }
            //Onko target oikealla vai vasemmalla?
            if (distanceX > 0) {
                rightOrLeft = Direction.RIGHT;
                rL = map.getTile(enemy.x() + 1, enemy.y());
            } else {
                rightOrLeft = Direction.LEFT;
                rL = map.getTile(enemy.x() - 1, enemy.y());
            }

            //Liikkuu suuntaan jossa kohde on kauempana, paitsi jos siinä suunnassa on este
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                if (uD.occupied() || uD.getType() == Tiletype.Wall || uD.getType() == Tiletype.Door) {
                    if (!rL.occupied() && rL.getType() != Tiletype.Wall && rL.getType() != Tiletype.Door) {
                        enemy.move(map, rightOrLeft);
                    }
                } else {
                    enemy.move(map, upOrDown);
                }
            } else {
                if (rL.occupied() || rL.getType() == Tiletype.Wall || rL.getType() == Tiletype.Door) {
                    if (!uD.occupied() && uD.getType() != Tiletype.Wall && uD.getType() != Tiletype.Door) {
                        enemy.move(map, upOrDown);
                    }
                } else {
                    enemy.move(map, rightOrLeft);
                }
            }
        } else {
            Direction random = randomDirection();
            if (map.getTile(enemy.x() + random.x(), enemy.y() + random.y()).getType() == Tiletype.Floor && !map.getTile(enemy.x() + random.x(), enemy.y() + random.y()).occupied()) {
                enemy.move(map, random);
            }
        }
    }

    public void closeDoor(Player player, Direction dir) {

        Tile tile = map.getTile(player.x() + dir.x(), player.y() + dir.y());

        switch (tile.getType()) {
            case OpenDoor:
                if (!tile.occupied()) {
                    tile.setType(Tiletype.Door);
                    if (textArea != null) {
                        textArea.appendText("You closed the door \n");
                    }
                    player.hasNotAttacked();
                    player.setActed(true);

                }
                break;

            case Door:
                if (textArea != null) {
                    textArea.appendText("The door is already closed \n");
                }
                break;

            default:
                if (textArea != null) {
                    textArea.appendText("There is no door there \n");
                }
                break;
        }
        this.state = State.Normal;
    }

    public void shoot(Player player, Direction dir) {
        for (int i = 1; i <= player.getRange(); i++) {
            Tile tile = map.getTile(player.x() + i * dir.x(), player.y() + i * dir.y());
            if (tile.occupied() && tile.getCharacter() != null) {
                player.attack(tile.getCharacter());
                if (textArea != null) {
                    textArea.appendText("You hit " + tile.getCharacter().getName() + " for " + player.getLastDamage() + " damage \n");
                }
                Enemy enemy = map.getEnemy(tile.x(), tile.y());
                enemy.rage();
                if (enemy.sleeping()) {
                    enemy.wakeUp();
                    if (textArea != null) {
                        textArea.appendText(enemy.getName() + " woke \n");
                    }
                }
                break;
            } else if (tile.getType() == Tiletype.Wall || tile.getType() == Tiletype.Door) {
                break;
            }
        }
        player.setActed(true);
        this.state = state.Normal;
    }

    public void dropItem(Player player, int i) {
        map.getTile(player.x(), player.y()).setItem(player.inventory().get(i));
        player.loseItem(i);
    }

    public void Action(Player player) {
        switch (player.inventory().get(chooser.getY()).getAction()) {
            case "Equip":
                player.equipWeapon(chooser.getY());
                break;
            case "Drink":
                player.drinkPotion(chooser.getY());
        }
    }
}
