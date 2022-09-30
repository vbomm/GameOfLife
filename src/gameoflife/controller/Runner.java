package gameoflife.controller;

/**
 * Project is based on the Model–view–controller (MVC) pattern, <code>Controller</code> gets initialized here.
 */
public class Runner {

    /**
     * Initializes <code>Controller</code>.
     * The window title, the size of the board and the display size of a cell gets defined here.
     *
     * @param args an array of command-line arguments for the application, not used
     */
    public static void main(String[] args) {
        String windowTitle = "Game Of Life";
        int width = 140;
        int height = 100;
        int cellDisplaySize = 10;

        new Controller(windowTitle, width, height, cellDisplaySize);
    }
}