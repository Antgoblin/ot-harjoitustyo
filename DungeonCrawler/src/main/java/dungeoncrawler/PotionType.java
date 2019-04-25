/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

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

    public double getWeight(int depth) {
        return this.rarity / (Math.abs(this.depth - depth) + 1);
    }

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

}
