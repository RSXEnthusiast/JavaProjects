import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Game {

	public static void main(String[] args) {
		
	}
	
	private static void PCRunner(double gameNums) {
		double p1Wins = 0;
		double p2Wins = 0;
		double ties = 0;
		for (double i = 0; i < gameNums; i++) {
			System.out.println(gameNums - i + " games left.");
			switch (PCvsPC()) {
			case 0:
				p1Wins++;
				break;
			case 1:
				p2Wins++;
				break;
			case -1:
				ties++;
				break;
			}
		}
		System.out.println(
				"Player 1 won " + p1Wins + " times, or " + (p1Wins / gameNums * 100) + " percent of the time.");
		System.out.println(
				"Player 2 won " + p2Wins + " times, or " + (p2Wins / gameNums * 100) + " percent of the time.");
		System.out.println("There were " + ties + " ties.");
	}

	private static int PCvsPC() {
		ArrayList<Integer> deck = new ArrayList<Integer>();
		for (int i = 0; i < 26; i++) {
			deck.add(0);
			deck.add(1);
		}
		Player p1 = new Player();
		p1.p1Choice();
		Player p2 = new Player();
		p2.p2Choice(p1.getChoice());
		remove(deck, p1.getChoice());
		remove(deck, p2.getChoice());
		shuffle(deck);
		return playGame(deck, p1.getChoice(), p2.getChoice());
	}

	private static int playGame(ArrayList<Integer> deck, int[] p1, int[] p2) {
		Queue<Integer> q = new LinkedList<Integer>();
		while (true) {
			int draw = deck.remove(0);
			q.add(draw);
			if (q.size() >= 4) {
				q.poll();
			}
			if (q.size() == 3 && didWin(p1, q)) {
				return 0;
			} else if (q.size() == 3 && didWin(p2, q)) {
				return 1;
			} else if (deck.size() == 0) {
				return -1;
			}
		}
	}

	private static boolean didWin(int[] a, Queue<Integer> q) {
		int[] mem = new int[q.size()];
		boolean result = true;
		for (int i = 0; i < a.length; i++) {
			mem[i] = q.peek();
			if (a[i] != q.poll()) {
				result = false;
			}
		}
		for (int i = 0; i < mem.length; i++) {
			q.add(mem[i]);
		}
		return result;
	}

	private static void remove(ArrayList<Integer> deck, int[] choice) {
		for (int i = 0; i < choice.length; i++) {
			deck.remove((Object) choice[i]);
		}
	}

	private static void shuffle(ArrayList<Integer> deck) {
		Random rand = new Random();
		for (int i = 0; i < 10000; i++) {
			swap(deck, rand.nextInt(deck.size() - 1), rand.nextInt(deck.size() - 1));
		}
	}

	private static void swap(ArrayList<Integer> a, int i1, int i2) {
		int temp = a.get(i1);
		a.set(i1, a.get(i2));
		a.set(i2, temp);
	}
}
