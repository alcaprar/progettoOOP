package interfacce.Login;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;

/**
 * Created by alessandro on 05/04/15.
 */
public class CaricamentoDati extends JFrame {
    public CaricamentoDati(){
        super("Caricamento");
        JLabel caricamento = new JLabel("Download dati dal server.");
        add(caricamento);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
