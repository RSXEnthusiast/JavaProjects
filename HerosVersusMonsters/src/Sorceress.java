
import java.util.Random;

/**
 *
 * @author Joel Kalich
 */
public class Sorceress extends Hero {

    public Sorceress() {
        super(75, 5, 25, 45, 70, 30, "sorceress");
    }

    public void heal() {
        Random rand = new Random();
        int temp = rand.nextInt(25)+25;
        int temp2 = this.health + temp;
        if (temp2 >= 75) {
            this.health = 75;
            System.out.println("You heal to full health!");
        } else {
            System.out.println("You heal " + temp + " Health Points!");
            this.health = temp2;
        }
    }
}
