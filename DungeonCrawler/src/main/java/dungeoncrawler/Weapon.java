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
public class Weapon {
    private String name;
    private int minDamage;
    private int maxDamage;
    private int range;
    private Random random = new Random();
    
    public Weapon(String name, int min, int max, int range) {
        this.name = name;
        this.minDamage = min;
        this.maxDamage = max;
        this.range = range;
    }
    
    public int getDamage() {
        int damage =  random.nextInt(maxDamage - minDamage) + minDamage;
        return damage;
    }
    
    public String name() {
        return name;
    }
    
    public int getRange() {
        return range;
    }
    
}
