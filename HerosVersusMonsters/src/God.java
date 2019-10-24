
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class God extends Hero {

    public God() {
        super(500, 1, 50, 100, 50, 40, "god");
    }

    public int stealHealth() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= 40) {
            int temp = rand.nextInt(50 - 10) + 10;
            System.out.println("You succesfully stole " + temp + " health!");
            return temp;
        } else {
            System.out.println("You failed to steal health!");
            return 0;
        }
    }
}
