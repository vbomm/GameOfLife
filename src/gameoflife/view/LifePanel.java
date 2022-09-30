package gameoflife.view;

import javax.swing.*;
import java.awt.*;

/**
 * Displays Game Of Life, requests new board via listener after current is displayed.
 * A timer is used to set the amount of displayed boards per second.
 * Different types of cells are displayed in different colours.
 */
public class LifePanel extends JPanel {
    private int[][] board;
    private final int width;
    private final int height;
    private final int lifeDisplaySize;

    /**
     * Creates new LifePanel.
     * Sets how many frames are displayed per second, calls for a new board after each displayed frame.
     *
     * @param width           length of x-axis of board
     * @param height          length of y-axis of board
     * @param lifeDisplaySize size of the displayed cell in pixels
     */
    public LifePanel(int width, int height, int lifeDisplaySize) {
        this.board = new int[height][width];
        this.width = width;
        this.height = height;
        this.lifeDisplaySize = lifeDisplaySize;

        this.setPreferredSize(new Dimension(width * lifeDisplaySize, height * lifeDisplaySize));
        this.setBackground(Color.BLACK);
    }

    /**
     * Receives 2D-Array
     *
     * @param board 2D-Array which gets displayed
     */
    public void updateBoard(int[][] board) {
        this.board = board;
    }

    /**
     * Displays cells in the size provided by field, display colour depends on the value in the 2D-Array, where a value
     * of 0 equals a dead cell and gets the colour black, every value above means a live cell and gets assigned a
     * different colour according to the value.
     *
     * @param g the <code>Graphics</code> object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                Color color = switch (board[y][x]) {
                    case 1 -> Color.RED;
                    case 2 -> Color.YELLOW;
                    case 3 -> Color.GREEN;
                    case 4 -> Color.MAGENTA;
                    case 5 -> Color.CYAN;
                    default -> Color.BLACK;
                };

                g2D.setColor(color);
                g2D.fillRect(x * lifeDisplaySize, y * lifeDisplaySize, lifeDisplaySize, lifeDisplaySize);
            }
    }

    /**
     * Returns width of the array used for the cells.
     *
     * @return width of the board
     */
    public int getBoardWith() {
        return width;
    }

    /**
     * Returns height of the array used for the cells.
     *
     * @return height of the board
     */
    public int getBoardHeight() {
        return height;
    }
}
