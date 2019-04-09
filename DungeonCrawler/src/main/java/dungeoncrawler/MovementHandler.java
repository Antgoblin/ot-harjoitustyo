/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import javafx.scene.control.TextArea;

/**
 *
 * @author jy
 */
public class MovementHandler {

    private Map map;
    private TextArea textArea;
    private int state;

    public MovementHandler(Map map, TextArea textArea) {
        this.map = map;
        this.textArea = textArea;
        this.state = 0;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void handle(Player player, Direction dir) {
        switch (this.state) {
            case 0:
                move(player, dir);
                break;
            case 1:
                closeDoor(player, dir);
                break;
            case 2:
                shoot(player, dir);
                break;
            default:
                break;
        }
    }

    public void move(Player player, Direction dir) {
        //checks if enemies in way
        map.getEnemies().forEach(enemy -> {
            if (enemy.x() == player.x() + dir.x() && enemy.y() == player.y() + dir.y()) {
                player.attack(enemy);
//                enemy.rage();
                textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
            }
        });

        //checks what Tiletype
        if (!player.hasAttacked()) {
            Tile tile = map.getTile(player.x() + dir.x(), player.y() + dir.y());

            if (tile.getType() == Tiletype.Floor || tile.getType() == Tiletype.OpenDoor || tile.getType() == Tiletype.StairsDown || tile.getType() == Tiletype.StairsUp) {
                player.move(map, dir);
                player.hasNotAttacked();

            } else if (tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.OpenDoor);
            }
        }
        player.setActed(true);
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
        }
    }

    public void closeDoor(Player player, Direction dir) {

        Tile tile = map.getTile(player.x() + dir.x(), player.y() + dir.y());

        switch (tile.getType()) {
            case OpenDoor:
                if (!tile.occupied()) {
                    tile.setType(Tiletype.Door);
                    textArea.appendText("You closed the door \n");
                    player.hasNotAttacked();
                    player.setActed(true);

                }
                break;

            case Door:
                textArea.appendText("The door is already closed \n");
                break;

            default:
                textArea.appendText("There is no door there \n");
                break;
        }
        this.state = 0;
    }

    public void shoot(Player player, Direction dir) {
        for (int i = 1; i <= player.getRange(); i++) {
            Tile tile = map.getTile(player.x() + i * dir.x(), player.y() + i * dir.y());
            if (tile.occupied() && tile.getCharacter() != null) {
                player.attack(tile.getCharacter());
                textArea.appendText("You hit " + tile.getCharacter().getName() + " for " + player.getLastDamage() + " damage \n");
                map.getEnemy(tile.x(), tile.y()).rage();
                break;
            } else if (tile.getType() == Tiletype.Wall || tile.getType() == Tiletype.Door) {
                break;
            }
        }
        player.setActed(true);
        this.state = 0;
    }
}
