package gameoflife.model;

/**
 * The Logic of Game of Life. Different cell types are possible, the amount is set in the constructor.
 */
public class Simulation {
    private final int width;
    private final int height;
    private int[][] board;
    final private int cellTypes;

    /**
     * This constructor gets assigned the parameters which determine the size of the board, which is an 2D-Array.
     * It sets the value for the available cell types and calls a method to populate the board with cells.
     *
     * @param width  length of x-axis of board
     * @param height length of y-axis of board
     */
    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        cellTypes = 5;
        board = new int[height][width];
    }

    /**
     * Populates the 2D-Array with live cells.
     * If given amount exceeds the amount of dead cells, latter is used.
     *
     * @param amount
     */
    public void populate(int amount) {
        int freeSpace = countDeadCells();

        // can't create more life than free space exists
        int toCreate = Math.min(amount, freeSpace);

        while (toCreate > 0) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            if (board[y][x] == 0) {
                board[y][x] = (int) (Math.random() * (cellTypes + 1));
                toCreate--;
            }
        }
    }

    /**
     * Counts the amount of dead cells and returns the value.
     *
     * @return amount of dead cells
     */
    private int countDeadCells() {
        int counter = 0;

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (board[y][x] == 0)
                    counter++;

        return counter;
    }

    /**
     * Creates new board and fills it with the next generation of board.
     * Calls method for each cell to determine its future generation.
     */
    public void tick() {
        int[][] newBoard = new int[height][width];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                newBoard[y][x] = getNextGeneration(board[y][x], x, y);

        board = newBoard;
    }

    /**
     * Checks neighbouring cells of cell, creates next generation of cell according to rules of Game Of Life.
     * 1. Live cell with two or three live neighbour cells survives
     * 2. Dead cell with three live neighbours becomes a live cell. If there are more than one type of cells, the new
     *    cell becomes the type which surrounds it the most. Equal amount results in a random selection.
     * 3. If none of the above states, cell dies/stays dead.
     *
     * @param type  0 means empty, everything above is a living cell type
     * @param x     x-coordinate of cell
     * @param y     y-coordinate of cell
     * @return      next generation of cell
     */
    private int getNextGeneration(int type, int x, int y) {
        //determine amount of neighbours
        int neighbours = 0;
        int[] neighbourType = new int[cellTypes + 1];
        int mostCommonNeighbour = 0;
        for (int checkY =  0; checkY < 3; checkY++)
            for (int checkX = 0; checkX < 3; checkX++) {
                int currentX = coordinateWithoutBorder(checkX + x - 1, width);
                int currentY = coordinateWithoutBorder(checkY + y - 1, height);
                int current = board[currentY][currentX];

                // if the cell is alive, count neighbours of the same type
                // if the cell dead (type 0), count all types of cells as neighbours
                if (type > 0 && current != 0 || type == 0 && current > 0)
                    //ignore itself
                    if (checkX != 1 || checkY != 1) {
                        neighbours++;
                        neighbourType[current]++;
                        if (neighbourType[current] > neighbourType[mostCommonNeighbour]
                            || neighbourType[current] == neighbourType[mostCommonNeighbour] && isLucky())
                            mostCommonNeighbour = current;
                    }
            }

        //determine next generation
        int nextGeneration;
        if (board[y][x] == 0 && neighbours == 3) {
            //new life
            nextGeneration = mostCommonNeighbour;
        } else if (board[y][x] > 0 && neighbours != 2 && neighbours != 3) {
            //dead
            nextGeneration = 0;
        } else
            //no change
            nextGeneration = board[y][x];

        return nextGeneration;
    }

    /**
     * 50% chance to return yes.
     *
     * @return boolean of chance creation
     */
    private boolean isLucky() {
       return Math.random() < 0.5;
    }

    /**
     * If <code>coordinate</code> is smaller than zero or equal/bigger than <code>boardSize</code> it needs to be
     * transformed to a value on the opposite site of the 2D-Array.
     * This makes it possible for cells to travel as well as checking for neighbours through the borders.
     *
     * @param coordinate current coordinate
     * @param boardSize  either represents height or width of the 2D-Array
     * @return           transformed coordinate
     */
    private int coordinateWithoutBorder(int coordinate, int boardSize) {
        if (coordinate < 0) coordinate = boardSize + coordinate % boardSize;
        if (coordinate >= boardSize) coordinate %= boardSize;
  
        return coordinate;
    }

    /**
     * Calls method to generate next generation of board and returns it.
     *
     * @return next generation of 2D-Array, each value represents a cell
     */
    public int[][] compute() {
        tick();
        return board;
    }


    /**
     * Returns 2D-Array that contains the cells.
     *
     * @return 2D-Array
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Sets cell at given coordinates to given type.
     * Coordinates get checked if they fit in the 2D-Array.
     * Type doesn't get checked for validity.
     *
     * @param x    x-coordinate of the cell
     * @param y    y-coordinate of the cell
     * @param type type of the cell
     */
    public void setCell(int x, int y, int type) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;

        board[y][x] = type;
    }
}