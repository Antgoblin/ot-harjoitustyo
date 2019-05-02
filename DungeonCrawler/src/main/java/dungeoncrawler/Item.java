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
        return ItemType.ITEM;
    }

    public String getAction() {
        return "";
    }

}
