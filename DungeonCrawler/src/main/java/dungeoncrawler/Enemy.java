/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import java.util.Random;

/**
 *
 * @author jy
 */
public class Enemy extends Character {
    
    private String name;
    private int aggressionRange;
    private Character target;
    private int  minDamage;
    private int maxDamage;
//    private boolean attacked;
//    private int lastDamageDealt;
    private Random random = new Random();
    
    public Enemy(String name, int x, int y, int hp, int mindamage, int maxdamage, int aggressionRange, Character target) {
        super(x, y, hp);
        this.name = name;
        this.minDamage = mindamage;
        this.maxDamage = maxdamage;
        this.aggressionRange = aggressionRange;
        this.target = target;
//        this.lastDamageDealt = 0;
//        this.attacked = false;
    }
    
    public void moveTowards(int x, int y) {
        int distanceX = Math.abs(x - this.getX());
        int distanceY = Math.abs(y - this.getY());
        
        if(distanceY > distanceX) {
            if(y - this.getY() < 0) {
                this.moveUp();
            } else {
                this.moveDown();
            }
            
        } else if (distanceX >= distanceY) {
            if(x - this.getX() > 0) {
                this.moveRight();
            } else {
                this.moveLeft();
            }
        }
        
        
    }
    
    public void attack() {
        int damage =  random.nextInt(maxDamage - minDamage) + minDamage;
        target.loseHp(damage);
        this.attacked(damage);
        
    }
    
    public void act() {
        int distanceX = Math.abs(target.getX() - this.getX());
        int distanceY = Math.abs(target.getY() - this.getY());
        int fartherDistance = Math.max(distanceX, distanceY);
        int closerDistance = Math.min(distanceX, distanceY);
        
        if(closerDistance > 0 || fartherDistance > 1 && distanceY <= aggressionRange && distanceX <= aggressionRange) {
            moveTowards(target.getX(), target.getY());
            this.noAttack();
        } else if (fartherDistance == 1 && closerDistance == 0) {
            attack();
        }
    }
    
    public String getName() {
        return name;
    }
    
//    public int getDamageDealt() {
//        return lastDamageDealt;
//    }
//    
//    public boolean getIfAttacked() {
//        return attacked;
//    }
    
}
