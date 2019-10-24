
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class Warrior extends Hero {

    public Warrior() {
        super(125, 4, 35, 60, 80, 20, "warrior");
    }

    public int crushingBlow() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= 40) {
            int temp = rand.nextInt(175 - 75) + 75;
            System.out.println("Crushing Blow is succesful! You did " + temp + " damage!");
            return temp;
        } else {
            System.out.println("Attack missed!");
            return 0;
        }
    }
}
