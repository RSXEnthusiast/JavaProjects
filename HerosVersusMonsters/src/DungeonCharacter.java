
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public abstract class DungeonCharacter {

    protected String name;
    protected int health;
    protected int attackSpeed;
    protected int minDamage;
    protected int maxDamage;
    protected int hitChance;
    protected int roundTurns;
    protected String characterType;

    public DungeonCharacter(String name, int health, int attackSpeed, int minDamage, int maxDamage, int hitChance, String characterType) {
        this.name = name;
        this.health = health;
        this.attackSpeed = attackSpeed;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.hitChance = hitChance;
        this.characterType = characterType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getHitChance() {
        return hitChance;
    }

    public void setHitChance(int hitChance) {
        this.hitChance = hitChance;
    }

    public int getRoundTurns() {
        return roundTurns;
    }

    public void setRoundTurns(int roundTurns) {
        this.roundTurns = roundTurns;
    }

    public String getCharacterType() {
        return characterType;
    }

    public void setCharacterType(String characterType) {
        this.characterType = characterType;
    }

    public int attack() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 <= this.hitChance) {
            int temp = rand.nextInt(this.maxDamage - this.minDamage) + minDamage;
            System.out.println("Attack is succesful! " + temp + " damage was done!");
            return temp;
        } else {
            System.out.println("Attack missed!");
            return 0;
        }
    }

    public void calcRoundTurns(DungeonCharacter other) {
        int temp = this.attackSpeed / other.attackSpeed;
        if (temp <= 0) {
            this.setRoundTurns(1);
        } else {
            this.setRoundTurns(temp);
        }
    }
}
