/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.Random;


/**
 *
 * @author jy
 */
public class Weapon extends Item{
    private int minDamage;
    private int maxDamage;
    private int range;
    private Random random = new Random();
    
    public Weapon(String name, int min, int max, int range) {
        super(name);
        this.minDamage = min;
        this.maxDamage = max;
        this.range = range;
    }
    
    public int getDamage() {
        int damage =  random.nextInt(maxDamage - minDamage +1) + minDamage;
        return damage;
    }
    
    public int getRange() {
        return range;
    }
    
}
