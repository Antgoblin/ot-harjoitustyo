/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.logic;

import dungeoncrawler.logic.Tiletype;
import dungeoncrawler.Items.Item;
import dungeoncrawler.Items.Potion;
import dungeoncrawler.Items.PotionType;
import dungeoncrawler.Items.Spellbook;
import dungeoncrawler.Items.SpellbookType;
import dungeoncrawler.Items.Weapon;
import dungeoncrawler.Items.WeaponType;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.StackPane;

/**
 *
 * @author jy
 */
public class Tile extends StackPane {

    private int x;
    private int y;
    private Tiletype type;
    private Character character = null;
    private List<Item> items = new ArrayList<>();

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Tiletype.Void;

    }

    /**
     * Metodi lisää ruutuun esineen
     *
     * @param item mikä esine lisätään
     */
    public void setItem(Item item) {
        this.items.add(item);
    }

    /**
     * Metodi poistaa ruudusta listan viimeisimmän esineen
     */
    public void removeItem() {
        this.items.remove(items.size() - 1);
    }

    /**
     * Metodi poistaa ruudusta kaikki esineet
     */
    public void removeItems() {
        this.items.clear();
    }

    /**
     * Metodilla saa selville ruudussa olevan listan viimeisen esineen, jos
     * ruudussa on esineitä
     *
     * @return esineen joka oli listan viimeinen
     */
    public Item getItem() {
        if (!items.isEmpty()) {
            return items.get(items.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Metodi tarkistaa onko ruudussa esineitä
     *
     * @return true jos ruudussa on esine, muuten false
     */
    public boolean containsItem() {
        if (items.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setType(Tiletype type) {
        this.type = type;
    }

    public Tiletype getType() {
        return this.type;
    }

    /**
     * Metodi tarkistaa onko ruudussa hahmo
     *
     * @return true jos ruudussa on hahmo, muulloin false
     */
    public boolean occupied() {
        return this.character != null;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return this.character;
    }

    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Metodi etsii nimetyn esineen ja laittaa sellaisen ruudun sisälle
     *
     * @param name minkä niminen esine laitetaan
     */
    public void addItem(String name) {
        if (WeaponType.DAGGER.getWeapon(name) != null) {
            this.items.add(new Weapon(WeaponType.DAGGER.getWeapon(name)));
        } else if (PotionType.HP.getPotion(name) != null) {
            this.items.add(new Potion(PotionType.HP.getPotion(name)));
        } else if (SpellbookType.FIREBOLTBOOK.getSpellbook(name) != null) {
            this.items.add(new Spellbook(SpellbookType.FIREBOLTBOOK.getSpellbook(name)));
        }
    }
}
