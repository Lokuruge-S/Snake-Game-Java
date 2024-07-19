import javax.swing.JComponent;

import java.awt.Dimension;

public abstract class SnakeSeg extends JComponent{
    public Point pos;

    public abstract void setDirection(int direction);

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }
}
