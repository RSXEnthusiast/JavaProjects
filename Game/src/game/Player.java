package game;

import java.awt.*;
import java.lang.Math;

/**
 *
 * @author joelk
 */
public class Player extends Piece {

    public Player() {
        super();

        // Sets speed AFTER the super classes' constructor (which also sets speed)
        this.setSpeed(BLOCK/2);
        // this.speed is private, and cannot be accessed from outside of Piece's code

        // Sets color AFTER the super classes' constructor (which also sets color)
        this.setColor(new Color(66, 119, 255));
    }
}
