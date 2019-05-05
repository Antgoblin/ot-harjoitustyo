/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author antonpaa
 */
public enum PotionType {

    // name, hpgain, managain, rarity, depth 
    LHP("Lesser Healing potion", 20, 0, 1, 1),
    HP("Healing potion", 50, 0, 1, 10),
    GHP("Greater Healing potion", 100, 0, 1, 30),
    LMP("Lesser Mana potion", 0, 20, 1, 1),
    MP("Mana potion", 0, 50, 1, 10),
    GMP("Greater Mana potion", 0, 100, 1, 30);

    private String name;
    private int healthGain;
    private int manaGain;
    private double rarity;
    private int depth;

    private PotionType(String name, int hp, int mp, int rarity, int depth) {
        this.name = name;
        this.healthGain = hp;
        this.manaGain = mp;
        this.rarity = rarity;
        this.depth = depth;
    }

    public String getName() {
        return this.name;
    }

    public int getHealthGain() {
        return this.healthGain;
    }

    public int getManaGain() {
        return this.manaGain;
    }

    /**
     * Metodi selvittää kuinka todennäköisesti potion esiintyy tietyllä
     * syvyydellä
     *
     * @param depth luolaston syvyys
     * @return luvun kuinka todennäköisesti potion esiintyisi annetulla
     * syvyydellä
     */
    public double getWeight(int depth) {
        return this.rarity / (Math.abs(this.depth - depth) + 1);
    }

    /**
     * Metodilla saadaan haluttu määrä potioneja. Metodi valitsee potionit
     * verraten sille annettua syvyyttä ja potion tyyppien esiintymissyvyyttä
     * sekä harvinaisuutta. Metodia käytetään kun luolastoon luodaan potionit
     *
     * @param depth luolaston syvyys
     * @param amount kuinka monta halutaan
     * @return listan jossa sille annetun määrän verran satunnaisia potioneja
     */
    public List<PotionType> randomize(int depth, int amount) {
        List<PotionType> potions = getAll();
        List<PotionType> result = new ArrayList<>();
        Stream<Double> weights = getAll().stream().map(e -> e.rarity / (Math.abs(e.depth - depth) + 1));

        int nWeapons = 0;
        double total = weights.reduce(0.0, (a, b) -> a + b);

        while (nWeapons < amount) {
            double cumsum = 0;
            double random = Math.random() * total;

            for (int i = 0; i < potions.size(); i++) {
                cumsum += potions.get(i).getWeight(depth);
                if (random <= cumsum) {
                    result.add(potions.get(i));
                    nWeapons++;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Metodilla saa selville kaikki eri potionit
     *
     * @return Listan jossa kaikki eri tyyppise potionit
     */
    public List<PotionType> getAll() {
        List<PotionType> potiontypes = new ArrayList<>();
        potiontypes.add(LHP);
        potiontypes.add(HP);
        potiontypes.add(GHP);
        potiontypes.add(LMP);
        potiontypes.add(MP);
        potiontypes.add(GMP);
        return potiontypes;
    }

    /**
     * Etsii potionin jolla tietty nimi
     *
     * @param name jonka niminen potion halutaan
     * @return potion tyypin jolla sama nimi kuin annettu, jos ei ole sellaista
     * palauttaa null
     */
    public PotionType getPotion(String name) {
        List<PotionType> potiontypes = getAll();
        List<PotionType> searchedPotions = new ArrayList<>();
        potiontypes.forEach(w -> {
            if (w.getName().equals(name)) {
                searchedPotions.add(w);
            }
        });
        if (!searchedPotions.isEmpty()) {
            return searchedPotions.get(0);

        } else {
            return null;
        }
    }

}
