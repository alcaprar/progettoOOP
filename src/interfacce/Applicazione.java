package interfacce;

import classi.Persona;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alessandro on 11/03/15.
 */
public class Applicazione extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel homePanel;
    Persona utente;

    public Applicazione() {
        super("Gestore Fantacalcio");

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setVisible(true);
        setLocationRelativeTo(null);
    }


}
