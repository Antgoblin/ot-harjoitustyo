/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author jy
 */
public class Enemy extends Character {

//    private String name;
    private int aggressionRange;
    private Character target;
    private int minDamage;
    private int maxDamage;
    private int exp;
    private Random random = new Random();

    public Enemy(String name, int x, int y, int hp, Color color, int mindamage, int maxdamage, int aggressionRange, int exp, Character target) {
        super(name, x, y, hp, color);
//        this.name = name;
        this.minDamage = mindamage;
        this.maxDamage = maxdamage;
        this.aggressionRange = aggressionRange;
        this.exp = exp;
        this.target = target;
    }

    public void attack() {
        int damage = random.nextInt(maxDamage - minDamage) + minDamage;
        target.loseHp(damage);
        this.attacked(damage);

    }

    public int getExp() {
        return this.exp;
    }

    public Character getTarget() {
        return this.target;
    }

    public void setTarget(Character character) {
        this.target = character;
    }

    public int aggressionRange() {
        return this.aggressionRange;
    }

    public void rage() {
        this.aggressionRange = 100;
    }
}
