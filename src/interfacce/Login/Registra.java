package interfacce.Login;

import classi.*;
import db.*;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 *
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Registra extends JFrame {
    private JTextField nicktxt;
    private JPasswordField passtxt;
    private JTextField nometxt;
    private JTextField cognometxt;
    private JTextField emailtxt;
    private JButton registratiButton;
    private JPanel panel1;
    private JButton annullaButton;
    private JLabel emailIcon;
    private JLabel cognomeIcon;
    private JLabel nomeIcon;
    private JLabel passwordIcon;
    private JLabel nicknameIcon;
    private Persona utente;

    /**
     * Costruttore del frame per la registrazione.
     */
    public Registra() {
        //titolo del frame
        super("JFantacalcio - Registra");
        final Mysql db = new Mysql();
        setContentPane(panel1);

        pack();

        //centra il frame
        setLocationRelativeTo(null);

        setVisible(true);


        setResizable(false);
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (campiRegistrazioneValidi()) {
                        utente = creaUtente();
                        if (db.registra(utente)) {
                            JOptionPane.showMessageDialog(getContentPane(), "Registrazione effettuata con successo", "Registrazione OK", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                    }

                } catch (SQLException se) {
                    if (se.getErrorCode() == 1062) {
                        JOptionPane.showMessageDialog(getContentPane(), "Il nickname che hai inserito è già registrato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ce) {
                    ce.printStackTrace();
                }
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().dispose();
            }
        });
    }

    /**
     * Crea l'utente a partire dai valori nelle textfield.
     * @return utente
     */
    public Persona creaUtente() {
        return new Persona(nicktxt.getText(), Utils.passwordString(passtxt.getPassword()), nometxt.getText(), cognometxt.getText(), emailtxt.getText());
    }

    /**
     * Controlla se i campi inseriti sono validi.
     * @return true se i campi sono validi, false altrimenti.
     */
    private boolean campiRegistrazioneValidi(){
        boolean flag = true;

        if(!Validator.nickname(nicktxt.getText())){
            flag=false;
        } else if(!Validator.password(Utils.passwordString(passtxt.getPassword()))){
            flag = false;
        } else if(!Validator.email(emailtxt.getText())){
            flag= false;
        } else if(!Validator.nome(nometxt.getText())){
            flag=false;
        } else if(!Validator.cognome(cognometxt.getText())){
            flag=false;
        }
        return flag;
    }

    private void setInfoIcon(){
        UIDefaults defaults = UIManager.getDefaults( );
        Icon icon = defaults.getIcon("OptionPane.informationIcon");

        nicknameIcon.setIcon(icon);
        nicknameIcon.setToolTipText("Il nickname deve essere tra 5 e 20 caratteri.");

        passwordIcon.setIcon(icon);
        passwordIcon.setToolTipText("La password deve essere tra 5 e 20 caratteri.");

        nomeIcon.setIcon(icon);
        nomeIcon.setToolTipText("Il nome deve essere tra 2 e 20 caratteri.");

        cognomeIcon.setIcon(icon);
        cognomeIcon.setToolTipText("Il cognome deve essere tra 2 e 20 caratteri.");

        emailIcon.setIcon(icon);
        emailIcon.setToolTipText("La mail deve essere una mail valida e al massimo di 50 caratteri.");
    }

    /**Ritorna this.
     * @return this
     */
    private Registra getFrame(){
        return this;
    }

}
