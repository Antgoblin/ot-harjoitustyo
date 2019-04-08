/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.Random;

/**
 *
 * @author antonpaa
 */
public enum WeaponType {
    
    // Name, mindmg, maxdmg, range, rarity, depth
    DAGGER("Dagger", 3, 5, 1),
    LONGSWORD("Longsword", 5, 10, 1),
    BOW("Bow", 2, 3, 10);
    
    private String name;
    private int minDamage;
    private int maxDamage;
    private int range;
//    private double rarity;
//    private int depth;
    private Random random = new Random();


    private WeaponType(String name, int min, int max, int range) {
        this.name = name;
        this.minDamage = min;
        this.maxDamage = max;
        this.range = range;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getDamage() {
        int damage =  random.nextInt(maxDamage - minDamage +1) + minDamage;
        return damage;
    }
    
    public int getRange() {
        return range;
    }

}
