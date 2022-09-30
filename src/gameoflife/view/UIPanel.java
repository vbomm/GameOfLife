package gameoflife.view;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel for the user interface.
 */
public class UIPanel extends JPanel {
    JButton populateButton;
    JLabel setFPSLabel;
    JSpinner fpsSpinner;
    JLabel realFPSLabel;
    JButton paintButton;
    ColorBox colorBox;
    JButton recordButton;

    /**
     * Initializes new <code>UIPanel</code>.
     */
    public UIPanel() {
        setLayout(new FlowLayout());
        populateButton = new JButton();
        SpinnerNumberModel fpsModel = new SpinnerNumberModel(8, 1, 90, 1);
        setFPSLabel = new JLabel();
        fpsSpinner = new JSpinner(fpsModel);
        realFPSLabel = new JLabel();
        colorBox = new ColorBox();
        paintButton = new JButton();
        recordButton = new JButton();

        this.add(populateButton);
        this.add(setFPSLabel);
        this.add(fpsSpinner);
        this.add(realFPSLabel);
        this.add(colorBox);
        this.add(paintButton);
        this.add(recordButton);
    }
}
