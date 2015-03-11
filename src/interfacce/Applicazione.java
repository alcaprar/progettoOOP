package interfacce;

import javax.swing.*;

/**
 * Created by alessandro on 11/03/15.
 */
public class Applicazione {
    final static  JFrame frame = new JFrame("Gestore Fantacalcio");
    private JPanel panel1;
    private JTabbedPane tabbedPane1;

    public Applicazione(){
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
