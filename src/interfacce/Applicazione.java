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
    private Home homePanel;
    private JPanel pFormazione;

    private Squadra sqr;

    public Applicazione(Squadra squadra) {
        super("Gestore Fantacalcio");

        sqr = squadra;

        homePanel = new Home(squadra);

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {

    }


}
