package gameoflife.model;

import gameoflife.controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Calls <code>Simulation</code> to generate a new board, sends the new board to listeners when finished.
 */
public class Model {
    private Controller controller;
    private final Simulation simulation;

    /**
     * Class constructor forwarding axis parameters of the board to Simulation-Class.
     *
     * @param height y-axis of board
     * @param width  x-axis of board
     */
    public Model(Controller controller, int width, int height) {
        this.controller = controller;
        simulation = new Simulation(width, height);
    }

    /**
     * Starts a thread to generate new board, then sends it to listeners.
     */
    public void compute() {
        CompletableFuture<int[][]> future = CompletableFuture.supplyAsync(simulation::compute);
        future.thenAcceptAsync(result -> {
            controller.updateBoard(result);
        });
    }

    /**
     * Gets the 2D-Array from <code>Simulation</code> and returns it.
     * Is used for paint mode, where computation isn't needed.
     *
     * @return 2D-Array
     */
    public int[][] getBoard() {
        return simulation.getBoard();
    }

    /**
     * Tells the <code>Simulation</code> to populate the board with a given amount of live cells.
     *
     * @param amount
     */
    public void populate(int amount) {
        simulation.populate(amount);
    }

    /**
     * Tells the <code>Simulation</code> to set a specific cell to a given type.
     *
     * @param x    x-coordinate of the cell
     * @param y    y-coordinate of the cell
     * @param type type of the cell
     */
    public void setCell(int x, int y, int type) {
        simulation.setCell(x, y, type);
    }
}
