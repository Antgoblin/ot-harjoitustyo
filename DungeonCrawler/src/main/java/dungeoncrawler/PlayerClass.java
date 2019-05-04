/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.WeaponType;

/**
 *
 * @author jy
 */
public enum PlayerClass {
    Warrior(100, 0, new Weapon(WeaponType.LONGSWORD), null),
    Ranger(60, 10, new Weapon(WeaponType.BOW), new Weapon(WeaponType.DAGGER)),
    Mage(50, 25, new Weapon(WeaponType.DAGGER), null);

    private int hp;
    private int mana;
    private Weapon weapon;
    private Weapon weapon2;

    private PlayerClass(int hp, int mana, Weapon weapon, Weapon weapon2) {
        this.hp = hp;
        this.mana = mana;
        this.weapon = weapon;
        this.weapon2 = weapon2;

    }

    public Weapon startingWeapon() {
        return this.weapon;
    }

    public Weapon secondWeapon() {
        return this.weapon2;
    }

    public int hp() {
        return this.hp;
    }

    public int mana() {
        return this.mana;
    }

}
