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
    private int exp;
    private Random random = new Random();
    
    public Enemy(String name, int x, int y, int hp, int mindamage, int maxdamage, int aggressionRange, int exp, Character target) {
        super(x, y, hp);
        this.name = name;
        this.minDamage = mindamage;
        this.maxDamage = maxdamage;
        this.aggressionRange = aggressionRange;
        this.exp = exp;
        this.target = target;
    }
    
    public void moveTowards(Map map, int x, int y) {
        int distanceX = Math.abs(x - this.X());
        int distanceY = Math.abs(y - this.Y());
        
        if(distanceY > distanceX) {
            if(y - this.Y() < 0) {
                this.move(map, Direction.UP);
            } else {
                this.move(map, Direction.DOWN);
            }
            
        } else if (distanceX >= distanceY) {
            if(x - this.X() > 0) {
                this.move(map, Direction.RIGHT);
            } else {
                this.move(map, Direction.LEFT);
            }
        }
        
        
    }
    
    public void attack() {
        int damage =  random.nextInt(maxDamage - minDamage) + minDamage;
        target.loseHp(damage);
        this.attacked(damage);
        
    }
    
    public void act(Map map) {
        int distanceX = Math.abs(target.X() - this.X());
        int distanceY = Math.abs(target.Y() - this.Y());
        int fartherDistance = Math.max(distanceX, distanceY);
        int closerDistance = Math.min(distanceX, distanceY);
        
        if(closerDistance > 0 || fartherDistance > 1 && distanceY <= aggressionRange && distanceX <= aggressionRange) {
            moveTowards(map, target.X(), target.Y());
            this.noAttack();
        } else if (fartherDistance == 1 && closerDistance == 0) {
            attack();
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getExp() {
        return this.exp;
    }
    
    public Character getTarget() {
        return this.target;
    }
    
    public int aggressionRange() {
        return this.aggressionRange;
    }

}
