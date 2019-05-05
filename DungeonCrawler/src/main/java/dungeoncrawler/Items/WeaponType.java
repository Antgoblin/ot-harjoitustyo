/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

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

    /**
     * Metodi ottaa satummanvaraisesti luvun aseen minimidamagen ja
     * maksimidamagen väliltä
     *
     * @return luvun minimidamagen ja maksimidamagen väliltä
     */
    public int getDamage() {
        int damage = random.nextInt(maxDamage - minDamage + 1) + minDamage;
        return damage;
    }

    public int getRange() {
        return range;
    }

    /**
     * Metodi tutkii kuinka todennäköisesti ase esiintyy tietyllä syvyydellä
     *
     * @param depth syvyys jossa ollaan
     * @return luvun joka kertoo kuinka todennäköisesti ase esiintyy syvyydellä
     */
    public double getWeight(int depth) {
        return this.rarity / (Math.abs(this.depth - depth) + 1);
    }

    /**
     * Metodi vertailee sille annettua syvyyttä ja ottaa listalle aseita niiden
     * esiintymissyvyyttä ja harvinaisuutta käyttäen. Mitä lähempänä annettu
     * syvyys on aseen esiintymissyvyyttä suurentaa tietyn aseen
     * todennäköisyyttä joutua listalle. Listalle otetaan aseita metodille
     * annetun määrän verran
     *
     * @param depth syvyys jossa ollaan
     * @param amount kuinka monta asetta halutaan
     * @return listan aseita
     */
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

    /**
     * Metodi kerää listan kaikista eri asetyypeistä.
     *
     * @return Listan jossa on jokaista eri asetta yksi kappale
     */
    public List<WeaponType> getAll() {
        List<WeaponType> weapontypes = new ArrayList<>();
        weapontypes.add(DAGGER);
        weapontypes.add(LONGSWORD);
        weapontypes.add(BOW);
        return weapontypes;
    }

    /**
     * Palauttaa aseen jolla tietty nimi
     *
     * @param name minkä niminen ase halutaan
     * @return WeaponTypen jonka nimi sama kuin annettu String, jos sellaista ei
     * löydy palauttaa null.
     */
    public WeaponType getWeapon(String name) {
        List<WeaponType> weapontypes = getAll();
        List<WeaponType> searchedWeapons = new ArrayList<>();
        weapontypes.forEach(w -> {
            if (w.getName().equals(name)) {
                searchedWeapons.add(w);
            }
        });
        if (!searchedWeapons.isEmpty()) {
            return searchedWeapons.get(0);

        } else {
            return null;

        }
    }
}
