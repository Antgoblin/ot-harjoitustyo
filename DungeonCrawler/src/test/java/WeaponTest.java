/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.Items.Item;
import dungeoncrawler.Items.Item.ItemType;
import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.WeaponType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jy
 */
public class WeaponTest {

    Weapon weapon;

    @Before
    public void setUp() {
        weapon = new Weapon(WeaponType.DAGGER);
    }

    @Test
    public void getActionOnEquip() {
        assertEquals("Equip", weapon.getAction());
    }

    @Test
    public void getTypeToimii() {
        assertEquals(ItemType.WEAPON, weapon.getType());
    }

    @Test
    public void getWeaponTypeToimii() {
        assertEquals(WeaponType.DAGGER, weapon.getWeaponType());
    }

    @Test
    public void getWeaponFromName() {
        weapon = new Weapon(WeaponType.DAGGER.getWeapon("Bow"));
        assertEquals("Bow", weapon.getName());
    }

    @Test
    public void getWeaoponReturnNullIfWrongName() {
        assertEquals(null, WeaponType.DAGGER.getWeapon("AAA"));
    }

}
