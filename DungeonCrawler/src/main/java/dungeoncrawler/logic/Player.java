/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.logic;

import dungeoncrawler.logic.PlayerClass;
import dungeoncrawler.Items.Spell;
import dungeoncrawler.Items.Item;
import dungeoncrawler.Items.Potion;
import dungeoncrawler.Items.Spellbook;
import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.Item.ItemType;
import dungeoncrawler.Items.PotionType;
import dungeoncrawler.Items.SpellbookType;
import dungeoncrawler.Items.WeaponType;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author jy
 */
public class Player extends Character {

    private PlayerClass playerClass;
    private int lvl;
    private int exp;
    private int gold;
    private int maxMana;
    private int currentMana;
    private Weapon weapon;
    private Weapon weapon2;
    private int regenerationTimer = 6;
    private int str = 0;
    private List<Item> inventory = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();

    public Player(int x, int y, PlayerClass playerclass) {
        super("You", x, y, playerclass.hp(), Color.BLACK);
        this.weapon = playerclass.startingWeapon();
        this.weapon2 = playerclass.secondWeapon();
        if (this.weapon != null) {
            this.inventory.add(this.weapon);
        }
        if (this.weapon2 != null) {
            this.inventory.add(this.weapon2);
        }
        this.playerClass = playerclass;
        this.lvl = 1;
        this.exp = 0;
        this.gold = 0;
        this.maxMana = playerclass.mana();
        this.currentMana = playerclass.mana();
    }

    /**
     * Metodi poistaa sille annetulta viholliselta pelaajan aseen damagen verran
     * elämää
     *
     * @param target ketä isketään
     */
    public void attack(Enemy target) {
        target.rage();
        int damage = 1;
        if (this.weapon != null) {
            damage = weapon.getDamage();
        }
        this.attacked(damage);
        target.loseHp(damage);
    }

    /**
     * Metodi poistaa sille annetulta hahmolta pelaajan aseen damagen verran
     * elämää
     *
     * @param target ketä isketään
     */
    public void attack(Character target) {
        int damage = 1;
        if (this.weapon != null) {
            damage = weapon.getDamage();
        }
        this.attacked(damage);
        target.loseHp(damage);
    }

    public PlayerClass getPlayerClass() {
        return this.playerClass;
    }

