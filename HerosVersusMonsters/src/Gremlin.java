
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class Gremlin extends Monster {

    public Gremlin() {
        super("Gremlin", 70, 5, 15, 30, 80, 40, 20, 40, "gremlin");
    }

    public int claw() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= 70) {
            int temp = rand.nextInt(45 - 30) + 30;
            System.out.println("Gremling uses Claw! It did " + temp + " damage!");
            return temp;
        } else {
            System.out.println("Gremlin attacks!");
            return this.attack();
        }
    }
}
