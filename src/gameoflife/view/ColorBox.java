package gameoflife.view;

import javax.swing.*;
import java.awt.*;

public class ColorBox extends JComboBox {
    private final Icon[] COLOR_ICONS;
    public final String LABELS[] = {
            "BLACK", "RED", "YELLOW", "GREEN", "MAGENTA", "CYAN"
    };
    public final Color COLORS[] = {
            Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN
    };
    public ColorBox(){
        super();

        COLOR_ICONS = new Icon[LABELS.length];
        final Integer[] INT_ARRAY = new Integer[LABELS.length];
        for (int i = 0; i < LABELS.length; i++) {
            INT_ARRAY[i] = i;
            COLOR_ICONS[i] = new ColorIcon(COLORS[i], new Dimension(100, 20));
        setModel(new DefaultComboBoxModel<>(INT_ARRAY));
        }
        setModel(new DefaultComboBoxModel<>(INT_ARRAY));
        setRenderer(new ComboBoxRenderer());
    }
    class ComboBoxRenderer extends JLabel implements ListCellRenderer {
        public ComboBoxRenderer() {setOpaque(true);}
        public Component getListCellRendererComponent(
                final JList list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }

            int selectedIndex = (Integer) value;
            setIcon(COLOR_ICONS[selectedIndex]);
            setText(LABELS[selectedIndex]);
            return this;
        }
    }
    class ColorIcon implements Icon {
        final private Color color;
        final private Dimension size;
        public ColorIcon(final Color color, final Dimension size) {
            this.color = color;
            this.size = size;
        }
        public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
            g.setColor(color);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
        public int getIconWidth() {return size.width;}
        public int getIconHeight() {return size.height;}
    }
}
