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

    public Applicazione(Squadra squadra) {
        super("Gestore Fantacalcio");
        if(squadra.getNome()==null){
            Object[] options = {"OK"};
            int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Questo è il tuo primo login!\n Dai un nome alla tua squadra:",
                    "Nome Squadra",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
        }

        System.out.print(squadra.getNome());
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*private void createUIComponents() {
        pForm = new Formazione(sqr);
    }*/

}
