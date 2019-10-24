/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;

/**
 *
 * @author joelk
 */
public class Enemy extends Piece {

    public Enemy() {
        super();
        this.setSpeed(Piece.BLOCK / 8);
        this.setColor(new Color(187, 26, 29));
    }

    public Enemy(int newX, int newY) {
        super(newX, newY);
        super.setSpeed(Piece.BLOCK / 5);
        super.setColor(new Color(187, 26, 29));
    }

    public void chase(Piece p) {
        if (this.getX() > p.getX()) {
            this.left();
        } else if (this.getX() < p.getX()) {
            this.right();
        }
        if (this.getY() > p.getY()) {
            this.up();
        } else if (this.getY() < p.getY()) {
            this.down();
        }
    }
}
