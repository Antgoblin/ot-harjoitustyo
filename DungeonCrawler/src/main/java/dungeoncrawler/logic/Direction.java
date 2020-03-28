/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.logic;

/**
 *
 * @author jy
 */
public enum Direction {
    UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(-1, 0), UPRIGHT(1, -1), UPLEFT(-1, -1), DOWNRIGHT(1, 1), DOWNLEFT(-1, 1);

    private int x;
    private int y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
