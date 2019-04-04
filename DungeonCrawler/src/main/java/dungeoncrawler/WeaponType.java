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

    DAGGER("Dagger", 1, 2, 1),
    SWORD("Sword", 4, 7, 1),
    BOW("Bow", 2, 3, 10);
    
    private String name;
    private int minDamage;
    private int maxDamage;
    private int range;
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
