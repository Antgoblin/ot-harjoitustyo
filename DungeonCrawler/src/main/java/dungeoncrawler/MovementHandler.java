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
            if (enemy.X() == player.X() + dir.X() && enemy.Y() == player.Y() + dir.Y()) {
                player.attack(enemy);
                textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
            }
        });

        //checks what Tiletype
        if (player.getIfAttacked() == false) {
            Tile tile = map.getTile(player.X() + dir.X(), player.Y() + dir.Y());
            
            if (tile.getType() == Tiletype.Floor || tile.getType() == Tiletype.OpenDoor) {
                player.move(map, dir);
                player.noAttack();

            } else if (tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.OpenDoor);
            }
        }
        player.acted();
    }
    
    public void move(Enemy enemy) {
        Direction dir = chooseDirection(enemy);
        Tile tile = map.getTile(enemy.X() + dir.X(), enemy.Y() + dir.Y());
        
        if(enemy.getTarget().X() == enemy.X() + dir.X() && enemy.getTarget().Y() == enemy.Y() + dir.Y()) {
            enemy.attack();
        }
        
        if (enemy.getIfAttacked() == false) {
            
            if (tile.occupied()) {
                
            } else if (tile.getType() == Tiletype.Floor || tile.getType() == Tiletype.OpenDoor) {
                enemy.move(map, dir);
                enemy.noAttack();

//            } else if (tile.getType() == Tiletype.Door) {
//                tile.setType(Tiletype.OpenDoor);
            }
        }
        enemy.acted();
        
        
    }

    public Direction chooseDirection(Enemy enemy) {
        //IMPORTANT!
        int DistanceX = enemy.getTarget().X() - enemy.X();
        int DistanceY = enemy.getTarget().Y() - enemy.Y();
        int closer = Math.min(Math.abs(DistanceX),Math.abs(DistanceY));
        Direction dir = null;
        
        if (closer == Math.abs(DistanceX) && DistanceY < 0) {
            dir = Direction.UP;
        } else if (closer == Math.abs(DistanceX) && DistanceY > 0) {
            dir = Direction.DOWN;
        } else if (closer == Math.abs(DistanceY) && DistanceX > 0) {
            dir = Direction.RIGHT;
        } else if (closer == Math.abs(DistanceY) && DistanceX < 0) {
            dir = Direction.LEFT;
        }  
        
        return dir;
    }

    public void closeDoor(Player player, Direction dir) {

        Tile tile = map.getTile(player.X() + dir.X(), player.Y() + dir.Y());

        switch (tile.getType()) {
            case OpenDoor:
                tile.setType(Tiletype.Door);
                textArea.appendText("You closed the door \n");
                player.noAttack();
                player.acted();
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
}
