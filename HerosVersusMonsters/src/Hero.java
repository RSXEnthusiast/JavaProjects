
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Joel Kalich
 */
public abstract class Hero extends DungeonCharacter {

    protected int blockChance;

    public Hero(int health, int attackSpeed, int minDamage, int maxDamage, int hitChance, int blockChance, String heroType) {
        super("Null", health, attackSpeed, minDamage, maxDamage, hitChance, heroType);
        this.blockChance = blockChance;
    }

    public int getBlockChance() {
        return blockChance;
    }

    public void setBlockChance(int blockChance) {
        this.blockChance = blockChance;
    }

    public void setName() {
        System.out.print("Enter a name for your Hero: ");
        Scanner scanner = new Scanner(System.in);
        super.setName(scanner.next());
    }

    public boolean block() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 <= this.blockChance) {
            System.out.println("Blocked attack!");
            return true;
        }
        return false;
    }
}
