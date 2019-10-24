
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public abstract class Monster extends DungeonCharacter {

    protected int healChance;
    protected int minHeal;
    protected int maxHeal;
    protected int maxHealth;

    public Monster(String name, int health, int attackSpeed, int minDamage, int maxDamage, int hitChance, int healChance, int minHeal, int maxHeal, String monsterType) {
        super(name, health, attackSpeed, minDamage, maxDamage, hitChance, monsterType);
        this.healChance = healChance;
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
        this.maxHealth = health;
    }

    public void heal() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= this.healChance) {
            int temp = rand.nextInt(this.maxHeal - this.minHeal) + minHeal;
            int temp2 = this.health + temp;
            if (temp2 >= maxHealth) {
                this.health = maxHealth;
                System.out.println(this.getCharacterType() + " heals to full health!");
            } else {
                System.out.println(this.getCharacterType() + " healed " + temp + " Health Points!");
                this.health = temp2;
            }
        }
    }
}