    public int getLvl() {
        return this.lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    /**
     * Metodilla lisätään pelaajalle kokemuspisteitä
     *
     * @param amount Määrä kuinka paljon lisätään
     */
    public void gainExp(int amount) {
        this.exp += amount;
    }

    /**
     * Metodilla vähennetään pelaajalta kokemuspisteitä
     *
     * @param amount kuinka paljon vähennetään
     */
    public void loseExp(int amount) {
        this.exp -= amount;
        if (this.exp < 0) {
            this.exp = 0;
        }
    }

    public int maxMana() {
        return this.maxMana;
    }

    public int currentMana() {
        return this.currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * Metodilla pelaajalle lisätään manaa, Ei voi mennä yli maksimi manan
     *
     * @param amount kuinka paljon lisätään
     */
    public void gainMana(int amount) {
        this.currentMana += amount;
        if (this.currentMana > this.maxMana) {
            this.currentMana = this.maxMana;
        }
    }

    /**
     * Metodilla pelaajalle lisätään pelaajan manan maksimimäärää
     *
     * @param amount kuinka paljon lisätään
     */
    public void gainMaxMana(int amount) {
        this.maxMana += amount;
    }

    /**
     * Metodilla vähennetään pelaajalta manaa. Manaa ei vähennetä jos se menisi
     * alle nollan
     *
     * @param amount kuinka paljon vähennetään
     *
     * @return true jos manaa vähennettiin ja false jos sitä ei vähennetty
     */
    public boolean loseMana(int amount) {
        if (this.currentMana >= amount) {
            this.currentMana -= amount;
            return true;
        } else {
            return false;
        }
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Metodilla lisätään pelaajalle kultaa
     *
     * @param amount kuinka paljon lisätään
     */
    public void gainGold(int amount) {
        this.gold += amount;
    }

    /**
     * Metodilla vähennetään pelaajalta kultaa. Kullan määrä ei voi mennä alle
     * nollan
     *
     * @param amount kuinka paljon vähennetään
     */
    public void loseGold(int amount) {
        this.gold -= amount;
        if (this.gold < 0) {
            this.gold = 0;
        }
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon2() {
        return this.weapon2;
    }

    public void setWeapon2(Weapon weapon) {
        this.weapon2 = weapon;
    }

    public List<Item> inventory() {
        return this.inventory;
    }

    public List<Spell> spells() {
        return this.spells;
    }

    /**
     * Metodilla lisätään pelaajan inventoryyn esine
     *
     * @param item esine joka lisätään
     */
    public void addItem(Item item) {
        this.inventory.add(item);
    }

    /**
     * Metodi lisää pelaajan inventoryyn tietyn nimisen esineen
     *
     * @param name minkä niminen esine lisätään
     */
    public void addItem(String name) {
        if (WeaponType.DAGGER.getWeapon(name) != null) {
            this.inventory.add(new Weapon(WeaponType.DAGGER.getWeapon(name)));
        } else if (PotionType.HP.getPotion(name) != null) {
            this.inventory.add(new Potion(PotionType.HP.getPotion(name)));
        } else if (SpellbookType.FIREBOLTBOOK.getSpellbook(name) != null) {
            this.inventory.add(new Spellbook(SpellbookType.FIREBOLTBOOK.getSpellbook(name)));
        }
    }

    /**
     * Metodi laittaa pelaajan invetorista aseen "asepaikalle" (käteen?)
     *
     * @param i indeksi monesko esine inventorista laitetaan
     */
    public void equipWeapon(int i) {
        if (this.inventory.get(i).getType() == ItemType.WEAPON) {
            Weapon weapon = (Weapon) this.inventory.get(i);
            if (this.weapon2 == null && this.weapon != null) {
                this.weapon2 = weapon;
            } else {
                this.weapon = weapon;

            }
        }
    }

    /**
     * Metodilla pelaajan inventorista poistetaan potion ja sen vaikutukset
     * lisätään pelaajaan
     *
     * @param i indeksi monesko esine inventorista juodaan
     */
    public void drinkPotion(int i) {
        if (this.inventory.get(i).getType() == ItemType.POTION) {
            Potion potion = (Potion) this.inventory.get(i);
            gainHp(potion.getHealthGain());
            gainMana(potion.getManaGain());
            loseItem(i);
        }
    }

    /**
     * Metodilla pelaajan inventorista poistetaan kirja ja sen sisältämä loitsu
     * lisätään pelaajaan
     *
     * @param i indeksi monesko esine inventorista luotaan
     */
    public void readSpellbook(int i) {
        if (this.inventory.get(i).getType() == ItemType.SPELLBOOK) {
            Spellbook spellbook = (Spellbook) this.inventory.get(i);
            this.spells.add(spellbook.getSpell());
            loseItem(i);
        }
    }

//    public void castSpell(int i) {
//        Spell spell = this.spells.get(i);
//        switch (spell.getName()) {
//            case "Firebolt":
//                
//        }
//    }
    /**
     * Metodilla poistetaan pelaajan inventorista esine. jos esine oli pelaajan
     * käytössä poistetaan se myös asepaikasta.
     *
     * @param i inseksi monesko esine poistetaan
     */
    public void loseItem(int i) {
        Item item = this.inventory.get(i);
        if (item == this.weapon && this.weapon2 != null) {
            this.weapon = this.weapon2;
            this.weapon2 = null;
        } else if (item == this.weapon) {
            this.weapon = null;
        } else if (item == this.weapon2) {
            this.weapon2 = null;
        }

        this.inventory.remove(i);
    }

    /**
     * Metodilla pelaajan ensimmäisessä ja toisessa asepaikassa olevat aseet
     * vaihtavat paikkaa
     */
    public void switchWeapons() {
        if (this.weapon != null && this.weapon2 != null) {
            Weapon one = this.weapon;
            Weapon two = this.weapon2;
            this.weapon = two;
            this.weapon2 = one;

        }
    }

    /**
     * Metodi tarkistaa kuinka kauas voi pelaajan aseella iskeä
     *
     * @return aseen kantaman
     */
    public int getRange() {
        return weapon.getRange();
    }

    /**
     * Metodi tarkistaa onko pelaajalla tarpeeksi kokemuspisteitä kehittymään
     * taso. Jos on niin pelaaja nousee tason
     */
    public void checkIfLevelUp() {
        if (this.exp >= this.lvl * this.lvl * 100) {
            lvlUp();
        }
    }

    /**
     * Metodi nostaa pelaajan tasoa yhdellä, elämää kymmenellä jokaista pelaajan
     * tasoa kohdan ja voimaa yhdellä
     */
    public void lvlUp() {
        this.gainMaxHp(this.lvl * 10);
        int mana = 5;
        if (this.playerClass == PlayerClass.Mage) {
            mana = 10;
        }
        this.gainMaxMana(mana);
        this.lvl++;

    }

    /**
     * Metodi tarkistaa onko regenerationTimer nolla. Jos ei, niin se laskee
     * yhdellä. Jos taas on, niin pelaaja saa yhden elämäpisteen ja
     * regenerationTimer nousee kuuteen
     */
    public void checkIfRegenerates() {
        if (this.regenerationTimer > 0) {
            this.regenerationTimer--;
        } else {
            this.gainHp(1);
            this.gainMana(1);
            this.regenerationTimer = 6;
        }
    }

    /**
     * hakee pelaajalle listatuista loitsuista loitsun
     *
     * @param i monesko loitsu listalta
     * @return loitsu
     */
    public Spell getSpell(int i) {
        if (this.spells != null) {
            return this.spells.get(i);
        } else {
            return null;
        }
    }
}
