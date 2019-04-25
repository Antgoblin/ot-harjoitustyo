/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

/**
 *
 * @author antonpaa
 */
public class Potion extends Item {

    private PotionType potion;

    public Potion(PotionType p) {
        super(p.getName(), ItemType.POTION);
        this.potion = p;
    }

    public ItemType getType() {
        return ItemType.POTION;
    }

    public int getHealthGain() {
        return this.potion.getHealthGain();
    }

    public int getManaGain() {
        return this.potion.getManaGain();
    }

    public String getAction() {
        return "Drink";
    }

}
