import java.util.Arrays;
import java.util.Random;

public class Player {
	private int[] choice;


	public Player() {
		this.choice = new int[3];
	}

	public void p1Choice() {
		Random rand = new Random();
		for (int i = 0;i<3;i++) {
			this.choice[i] = rand.nextInt(2);
		}
	}

	public void p2Choice(int[] p1Choice) {
		this.choice[0] = getOpposite(p1Choice[1]);
		this.choice[1] = p1Choice[0];
		this.choice[2] = p1Choice[1];
	}

	public int[] getChoice() {
		return choice;
	}

	private int getOpposite(int i) {
		if (i == 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "choice is " + Arrays.toString(choice);
	}
}
