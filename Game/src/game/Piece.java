package game;

import java.awt.*;

/**
 * @author joelk
 */
public class Piece {

    public static final int BLOCK = Game.BLOCK;
    public static final int HEIGHT = Game.HEIGHT;

    // *******************   fields   *********************
    // private fields, will be initialized in the constructor:
    private int x, y, speed;
    private Color color;

    // ****************** constructors ********************
    // CREATE A CONSTRUCTOR THAT TAKES NO PARAMETERS, AND SETS ALL FIELDS
    public Piece() {
        x = 0;
        y = 0;
        speed = Game.BLOCK;
        color = Color.BLUE;
    }

    // uses first constructor, then teleports
    public Piece(int initialX, int initialY) {
        // call constructor with no arguments
        this();
        this.teleport(initialX, initialY);

    }

    //********************* methods ***********************   
    // Centers the Player
    public void goToCenter() {
        teleport(BLOCK * Game.GRID / 2, BLOCK * Game.GRID / 2);
    }

    // accessor for x
    public int getX() {
        return x;
    }

    //Accessor for y
    public int getY() {
        return y;
    }

    //Accessor for speed
    public int getSpeed() {
        return speed;
    }

    //Accessor for color
    public Color getColor() {
        return color;
    }

    // mutator for x. Returns flase if the newX value is invalid
    public boolean setX(int newX) {
        if (0 <= newX && newX <= Game.WIDTH - BLOCK) {
            x = newX;
            return true;
        }
        return false;
    }

    //Mutator for y. Returns false if the newY value is invalid
    public boolean setY(int newY) {
        if (0 <= newY && newY <= Game.HEIGHT - BLOCK) {
            y = newY;
            return true;
        }
        return false;
    }

    //Mutator for speed. returns false if speed is invalid
    public boolean setSpeed(int newSpeed) {
        if (newSpeed <= Game.HEIGHT && newSpeed >= 0) {
            speed = newSpeed;
            return true;
        }
        return false;
    }

    //mutator for color
    public void setColor(Color newColor) {
        color = newColor;
    }

    public boolean teleport(int newX, int newY) {
        if (newX >= 0 && newX <= Game.WIDTH - BLOCK && newY >= 0 && newY <= Game.HEIGHT - BLOCK) {
            x = newX;
            y = newY;
            return true;
        }
        return false;
    }

    public boolean collide(Piece p) {
        // edit the line below, using this.getX(), this.getY(), p.getX(), and p.getY()
        if ((this.getX() - p.getX()) * (this.getX() - p.getX()) + (this.getY() - p.getY()) * (this.getY() - p.getY()) < (BLOCK - 2) * (BLOCK - 2)) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, BLOCK, BLOCK);
    }

    public void drawDPad(Graphics g) {
        g.setColor(new Color(239, 99, 0));
        g.fillRect(0, HEIGHT - 2 * BLOCK, BLOCK, BLOCK);
        g.fillRect(BLOCK, HEIGHT - 3 * BLOCK, BLOCK, BLOCK);
        g.fillRect(BLOCK, HEIGHT - BLOCK, BLOCK, BLOCK);
        g.fillRect(2 * BLOCK, HEIGHT - 2 * BLOCK, BLOCK, BLOCK);
        g.setColor(Color.BLACK);
        g.fillRect(BLOCK, HEIGHT - 2 * BLOCK, BLOCK, BLOCK);
    }

    public boolean moveRandomly(int dir) {
        switch (dir) {
            case 0: // up
                return this.left();
            case 1: // down
                return this.right();
            case 2: // left
                return this.up();
            case 3:
                return this.down();
        }
        System.out.println("!ERROR! moveRandomly says: dir should be between 0 and 3. Yours was " + dir);
        return false;

    }

    public boolean left() {
        if (x > 0) {
            x = x - speed;
            return true;
        }
        return false;
    }

    public boolean right() {
        if (x < Game.WIDTH - BLOCK) {
            x = x + speed;
            return true;
        }
        return false;
    }

    public boolean up() {
        if (y > 0) {
            y = y - speed;
            return true;
        }
        return false;
    }

    public boolean down() {
        if (y < Game.HEIGHT - BLOCK) {
            y = y + speed;
            return true;
        }
        return false;
    }
}
