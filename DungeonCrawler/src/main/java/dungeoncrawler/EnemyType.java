/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.paint.Color;

/**
 *
 * @author jy
 */
public enum EnemyType {

    //(name, hp, mindmg, maxdmg, aggro, exp, color, spawnrate, depth)
    RAT("Rat", 10, 5, 10, 20, 10, Color.GRAY, 5, 1),
    CAT("Cat", 15, 4, 6, 5, 15, Color.ORANGE, 3, 1),
    WOLF("Wolf", 20, 7, 7, 10, 30, Color.DARKGRAY, 4, 2),
    BEAR("Bear", 30, 4, 10, 5, 50, Color.BROWN, 3, 3),
    GIANTSPIDER("Giant Spider", 25, 10, 15, 8, 35, Color.PURPLE, 5, 10),
    DRAGON("Dragon", 150, 28, 54, 7, 500, Color.RED, 0.2, 24),
    DEATH("Death", 50, 1, 1000, 100, 9999, Color.BLACK, 1, 40);

    private int hp;
    private String name;
    private int aggressionRange;
    private int minDamage;
    private int maxDamage;
    private int exp;
    private Color color;
    private Random random = new Random();
    private double spawnrate;
    private int depth;

    private EnemyType(String name, int hp, int mindamage, int maxdamage, int aggressionRange, int exp, Color color, double spawnrate, int depth) {
        this.hp = hp;
        this.name = name;
        this.aggressionRange = aggressionRange;
        this.minDamage = mindamage;
        this.maxDamage = maxdamage;
        this.exp = exp;
        this.color = color;
        this.spawnrate = spawnrate;
        this.depth = depth;
    }

    /**
     * Luo vihollisen tyypistä ja laittaa sen hyökkäämään targetin kimppuun
     *
     * @param target hahmo johon hyökkää
     * @return vihollisen
     */
    public Enemy spawn(Character target) {
        return new Enemy(name, 0, 0, hp, color, minDamage, maxDamage, aggressionRange, exp, target);
    }

    /**
     * Luo vihollisen tyypistä ja laittaa sen hyökkäämään targetin kimppuun
     * vihollinen tulee annettuihin koordinaatteihin
     *
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @param target hahmo johon hyökkää
     * @return vihollisen
     */
    public Enemy spawn(int x, int y, Character target) {
        return new Enemy(name, x, y, hp, color, minDamage, maxDamage, aggressionRange, exp, target);
    }

    /**
     * Metodi selvittää kuinka todennäköisesti vihollinen esiintyy
     *
     * @param depth syvyys
     * @return luvun kuinka todennäköisesti vihollinen esiintyy syvyydelllä
     */
    public double getWeight(int depth) {
        return this.spawnrate / (Math.abs(this.depth - depth) + 1);
    }

    /**
     * Metodi palauttaa annetun määrän satunnaisia vihollisia
     *
     * @param depth syvyys
     * @param amount määrä
     * @return
     */
    public List<EnemyType> randomize(int depth, int amount) {
        List<EnemyType> enemies = getAll();
        List<EnemyType> result = new ArrayList<>();
        Stream<Double> weights = getAll().stream().map(e -> e.spawnrate / (Math.abs(e.depth - depth) + 1));

        int nEnemies = 0;
        double total = weights.reduce(0.0, (a, b) -> a + b);

        while (nEnemies < amount) {
            double cumsum = 0;
            double random = Math.random() * total;

            for (int i = 0; i < enemies.size(); i++) {
                cumsum += enemies.get(i).getWeight(depth);
                if (random <= cumsum) {
                    result.add(enemies.get(i));
                    nEnemies++;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Metodi palauttaa listan jossa kaikkia vihollistyyppejä yksi
     *
     * @return lista
     */
    public List<EnemyType> getAll() {
        List<EnemyType> enemytypes = new ArrayList<>();
        enemytypes.add(RAT);
        enemytypes.add(CAT);
        enemytypes.add(BEAR);
        enemytypes.add(WOLF);
        enemytypes.add(GIANTSPIDER);
        enemytypes.add(DRAGON);
        enemytypes.add(DEATH);

        return enemytypes;
    }

    /**
     * Palauttaa vihollisen jolla on haettu nimi sekä muuttaa vihollisen
     * kohteeksi annetun kohteen
     *
     * @param name nimi
     * @param target kohde
     * @return vihollisen
     */
    public Enemy getEnemy(String name, Character target) {
        List<EnemyType> enemytypes = getAll();
        EnemyType enemytype = enemytypes.get(0);
        for (int i = 0; i < enemytypes.size(); i++) {
            enemytype = enemytypes.get(i);
            if (enemytype.name.equals(name)) {
                break;
            }
        }
        Enemy enemy = enemytype.spawn(target);
        return enemy;
    }

}
