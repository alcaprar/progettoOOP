package interfacce;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alessandro on 11/03/15.
 */
public class Applicazione extends JFrame {
        private JPanel panel1;
    private JTabbedPane tabbedPane1;

    public Applicazione(){
        super("Gestore Fantacalcio");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pack();
        setVisible(true);
    }
}
