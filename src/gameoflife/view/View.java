package gameoflife.view;

import gameoflife.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Creates JFrame and <code>lifePanel</code>.
 * Sends relevant user actions to <code>Controller</code>.
 * Receives orders from <code>Controller</code>.
 */
public class View implements MouseListener, MouseMotionListener {
    private final Controller controller;
    private Locale locale;
    private final String windowTitle;
    private final JFrame frame;
    private final LifePanel lifePanel;
    private final UIPanel uiPanel;
    private final int width;
    private final int height;
    private final int cellDisplaySize;
    private Timer timer;

    /**
     * Creates a new View.
     *
     * @param windowTitle     Title of the window.
     * @param width           length of x-axis of 2D-Array
     * @param height          length of y-axis of 2D-Array
     * @param cellDisplaySize size of the displayed cell in pixels
     */
    /**
     * Creates a new <code>View</code>.
     * Calls method to create frame.
     * Initializes <code>UIPanel</code> and <code>LifePanel</code> and adds mouse listeners.
     * Starts updating of <code>LifePanel</code> based on timer.
     *
     * @param controller      <code>Controller</code> object
     * @param windowTitle     title of the Window
     * @param width           width of used 2D-Array to display cells
     * @param height          height of used 2D-Array to display cells
     * @param cellDisplaySize width/length of displayed cell
     */
    public View(Controller controller, String windowTitle, int width, int height, int cellDisplaySize) {
        this.controller = controller;
        locale = new Locale();
        this.windowTitle = windowTitle;
        this.width = width;
        this.height = height;
        this.cellDisplaySize = cellDisplaySize;
        frame = createFrame();
        lifePanel = new LifePanel(width, height, cellDisplaySize);
        lifePanel.addMouseListener(this);
        lifePanel.addMouseMotionListener(this);

        uiPanel = new UIPanel();
        frame.add(lifePanel, BorderLayout.CENTER);
        frame.add(uiPanel, BorderLayout.NORTH);
        frame.pack();

        setupListeners();

        timer = new Timer(1000 / controller.getSimulationFPS(), e -> {
            lifePanel.repaint();
           controller.requestBoard();
        });
        timer.start();
    }

    /**
     * Sets up listeners that report to <code>Controller</code>.
     */
    private void setupListeners() {
        uiPanel.paintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.paintButtonPressed();
            }});
        uiPanel.fpsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                controller.changeFPS((int) uiPanel.fpsSpinner.getValue());
            }
        });

        uiPanel.populateButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 controller.populateButtonPressed();
             }
         });

        uiPanel.recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.recordButtonPressed();
            }
        });
    }

    /**
     * Forwards board to Panel.
     *
     * @param board 2D-Array containing the state of the Game Of Life
     */
    public void updateBoard(int[][] board) {
        lifePanel.updateBoard(board);
    }

    /**
     * Set frame to visible.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Creates a frame with the title and size attributes provided by fields.
     *
     * @return the frame created
     */
    private JFrame createFrame() {
        JFrame f = new JFrame();

        f.setLayout(new BorderLayout(0, 0));
        f.setTitle(windowTitle);
        f.setSize(width * cellDisplaySize, height * cellDisplaySize);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return f;
    }

    /**
     * Used to set and update the used texts of the buttons and labels.
     * Maybe split up later to only update text of specific button/label.
     */
    public void setText() {
        setRecordButtonText();
        uiPanel.populateButton.setText(locale.getPopulate());

        if (controller.isPaintMode()) {
            timer.setDelay(1000 / controller.getPaintFPS());
            uiPanel.paintButton.setText(locale.getContinueSimulation());
            uiPanel.setFPSLabel.setText(String.valueOf(locale.getSetFPS()));
            uiPanel.fpsSpinner.setVisible(false);
            uiPanel.setFPSLabel.setVisible(false);
            uiPanel.realFPSLabel.setVisible(false);
            uiPanel.colorBox.setVisible(true);
        } else {
            timer.setDelay(1000 / controller.getSimulationFPS());
            uiPanel.paintButton.setText(locale.getStartPainting());
            uiPanel.setFPSLabel.setText(String.valueOf(locale.getSetFPS()));
            uiPanel.setFPSLabel.setVisible(true);
            uiPanel.fpsSpinner.setVisible(true);
            uiPanel.realFPSLabel.setVisible(true);
            uiPanel.colorBox.setVisible(false);
        }
    }

    /**
     * Sets the text of the record button based on <code>Locale</code>.
     */
    public void setRecordButtonText() {
        if (controller.isRecording())
            uiPanel.recordButton.setText(locale.getStopRecording());
        else
            uiPanel.recordButton.setText(locale.getStartRecording());
    }

    /**
     * Changes the timer to display frames.
     */
    public void updateSimulationFPS() {
        timer.setDelay(1000 / controller.getSimulationFPS());
    }

    /**
     * Sets the text of the current FPS label based on <code>Locale</code>.
     */
    public void setReaFPSLabel(int value) {
        uiPanel.realFPSLabel.setText("(" + locale.getCurrent() + " " +  String.valueOf(value) + ")");
    }

    /**
     * Calls <code>UIPanel</code> to get the selected cell type.
     *
     * @return index of the selected cell type
     */
    public int getSelectedPaintType() {
        return uiPanel.colorBox.getSelectedIndex();
    }


    /**
     * Saves the current state of the displayed <code>LifePanel</code>.
     *
     * @param format   format of the image
     * @param path     path of the image
     * @param filename name of the file
     */
    public void saveImage(String format, String path, String filename){
        BufferedImage img = null;
        try {
            img = new Robot().createScreenCapture(lifePanel.bounds());
        } catch (AWTException e) {
            e.printStackTrace();
        }
        if (img == null) return;

        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();

            Graphics2D graphics2D = img.createGraphics();
        lifePanel.paint(graphics2D);
        try {
            ImageIO.write(img,format, new File(path + filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Tells <code>Controller</code> coordinates where mouse button was pressed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        controller.mousePressed(e.getX(), e.getY());
    }

    /**
     * Tells <code>Controller</code> when mouse button was released.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        controller.mouseReleased();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Tells <code>Controller</code> where mouse was dragged.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        controller.mouseDragged(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Gets width of board used in <code>LifePanel</code>.
     *
     * @return width of the 2D-Array
     */
    public int getWidthCells() {
        return lifePanel.getBoardWith();
    }

    /**
     * Gets height of board used in <code>LifePanel</code>.
     *
     * @return height of the 2D-Array
     */
    public int getHeightCells() {
        return lifePanel.getBoardHeight();
    }
}
