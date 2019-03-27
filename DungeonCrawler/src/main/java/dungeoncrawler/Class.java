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
public enum Class {    
    Warrior(new Weapon("Sword",4,7,1), null),
    Ranger(new Weapon ("Bow",2,3,10), new Weapon("Dagger",1,2,1)),
    Mage(new Weapon ("Dagger",1,2,1), null);
    
    private Weapon weapon;
    private Weapon weapon2;
    
    private Class(Weapon weapon, Weapon weapon2) {
        this.weapon = weapon;
        this.weapon2 = weapon2;
        
    }
    
    public Weapon StartingWeapon() {
        return this.weapon;
    }
    
    public Weapon SecondWeapon() {
        return this.weapon2;
    }
    
    
}
