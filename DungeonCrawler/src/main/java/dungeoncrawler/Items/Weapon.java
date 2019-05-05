/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

import dungeoncrawler.Items.WeaponType;
import java.util.Random;

/**
 *
 * @author jy
 */
public class Weapon extends Item {

    private WeaponType weapon;

    public Weapon(WeaponType w) {
        super(w.getName(), ItemType.WEAPON);
        this.weapon = w;
    }

    public WeaponType getWeaponType() {
        return this.weapon;
    }

    /**
     * Metodi kutsuu WeaponTypen samannimistä metodia getDamage()
     *
     * @return luvun paljon ase iskee
     */
    public int getDamage() {
        int damage = this.weapon.getDamage();
        return damage;
    }

    /**
     * Metodi kutsuu WeaponTypen samannimistä metodia getRange()
     *
     * @return luvun joka kertoo kuinka kauas aseella voi iskeä
     */
    public int getRange() {
        return this.weapon.getRange();
    }

    /**
     * Kertoo mitä aseelle pystyy tehdä. Kaikki Itemiä lisäävät luokat omistavat
     * getAction() metodin
     *
     * @return String "Equip"
     */
    public String getAction() {
        return "Equip";
    }
}
