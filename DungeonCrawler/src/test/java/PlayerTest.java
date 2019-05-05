/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.logic.Player;
import dungeoncrawler.logic.PlayerClass;
import dungeoncrawler.Items.Item;
import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.WeaponType;
import java.util.ArrayList;
import java.util.List;
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
public class PlayerTest {

    Player player;

    @Before
    public void setUp() {
        player = new Player(2, 3, PlayerClass.Ranger);
    }

    @Test
    public void setAndGetLvl() {
        player.setLvl(5);
        assertEquals(5, player.getLvl());
    }

    @Test
    public void getMana() {
        assertEquals(10, player.currentMana());
        assertEquals(10, player.maxMana());
    }

    @Test
    public void LosingMana() {
        assertEquals(true, player.loseMana(5));
        assertEquals(5, player.currentMana());
        assertEquals(10, player.maxMana());
    }

    @Test
    public void LosingTooMuchMana() {
        assertEquals(false, player.loseMana(100));
        assertEquals(10, player.currentMana());
        assertEquals(10, player.maxMana());
    }

    @Test
    public void GainingMana() {
        player.loseMana(5);
        player.gainMana(3);
        assertEquals(8, player.currentMana());
        assertEquals(10, player.maxMana());
    }

    @Test
    public void GainingTooMuchMana() {
        player.loseMana(5);
        player.gainMana(100);
        assertEquals(10, player.currentMana());
        assertEquals(10, player.maxMana());
    }

    @Test
    public void GoldAnDExpAtBeginning() {
        assertEquals(0, player.getGold());
        assertEquals(0, player.getExp());
    }

    @Test
    public void SettingGoldAnDExp() {
        player.setExp(10);
        player.setGold(10);
        assertEquals(10, player.getGold());
        assertEquals(10, player.getExp());
    }

    @Test
    public void GainingGoldAnDExp() {
        player.gainExp(10);
        player.gainGold(10);
        assertEquals(10, player.getGold());
        assertEquals(10, player.getExp());
    }

    @Test
    public void LosingGoldAnDExp() {
        player.gainExp(10);
        player.gainGold(10);
        player.loseExp(5);
        player.loseGold(5);
        assertEquals(5, player.getGold());
        assertEquals(5, player.getExp());
    }

    @Test
    public void GoldAndExpDontGoToNegativesWhenLosingTooMuch() {
        player.gainExp(10);
        player.gainGold(10);
        player.loseExp(500);
        player.loseGold(500);
        assertEquals(0, player.getGold());
        assertEquals(0, player.getExp());
    }

    @Test
    public void GettingRange() {
        assertEquals(10, player.getRange());
    }

    @Test
    public void GettingWeaponAndWeapon2() {
        assertEquals(PlayerClass.Ranger.startingWeapon(), player.getWeapon());
        assertEquals(PlayerClass.Ranger.secondWeapon(), player.getWeapon2());
    }

    @Test
    public void SwitchingWeaponAndWeapon2() {
        player.switchWeapons();
        assertEquals(PlayerClass.Ranger.secondWeapon(), player.getWeapon());
        assertEquals(PlayerClass.Ranger.startingWeapon(), player.getWeapon2());
    }

    @Test
    public void GettingInventory() {
        List<Item> items = new ArrayList<>();
        items.add(PlayerClass.Ranger.startingWeapon());
        items.add(PlayerClass.Ranger.secondWeapon());
        assertEquals(items, player.inventory());
    }

    @Test
    public void GettingPlayerClass() {
        assertEquals(PlayerClass.Ranger, player.getPlayerClass());
    }

    @Test
    public void AddItemWorks() {
        player.addItem("Bow");
        assertEquals("Bow", player.inventory().get(player.inventory().size() - 1).getName());
        player.addItem("Mana potion");
        assertEquals("Mana potion", player.inventory().get(player.inventory().size() - 1).getName());
        player.addItem("Book of Healing");
        assertEquals("Book of Healing", player.inventory().get(player.inventory().size() - 1).getName());
    }

    @Test
    public void setWeaponAndSetWeapon2() {
        Weapon weapon = new Weapon(WeaponType.BOW);
        player.setWeapon(weapon);
        player.setWeapon2(weapon);
        assertEquals(weapon, player.getWeapon());
        assertEquals(weapon, player.getWeapon2());
    }

    @Test
    public void listOfSpellsIsEmpty() {
        assertEquals(0, player.spells().size());
    }

    @Test
    public void setCurrentAndMaxManaAndGainMaxMana() {
        player.setMaxMana(30);
        player.setCurrentMana(9);
        assertEquals(30, player.maxMana());
        assertEquals(9, player.currentMana());
        player.gainMaxMana(10);
        assertEquals(40, player.maxMana());
    }

    @Test
    public void CheckIfLevelUpAndLevelUp() {
        player.gainExp(1);
        player.checkIfLevelUp();
        assertEquals(1, player.getLvl());
        assertEquals(60, player.getMaxHp());
        assertEquals(10, player.maxMana());
        player.gainExp(300);
        player.checkIfLevelUp();
        assertEquals(2, player.getLvl());
        assertEquals(70, player.getMaxHp());
        assertEquals(15, player.maxMana());
    }

    @Test
    public void MageLevelingUp() {
        player = new Player(3, 3, PlayerClass.Mage);
        player.lvlUp();
        assertEquals(35, player.maxMana());
    }

    @Test
    public void CheckIfRegenerates() {
        player.loseHp(10);
        player.loseMana(5);
        player.checkIfRegenerates();
        assertEquals(50, player.getCurrentHp());
        assertEquals(5, player.currentMana());
        player.checkIfRegenerates();
        player.checkIfRegenerates();
        player.checkIfRegenerates();
        player.checkIfRegenerates();
        player.checkIfRegenerates();
        player.checkIfRegenerates();
        player.checkIfRegenerates();
        assertEquals(51, player.getCurrentHp());
        assertEquals(6, player.currentMana());
    }

    @Test
    public void DrinkingPotion() {
        player.addItem("Mana potion");
        player.setCurrentMana(0);
        player.drinkPotion(2);
        assertEquals(10, player.currentMana());
        assertEquals(2, player.inventory().size());
    }

    @Test
    public void EquipinWeapon() {
        Weapon weapon = new Weapon(WeaponType.LONGSWORD);
        player.inventory().add(weapon);
        player.equipWeapon(2);
        assertEquals(weapon, player.getWeapon());
        assertEquals(3, player.inventory().size());
    }

    @Test
    public void ReadingSpellBookAndGettingSpell() {
        player.addItem("Book of Healing");
        player.readSpellbook(2);
        assertEquals(2, player.inventory().size());
        assertEquals("Heal", player.getSpell(0).getName());
    }
}
