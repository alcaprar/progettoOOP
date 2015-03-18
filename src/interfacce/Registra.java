package interfacce;

import classi.*;
import db.*;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by alessandro on 13/03/15.
 */
public class Registra extends JFrame {
    private JTextField nicktxt;
    private JPasswordField passtxt;
    private JTextField nometxt;
    private JTextField cognometxt;
    private JTextField emailtxt;
    private JButton registratiButton;
    private JPanel panel1;
    private JLabel infolbl;
    public Persona utente;
    public Utils utils = new Utils();

    public Registra() {
        //titolo del frame
        super("Registrati - Gestore Fantacalcio");
        final Mysql db = new Mysql();
        setContentPane(panel1);

        pack();

        infolbl.setVisible(false);

        //centra il frame
        setLocationRelativeTo(null);

        setVisible(true);


        setResizable(false);
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utente = creaUtente();

                try {
                    if (db.registra(utente)) registraTrue();

                } catch (SQLException se) {
                    if (se.getErrorCode() == 1062) {

                        nicknameRegistrato();
                    }

                } catch (Exception ce) {


                }
            }
        });
    }

    public Persona creaUtente() {
        return new Persona(nicktxt.getText(), utils.passwordString(passtxt.getPassword()), nometxt.getText(), cognometxt.getText(), emailtxt.getText());
    }

    public void registraTrue() {
        Object[] options = {"OK"};
        int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Registrazione effettuata con successo!",
                "Risposta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (succesDialog == 0 || succesDialog == -1) dispose();
    }

    public void nicknameRegistrato() {
        Object[] options = {"OK"};
        int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Nickname già registrato!",
                "Nickname esistente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

    }

}
