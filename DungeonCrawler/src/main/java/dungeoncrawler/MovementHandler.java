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
        switch(this.state) {
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
//        if (state == 0) {
//            move(player, dir);
//        } else if (state == 1) {
//            closeDoor(player, dir);
//        }
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
        player.acted(true);
    }
    
    public void move(Enemy enemy) {
        
        int DistanceX = enemy.getTarget().X() - enemy.X();
        int DistanceY = enemy.getTarget().Y() - enemy.Y();
        //Jos target vieressä iskee
        if(Math.abs(DistanceX) + Math.abs(DistanceY) == 1) {
            enemy.attack();
        
        //Jos target aggressionrangessa liikkuu päin
        } else if (Math.max(Math.abs(DistanceY), Math.abs(DistanceX)) <= enemy.aggressionRange()){
            Direction UPorDOWN = null;
            Direction RIGHTorLEFT = null;
            Tile UoD = null;
            Tile RoL = null;
                   
            //Onko target ylä- vai alapuolella?
            if (DistanceY > 0) {
                UPorDOWN = Direction.DOWN;
                UoD = map.getTile(enemy.X(), enemy.Y() +1);
            } else {
                UPorDOWN = Direction.UP;
                UoD = map.getTile(enemy.X(), enemy.Y() -1);
            }
            //Onko target oikealla vai vasemmalla?
            if (DistanceX > 0) {
                RIGHTorLEFT = Direction.RIGHT;
                RoL = map.getTile(enemy.X() +1, enemy.Y());
            } else {
                RIGHTorLEFT = Direction.LEFT;
                RoL = map.getTile(enemy.X() -1, enemy.Y());
            }
            
            //Liikkuu suuntaan jossa kohde on kauempana, paitsi jos siinä suunnassa on este
            if (Math.abs(DistanceY) > Math.abs(DistanceX)) {
                if (UoD.occupied() || UoD.getType() == Tiletype.Wall || UoD.getType() == Tiletype.Door) {
                    if (!RoL.occupied() && RoL.getType() != Tiletype.Wall && RoL.getType() != Tiletype.Door) {
                        enemy.move(map, RIGHTorLEFT); 
                    }
                } else {
                    enemy.move(map, UPorDOWN);                    
                }         
            } else {
                if (RoL.occupied() || RoL.getType() == Tiletype.Wall || RoL.getType() == Tiletype.Door) {
                    if (!UoD.occupied() && UoD.getType() != Tiletype.Wall && UoD.getType() != Tiletype.Door) {
                        enemy.move(map, UPorDOWN);
                    }
                } else {
                    enemy.move(map, RIGHTorLEFT);
                }
            }
        } else {
            
        }
    }    

    public void closeDoor(Player player, Direction dir) {

        Tile tile = map.getTile(player.X() + dir.X(), player.Y() + dir.Y());

        switch (tile.getType()) {
            case OpenDoor:
                tile.setType(Tiletype.Door);
                textArea.appendText("You closed the door \n");
                player.noAttack();
                player.acted(true);
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
        for(int i = 1; i <= player.getRange(); i++) {
            Tile tile = map.getTile(player.X() + i*dir.X(), player.Y() + i*dir.Y());
            if(tile.occupied()) {
                player.attack(tile.getCharacter());
                textArea.appendText("You hit " + tile.getCharacter().getName() + " for " + player.getLastDamage() + " damage \n");
                break;
            } else if (tile.getType() == Tiletype.Wall || tile.getType() == Tiletype.Door) {
                break;
            }
        }
        player.acted(true);
        this.state = 0;
    }
}
