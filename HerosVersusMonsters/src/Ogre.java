
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class Ogre extends Monster {

    public Ogre() {
        super("Ogre", 200, 2, 30, 60, 60, 10, 30, 60, "ogre");
    }

    public int bodySlam() {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 >= 95) {
            System.out.println("Orge did a body slam! You have suffocated!");
            return -1;
        } else {
            System.out.println("Ogre attacks!");
            return this.attack();
        }
    }
}
