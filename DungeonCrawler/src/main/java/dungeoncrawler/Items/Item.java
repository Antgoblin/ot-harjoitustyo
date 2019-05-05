/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jy
 */
public class Item {

    public enum ItemType {
        ITEM, WEAPON, POTION, SPELLBOOK;
    }

    private String name;
    private ItemType type;

    public Item(String name, ItemType i) {
        this.name = name;
        this.type = i;

    }

    public String getName() {
        return this.name;
    }

    public ItemType getType() {
        return this.type;
    }

    /**
     * Kertoo mitä esineelle pystyy tehdä. Kaikki Itemiä lisäävät luokat
     * omistavat getAction() metodin
     *
     * @return String ""
     */
    public String getAction() {
        return "";
    }
}
