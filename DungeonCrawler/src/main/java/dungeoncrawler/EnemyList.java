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
public enum EnemyList {
    
    //(name, hp, mindmg, maxdmg, aggro, exp)
    RAT("Rat", 10, 5, 10, 20, 10),
    CAT("Cat", 15, 4, 6, 5, 5);
    
    
    private int hp;
    private String name;
    private int aggressionRange;
    private int  minDamage;
    private int maxDamage;
    private int exp;
    
    private EnemyList(String name, int hp, int mindamage, int maxdamage, int aggressionRange, int exp) {
        this.hp = hp;
        this.name = name;
        this.aggressionRange = aggressionRange;
        this.minDamage = mindamage;
        this.maxDamage = maxdamage;
        this.exp = exp;
    }
    
    public Enemy spawn(int x, int y, Character target) {
        return new Enemy(name, x, y, hp, minDamage, maxDamage, aggressionRange, exp, target);
    }
    
}
