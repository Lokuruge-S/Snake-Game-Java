import java.awt.Color;
import java.awt.Graphics;

public class SnakeBody extends SnakeSeg{

    public void setDirection(int direction) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(8, 196, 149));

        g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        g.dispose();
    }
}
