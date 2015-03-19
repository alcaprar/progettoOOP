package interfacce;

import classi.Squadra;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alessandro on 11/03/15.
 */
public class Applicazione extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel homePanel;
    private JPanel pFormazione;
    private Formazione pForm;

    private Squadra sqr;

    public Applicazione() {
        super("Gestore Fantacalcio");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setSize(800, 600);
        setVisible(true);
    }

    /*private void createUIComponents() {
        pForm = new Formazione(sqr);
    }*/

}
