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

    public MovementHandler(Map map, TextArea textArea) {
        this.map = map;
        this.textArea = textArea;
    }

    public void moveUp(Player player) {

        //checks if enemies in way
        map.getEnemies().forEach(enemy -> {
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY() - 1) {
                player.attack(enemy);
                textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
            }
        });

        //checks what Tiletype
        if (player.getIfAttacked() == false) {
            Tile tile = map.getTile(player.getX(), player.getY() - 1);

            if (tile.getType() == Tiletype.Floor) {
                player.moveUp();
                player.noAttack();
            
            } else if(tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.Floor);
            }
        }
    }

    public void moveDown(Player player) {

        map.getEnemies().forEach(enemy -> {
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY() + 1) {
                player.attack(enemy);
                textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
            }
        });

        if (player.getIfAttacked() == false) {
            Tile tile = map.getTile(player.getX(), player.getY() + 1);
            
            if (tile.getType() == Tiletype.Floor) {
                player.moveDown();
                player.noAttack();
                
            } else if(tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.Floor);
            }
        }
    }
    

    public void moveRight(Player player) {
        
        map.getEnemies().forEach(enemy -> {
                if (enemy.getX() == player.getX() + 1 && enemy.getY() == player.getY()) {
                    player.attack(enemy);
                    textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
                }
            });
        
        if (player.getIfAttacked() == false) {
            Tile tile = map.getTile(player.getX() + 1, player.getY());
            
            if (tile.getType() == Tiletype.Floor) {
                player.moveRight();
                player.noAttack();
                
            } else if (tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.Floor);
            }
        }
    }

    public void moveLeft(Player player) {
        map.getEnemies().forEach(enemy -> {
                if (enemy.getX() == player.getX() - 1 && enemy.getY() == player.getY()) {
                    player.attack(enemy);
                    textArea.appendText("You hit " + enemy.getName() + " for " + player.getLastDamage() + " damage \n");
                }
            });
        
        if (player.getIfAttacked() == false) {
            Tile tile = map.getTile(player.getX() - 1, player.getY());
            
            if (tile.getType() == Tiletype.Floor) {
                player.moveLeft();
                player.noAttack();
                
            } else if (tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.Floor);
            }
        }
    }

}
