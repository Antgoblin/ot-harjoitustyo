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
    Warrior(100, 0, new Weapon(WeaponType.LONGSWORD), null),
    Ranger(60, 10, new Weapon(WeaponType.BOW), new Weapon(WeaponType.DAGGER)),
    Mage(50, 25, new Weapon(WeaponType.DAGGER), null);
    
    private int hp;
    private int mana;
    private Weapon weapon;
    private Weapon weapon2;
    
    private Class(int hp, int mana, Weapon weapon, Weapon weapon2) {
        this.hp = hp;
        this.mana = mana;
        this.weapon = weapon;
        this.weapon2 = weapon2;
        
    }
    
    public Weapon StartingWeapon() {
        return this.weapon;
    }
    
    public Weapon SecondWeapon() {
        return this.weapon2;
    }
    
    public int Hp() {
        return this.hp;
    }
    
    public int mana() {
        return this.mana;
    }
    
}
