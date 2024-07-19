import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class ModalPanel extends JPanel {
    public ModalPanel() {}

    public ModalPanel(LayoutManager mgr) {
        super(mgr);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}
