package mazetester;

import java.io.*;
import java.util.*;

public class MazeTester {

    private char[][] squares;
    private boolean[][] explored;
    private boolean animated;
    private int lastIsRow, lastIsCol;
    private static MazeTester m;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Enter a file name, such as \"maze2.txt\": ");
            fileName = scanner.next();
            File file = new File(fileName);
            if (file.exists()) {
                isValid = true;
            } else {
                System.out.println("Please enter a valid file name!\n");
            }
        }
        String s = "";
        Scanner mazeFile = new Scanner(new File(fileName));
        while (mazeFile.hasNextLine()) {
            s = s + mazeFile.nextLine() + "\n";
        }
        m = new MazeTester(s);
        isValid = false;
        while (!isValid) {
            System.out.print("Would you like the solution to be animated? Y/N: ");
            String selection = scanner.next();
            switch (selection) {
                case "Y":
                    m.setAnimated(true);
                    isValid = true;
                    break;
                case "N":
                    m.setAnimated(false);
                    isValid = true;
                    break;
                default:
                    System.out.println("Please make a valid selection!");
                    break;
            }
        }
        System.out.println("");
        System.out.println(m);
        solveMaze(1, 1, m);
    }

    private static void solveMaze(int col, int row, MazeTester m) {
        m.setExplored(col, row, true);
        if (m.isEscaped()) {
            System.exit(0);
        }
        System.out.println(m);
        {
            int newCol;
            int newRow;

//right
            if (col < m.getWidth() - 1) {
                newCol = col + 1;
                newRow = row;
                if (isOpen(newCol, newRow, m)) {
                    solveMaze(newCol, newRow, m);
                }
            }

//left
            if (col > 0) {
                newCol = col - 1;
                newRow = row;
                if (isOpen(newCol, newRow, m)) {
                    solveMaze(newCol, newRow, m);
                }
            }

//up
            if (row > 0) {
                newCol = col;
                newRow = row - 1;
                if (isOpen(newCol, newRow, m)) {
                    solveMaze(newCol, newRow, m);
                }
            }

//down
            if (row < m.getHeight() - 1) {
                newCol = col;
                newRow = row + 1;
                if (isOpen(newCol, newRow, m)) {
                    solveMaze(newCol, newRow, m);
                }
            }

        }
        m.setExplored(col, row, true);
        m.mark(col, row);
    }

    private static boolean isOpen(int newCol, int newRow, MazeTester m) {
        return !m.isMarked(newCol, newRow) && !m.isExplored(newCol, newRow) && !m.isWall(newCol, newRow);
    }

    public MazeTester(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");
        squares = new char[lines.length][lines[0].length()];
        explored = new boolean[lines.length][lines[0].length()];

        for (int row = 0; row < getHeight(); row++) {
            if (lines[row].length() != getWidth()) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was "
                        + lines[row].length() + " but should be " + getWidth() + ")");
            }

            for (int col = 0; col < getWidth(); col++) {
                squares[row][col] = lines[row].charAt(col);
            }
        }

        lastIsRow = -1;
        lastIsCol = -1;
        animated = true;
    }

    public int getHeight() {
        return squares.length;
    }

    public char getSquare(int col, int row) {
        checkIndexes(row, col);
        return squares[row][col];
    }

    public int getWidth() {
        return squares[0].length;
    }

    public boolean isEscaped() {
        // check left/right edges
        for (int row = 0; row < getHeight(); row++) {
            if (explored[row][0] || explored[row][getWidth() - 1]) {
                return true;
            }
        }

        // check top/bottom edges
        for (int col = 0; col < getWidth(); col++) {
            if (explored[0][col] || explored[getHeight() - 1][col]) {
                return true;
            }
        }
        return false;
    }

    public boolean isExplored(int col, int row) {
        checkIndexes(col, row);
        lastIsRow = row;
        lastIsCol = col;
        display();
        return explored[row][col];
    }

    public boolean isMarked(int col, int row) {
        checkIndexes(col, row);
        lastIsRow = row;
        lastIsCol = col;
        display();
        return squares[row][col] == '7';
    }

    public boolean isWall(int col, int row) {
        checkIndexes(col, row);
        lastIsRow = row;
        lastIsCol = col;
        display();
        return squares[row][col] == '0';
    }

    public void mark(int col, int row) {
        checkIndexes(col, row);
        display();
        squares[row][col] = '3';
    }

    public void setAnimated(boolean value) {
        animated = value;
    }

    public void setExplored(int col, int row, boolean value) {
        checkIndexes(col, row);
        explored[row][col] = value;
        display();
    }

    private void display() {
        if (animated) {
            System.out.println(this.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (row == lastIsRow && col == lastIsCol) {
                    result.append('?');
                } else if (squares[row][col] == '0') {
                    result.append('0');
                } else if (squares[row][col] == '3') {
                    result.append('3');
                } else if (explored[row][col]) {
                    result.append('7');
                } else {
                    result.append('1');
                }
            }
            result.append('\n');
        }
        maybePause();
        return result.toString();
    }

    private void checkIndexes(int col, int row) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            throw new IllegalArgumentException("illegal indexes: (" + row + ", " + col + ")");
        }
    }

    private void maybePause() {
        if (animated) {
            sleep(10);
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
        }
    }
}
