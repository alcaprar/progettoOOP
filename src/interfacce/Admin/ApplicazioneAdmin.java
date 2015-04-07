package interfacce.Admin;

import javax.swing.*;

/**
 * Created by alessandro on 18/03/15.
 */
public class ApplicazioneAdmin extends JFrame {
    private JTabbedPane tabbedAdmin;
    private JPanel panel1;
    private GiocatoriAdmin giocatoriAdmin;
    private VotiAdmin votiAdmin1;

    public ApplicazioneAdmin() {
        super("JFantacalcio - Admin");


        setContentPane(panel1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    private void createUIComponents() {
        giocatoriAdmin = new GiocatoriAdmin(getFrame());
        votiAdmin1 = new VotiAdmin(getFrame());
    }

    private ApplicazioneAdmin getFrame() {
        return this;
    }



}
