package utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Christian on 12/03/2015.
 */
public class BkgPanel extends JPanel{

    private Image img;

    public BkgPanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public BkgPanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
