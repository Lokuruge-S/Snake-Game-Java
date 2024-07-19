import java.awt.Color;
import java.awt.Graphics;

public class SnakeHead extends SnakeSeg {
    private int direction;

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        super.paintComponent(g);
        int[] xpoints;
        int[] ypoints;

        switch (this.direction){ 
            case Snake.NORTH:
                xpoints = new int[] {1, width - 1, width / 2};
                ypoints = new int[] {height - 1, height - 1, 0};
                break;

            case Snake.EAST:
                xpoints = new int[] {1, 1, width - 1};
                ypoints = new int[] {1, height - 1, height / 2};
                break;

            case Snake.SOUTH:
                xpoints = new int[] {1, width - 1, width / 2};
                ypoints = new int[] {1, 1, height - 1 };
                break;

            case Snake.WEST:
                xpoints = new int[] {width - 1, width - 1, 1};
                ypoints = new int[] {1, height - 1, height / 2};
                break;
            
            default:
                xpoints = new int[] {1, width - 1, width / 2};
                ypoints = new int[] {height - 1, height - 1, 1};
                break;
        }
        
        g.setColor(new Color(8, 180, 149));
        g.fillPolygon(xpoints, ypoints, 3);
        g.setColor(new Color(0, 0, 0));
        g.drawPolygon(xpoints, ypoints, 3);
        g.dispose();
    }
}
