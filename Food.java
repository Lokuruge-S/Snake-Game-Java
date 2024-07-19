import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Food extends JComponent {
    private static boolean pulse = true;
    private static long start = 0L;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int diameter;
        if (pulse) {
            diameter = (Math.min(getWidth(), getHeight()) * 3) / 4;
        } else {
            diameter = (Math.min(getWidth(), getHeight()) * 2) / 3;
        }

        long end = System.nanoTime();
        if ((end - start) / 1000000 > 200) {
            pulse = !pulse;
            start = end;
        }
        
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
    
        g.setColor(new Color(194, 53, 43));
        g.drawOval(x, y, diameter, diameter);
        g.setColor(new Color(235, 64, 52));
        g.fillOval(x, y, diameter, diameter);
        g.dispose();
    }
}
