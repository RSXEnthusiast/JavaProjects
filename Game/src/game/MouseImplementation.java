package game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author ccarlson
 */
public class MouseImplementation implements MouseInputListener {

    public static final int BLOCK = Game.BLOCK;
    public static final int HEIGHT = Game.HEIGHT;

    // *********** fields **********
    Player p;
    // only the player will respond to the mouse

    // constructor
    public MouseImplementation(Player newPlayer) {
        // sets the only field
        p = newPlayer;
        
    }

    // *********** methods *************
    @Override
    public void mouseClicked(MouseEvent e) {
        // Point is a java class that you can look up.
        // Google: "Java point" for more info
        Point point = e.getPoint();
        // This is simply an integer that corresponds to a mouse button press.
        // Look at the print output for the values for each button.
        int button = e.getButton();

        // REACT TO THE POINT USING IF STATEMENTS AND CALLING METHODS of p
        if (0 <= point.x && point.x <= BLOCK && HEIGHT - 2 * BLOCK <= point.y && point.y <= HEIGHT - BLOCK) {
            p.left();
        } else if (2 * BLOCK <= point.x && point.x <= 3 * BLOCK && HEIGHT - 2 * BLOCK <= point.y && point.y <= HEIGHT - BLOCK) {
            p.right();
        } else if (BLOCK <= point.x && point.x <= 2 * BLOCK && HEIGHT - BLOCK <= point.y && point.y <= HEIGHT) {
            p.down();
        } else if (BLOCK <= point.x && point.x <= 2 * BLOCK && HEIGHT - 3 * BLOCK <= point.y && point.y <= HEIGHT - 2 * BLOCK) {
            p.up();
        }
        if (p.getSpeed()!=0){
        Game.game.updatePanel();
        }
    }

    // you do NOT need to implement the following methods.
    @Override
    public void mousePressed(MouseEvent e) {
        ;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ;
    }

}
