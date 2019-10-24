package game;

import static game.Piece.BLOCK;
import java.awt.*;
import java.util.*;
import java.lang.Math;

/**
 *
 * @author joelk
 */
public class Game {

    // ********* static (class variables) ********
    public static final int BLOCK = 30;
    public static final int GRID = 20;

    public static final int WIDTH = BLOCK * GRID;
    public static final int HEIGHT = BLOCK * GRID;

    public static final int DELAY = 10;
    public static final int ENEMIES = 15;
    public static int LIVES = 3;

    public static Game game;

    // ************ fields (object variables) *************
    private DrawingPanel panel;
    private Graphics g;
    private int newX, newY, dir, steps;

    private Random rand;
    private Enemy[] array;
    private Player p;

    // this will be explained later:
    private MouseImplementation m;
    // ************* object methods (they lack the static keyword) ***************

    public Game() {
        // initialize ALL the above fields.
        panel = new DrawingPanel(WIDTH, HEIGHT);
        panel.setBackground(new Color(12, 201, 50));
        g = panel.getGraphics();

        p = new Player();

        m = new MouseImplementation(p);
        panel.addMouseListener(m);

        // INITIALIZE array AND rand
        rand = new Random();
        array = new Enemy[ENEMIES];
        for (int i = 0; i < ENEMIES; i++) {
            array[i] = new Enemy();
            array[i].teleport(rand.nextInt(HEIGHT / BLOCK) * BLOCK, rand.nextInt(HEIGHT / BLOCK) * BLOCK);
        }
    }

    // this code is complete
    public final void updatePanel() {
        clear(g);
        p.draw(g);
        for (int i = 0; i < ENEMIES; i++) {
            array[i].chase(p);
            array[i].draw(g);
            g.drawString("LIVES: " + LIVES, WIDTH / 2, BLOCK);
            // check player-enemy collisions here
            boolean collide = array[i].collide(p);
            if (collide) {
                LIVES = LIVES - 1;
                array[i].teleport(rand.nextInt(HEIGHT / BLOCK) * BLOCK, rand.nextInt(HEIGHT / BLOCK) * BLOCK);
            }
            for (int j = 0; j < ENEMIES; j++) {

                if (i != j) { // to be sure an enemy does not collide with itself
                    // check enemy-enemy collisions here
                    boolean collide2 = array[j].collide(array[i]);
                    if (collide2) {
                        array[i].teleport(rand.nextInt(HEIGHT / BLOCK) * BLOCK, rand.nextInt(HEIGHT / BLOCK) * BLOCK);
                        array[i].setSpeed(BLOCK / 4);
                    }
                }
            }
        }
    }

    public void go() {
        p.goToCenter();
        p.draw(g);
        panel.sleep(DELAY);
        clear(g);
        //loops until player in at top left
        while (true) {
            System.out.println(p.getSpeed());
            if (LIVES == 0) {
                p.setSpeed(0);
                g.setColor(new Color(12, 201, 50));
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.RED);
                g.drawString("GAME OVER", HEIGHT / 2, HEIGHT / 2);
                panel.sleep(DELAY * 500);
                System.exit(0);
            }
            if (p.getX() == 0 && p.getY() == 0) {
                p.setSpeed(0);
                g.setColor(new Color(12, 201, 50));
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.drawString("YOU WIN!", HEIGHT / 2, HEIGHT / 2);
                panel.sleep(DELAY * 500);
                System.exit(0);
            }
        }
        // CODE THE MAIN GAME LOOP HERE
        // SILMILAR TO THE MAIN GAME LOOP IN MOVEMENT
        // BE SURE TO CHECK FOR PLAYER-ENEMY COLLISIONS
        // AND ENEMY-ENEMY COLLISIONS
        // THEN IMPLEMENT CODE TO HANDLE THESE COLLISIONS
        // This is where your nested for loops go.
    }

    // ******** class methods (they have static keyword) *********
    public static void main(String[] args) {
        game = new Game();
        game.go();
    }

    public final void clear(Graphics g) {
        g.setColor(new Color(12, 201, 50));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        p.drawDPad(g);
    }
}
