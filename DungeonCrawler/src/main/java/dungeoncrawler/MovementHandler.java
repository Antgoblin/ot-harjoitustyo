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

            if (tile.getType() == Tiletype.Floor) {
                player.move(dir);
                player.noAttack();
            
            } else if(tile.getType() == Tiletype.Door) {
                tile.setType(Tiletype.Floor);
            }
        }
    }
}
