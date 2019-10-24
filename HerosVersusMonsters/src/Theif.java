
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class Theif extends Hero {

    public Theif() {
        super(75, 6, 20, 40, 80, 40, "theif");
    }

    public boolean extraTurn() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= 70) {
            System.out.println("Sneak successful!");
            return true;
        } else {
            System.out.println("Sneak unsuccesful!");
            return false;
        }
    }
}
