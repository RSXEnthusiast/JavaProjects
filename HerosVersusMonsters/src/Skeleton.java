
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class Skeleton extends Monster {

    public Skeleton() {
        super("Skeleton", 100, 3, 30, 50, 80, 30, 30, 50, "skeleton");
    }

    public int throwsBomb() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= 80) {
            int temp = rand.nextInt(100 - 60) + 60;
            System.out.println("Skeleton throws bomb! It does " + temp + " damage!");
            return temp;
        } else {
            System.out.println("Skeleton attacks!");
            return this.attack();
        }
    }
}
