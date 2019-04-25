/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.List;

/**
 *
 * @author jy
 */
public class Chooser {

    private int y;
    private int x;

    public Chooser() {
        this.y = 0;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public void move(Direction dir) {
        if (dir.y() != 0) {
            this.y += dir.y();
            this.x = 0;
            if (this.y < 0) {
                this.y = 0;
            }
        } else {
            this.x += dir.x();
            if (this.x < 0) {
                this.x = 0;
            } else if (this.x > 2) {
                this.x = 2;
            }
        }
    }

}
