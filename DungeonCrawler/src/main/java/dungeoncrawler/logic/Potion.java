/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.logic;

import dungeoncrawler.logic.PotionType;

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

    /**
     * palauttaa tyypin sisällä olevan luvun joka kertoo paljon saa elämää
     *
     * @return luvun kuinka paljon saa elämää
     */
    public int getHealthGain() {
        return this.potion.getHealthGain();
    }

    /**
     * palauttaa tyypin sisällä olevan luvun joka kertoo paljon saa manaa
     *
     * @return luvun kuinka paljon saa manaa
     */
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
