/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

import dungeoncrawler.Items.PotionType;

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

    public int getHealthGain() {
        return this.potion.getHealthGain();
    }

    public int getManaGain() {
        return this.potion.getManaGain();
    }

    /**
     * Kertoo mitä potionille pystyy tehdä. Kaikki Itemiä lisäävät luokat
     * omistavat getAction() metodin
     *
     * @return String "Drink"
     */
    public String getAction() {
        return "Drink";
    }

}
