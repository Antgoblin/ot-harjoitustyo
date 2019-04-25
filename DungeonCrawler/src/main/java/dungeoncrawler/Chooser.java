/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

/**
 *
 * @author jy
 */
public class Chooser {

    private int y;

    public Chooser() {
        this.y = 0;
    }
    
    public int getY() {
        return this.y;
    }

    public void move(Direction dir) {
        this.y += dir.y();
        if (this.y < 0) {
            this.y = 0;
        }
    }

}
