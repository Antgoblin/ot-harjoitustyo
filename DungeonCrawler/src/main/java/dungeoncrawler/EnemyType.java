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

    //(name, hp, mindmg, maxdmg, aggro, exp)
    RAT("Rat", 10, 5, 10, 20, 10, Color.GRAY, 5, 1),
    CAT("Cat", 15, 4, 6, 5, 5, Color.ORANGE, 3, 1),
    BEAR("Bear", 30, 4, 10, 5, 50, Color.BROWN, 3, 3),
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

    public Enemy spawn(Character target) {
        return new Enemy(name, 0, 0, hp, color, minDamage, maxDamage, aggressionRange, exp, target);
    }

    public Enemy spawn(int x, int y, Character target) {
        return new Enemy(name, x, y, hp, color, minDamage, maxDamage, aggressionRange, exp, target);
    }

    public double getWeight(int depth) {
        return this.spawnrate / (Math.abs(this.depth - depth) + 1);
    }

//    public List<Double> getWeights(int depth) {
//        
//    }
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

    public List<EnemyType> getAll() {
        List<EnemyType> enemytypes = new ArrayList<>();
        enemytypes.add(RAT);
        enemytypes.add(CAT);
        enemytypes.add(BEAR);
        enemytypes.add(DEATH);

        return enemytypes;
    }

}
