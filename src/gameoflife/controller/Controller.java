package gameoflife.controller;

import gameoflife.model.Model;
import gameoflife.view.View;

import javax.swing.*;

/**
 * Interacts between <code>Model</code> and <code>View</code>.
 */
public class Controller {
    final private Model model;
    final private View view;
    int cellDisplaySize;
    boolean isMousePressed;
    private double lastTime;
    private int fps;
    private int simulationFPS;
    private int paintFPS;

    private boolean isPaintMode;
    private boolean isRecording;

    /**
     * Creates a new <code>Controller</code>, initializes <code>Model</code> and <code>View</code> before calling the model to start the calculation.
     *
     * @param windowTitle
     * @param width
     * @param height
     * @param cellDisplaySize
     */
    public Controller(String windowTitle, int width, int height, int cellDisplaySize) {
        lastTime = System.nanoTime();
        paintFPS = 60;
        simulationFPS = 8;
        isMousePressed = false;
        isPaintMode = false;
        isRecording = false;
        this.cellDisplaySize = cellDisplaySize;
        model = new Model(this, width, height);
        view = new View(this, windowTitle, width, height, cellDisplaySize);
        initView();
        model.compute();
    }

    /**
     * Run <code>View</code> in AWT Event dispatcher thread and show the frame.
     */
    private void initView() {
        view.setText();
        SwingUtilities.invokeLater(view::show);
    }

    /**
     * Gets called when a new board is available and then sends this board to <code>View</code>.
     *
     * @param board 2D-Array
     */
    public void updateBoard(int[][] board) {
        view.updateBoard(board);
    }

    /**
     * Calls the <code>Model</code> to start the computation of a new board.
     */
    public void requestBoard() {
        if (isPaintMode())
            view.updateBoard(model.getBoard());
        else {
            if (isRecording)
                view.saveImage("PNG", "recorded/", System.nanoTime() + ".png");
            fps = (int) Math.round(1000000000 / -(lastTime - (lastTime = System.nanoTime())));
            view.setReaFPSLabel(fps);
            model.compute();
        }
    }

    /**
     * Gets called the paint button was pressed.
     * When in paint mode, it switches to simulation and vice versa.
     * After that, it tells <code>View</code> to update the texts of the buttons.
     */
    public void paintButtonPressed() {
        isPaintMode = !isPaintMode;
        view.setText();
    }

    /**
     * Gets called when a mouse button was pressed.
     * Boolean is set to true for so other methods can act according if mouse button is pressed or not.
     * Calls other method to create a cell at given coordinates.
     *
     * @param x x-coordinate of the mouse cursor
     * @param y y-coordinate of the mouse cursor
     */
    public void mousePressed(int x, int y) {
        isMousePressed = true;
        setCell(x, y);
    }

    /**
     * Sets boolean to false for other methods to know that the mouse button is currently not pressed.
     */
    public void mouseReleased() {
        isMousePressed = false;
    }

    /**
     * If the mouse cursor gets dragged while mouse button is pressed, call other method to create new cell on current mouse coordinates.
     *
     * @param x x-coordinate of the mouse cursor
     * @param y y-coordinate of the mouse cursor
     */
    public void mouseDragged(int x, int y) {
        if (isMousePressed)
            setCell(x, y);
    }

    /**
     * If is in paint mode, call the model to set the cell on the given coordinates to the type that is selected in the UI.
     * Coordinate of the mouse cursor gets divided by the display size of a cell to get the coordinate in the 2D-Array.
     *
     * @param x x-coordinate of the mouse cursor
     * @param y y-coordinate of the mouse cursor
     */
    private void setCell(int x, int y) {
        if (isPaintMode())
            model.setCell(x / cellDisplaySize, y / cellDisplaySize, view.getSelectedPaintType());
    }

    /**
     * Changes the goal of the simulation FPS, but not lower than 1.
     */
    public void changeFPS(int value) {
        simulationFPS = value;
        if (simulationFPS < 1) simulationFPS = 1;
        view.updateSimulationFPS();
    }

    /**
     * Method to return the currently set FPS for the simulation.
     *
     * @return the currently set FPS.
     */
    public int getSimulationFPS() {
        return simulationFPS;
    }

    /**
     * Method to return the currently set FPS for the paint mode.
     *
     * @return
     */
    public int getPaintFPS() {
        return paintFPS;
    }

    /**
     * Calls <code>Model</code> to set a few dead cells to live.
     * Amount currently is a magic number.
     */
    public void populateButtonPressed() {
        int amount = view.getWidthCells() * view.getHeightCells() / 20;

        model.populate(amount);
    }

    /**
     * Returns the boolean that states if program is in paint mode.
     *
     * @return if program is in paint mode
     */
    public boolean isPaintMode() {
        return isPaintMode;
    }

    /**
     * If is not recording, start recording mode and vice versa.
     * Change text of record button accordingly.
     */
    public void recordButtonPressed() {
        isRecording = !isRecording;
        view.setRecordButtonText();
    }

    /**
     * Returns the boolean that states if program is in recorc mode.
     *
     * @return if program is in record mode
     */
    public boolean isRecording() {
        return isRecording;
    }
}
