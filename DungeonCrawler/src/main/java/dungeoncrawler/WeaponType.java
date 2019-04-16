/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 *
 * @author antonpaa
 */
public enum WeaponType {

    // Name, mindmg, maxdmg, range, rarity, depth
    DAGGER("Dagger", 3, 5, 1, 2, 2),
    LONGSWORD("Longsword", 5, 10, 1, 2, 2),
    BOW("Bow", 2, 3, 10, 2, 2);

    private String name;
    private int minDamage;
    private int maxDamage;
    private int range;
    private double rarity;
    private int depth;
    private Random random = new Random();

    private WeaponType(String name, int min, int max, int range, double rarity, int depth) {
        this.name = name;
        this.minDamage = min;
        this.maxDamage = max;
        this.range = range;
        this.rarity = rarity;
        this.depth = depth;
    }

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        int damage = random.nextInt(maxDamage - minDamage + 1) + minDamage;
        return damage;
    }

    public int getRange() {
        return range;
    }

    public double getWeight(int depth) {
        return this.rarity / (Math.abs(this.depth - depth) + 1);
    }

    public List<WeaponType> randomize(int depth, int amount) {
        List<WeaponType> weapons = getAll();
        List<WeaponType> result = new ArrayList<>();
        Stream<Double> weights = getAll().stream().map(e -> e.rarity / (Math.abs(e.depth - depth) + 1));

        int nWeapons = 0;
        double total = weights.reduce(0.0, (a, b) -> a + b);

        while (nWeapons < amount) {
            double cumsum = 0;
            double random = Math.random() * total;

            for (int i = 0; i < weapons.size(); i++) {
                cumsum += weapons.get(i).getWeight(depth);
                if (random <= cumsum) {
                    result.add(weapons.get(i));
                    nWeapons++;
                    break;
                }
            }
        }
        return result;
    }

    public List<WeaponType> getAll() {
        List<WeaponType> weapontypes = new ArrayList<>();
        weapontypes.add(DAGGER);
        weapontypes.add(LONGSWORD);
        weapontypes.add(BOW);
        return weapontypes;
    }
}
