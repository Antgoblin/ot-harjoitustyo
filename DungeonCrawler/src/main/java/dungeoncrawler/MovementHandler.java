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

    public void handle(Player player, Direction dir) {
        if (state == 0) {
            move(player, dir);
        } else if (state == 1) {
            closeDoor(player, dir);
        }
    }

    public void move(Player player, Direction dir) {
        //checks if enemies in way
        map.getEnemies().forEach(enemy -> {
            if (enemy.getX() == player.getX() + dir.getX() && enemy.getY() == player.getY() + dir.getY()) {
                player.attack(enemy);
                textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
            }
        });

        //checks what Tiletype
        if (player.getIfAttacked() == false) {
            Tile tile = map.getTile(player.getX() + dir.getX(), player.getY() + dir.getY());

            if (tile.getType() == Tiletype.Floor || tile.getType() == Tiletype.OpenDoor) {
                player.move(dir);
                player.noAttack();

            } else if (tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.OpenDoor);
            }
        }
        player.acted();
    }

    public void closeDoor(Player player, Direction dir) {

        Tile tile = map.getTile(player.getX() + dir.getX(), player.getY() + dir.getY());

        if (tile.getType() == Tiletype.OpenDoor) {
            tile.setType(Tiletype.Door);
            textArea.appendText("You closed the door \n");
            player.noAttack();
        } else if (tile.getType() == Tiletype.Door) {
            textArea.appendText("The door is already closed \n");
        } else {
            textArea.appendText("There is no door there \n");
        }
        this.state = 0;
        player.acted();
    }
}
