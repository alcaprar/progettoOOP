package interfacce;

import classi.*;
import db.Mysql;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

/**
 * Created by alessandro on 12/03/15.
 */
public class Login extends JFrame {

    private JTextField usertxt;
    private JPasswordField passtxt;
    private JButton loginButton;
    private JButton registratiButton;
    private JComboBox comboBox1;
    private JButton gestisciButton;
    private JComboBox pubbliciBox;
    private JButton iscrivitiButton;
    private JButton creaButton;
    private JPanel panel1;
    private JPanel login1;
    private JPanel login2;
    private JLabel infolbl;
    private JTextField squadratxt;
    private JLabel nomeutentetxt;

    public Persona utente;

    public Utils utils = new Utils();

    final Mysql db;

    public Login() {
        //titolo del frame
        super("Login - Gestore fantacalcio");

        db = new Mysql();



        setContentPane(panel1);

        pack();

        infolbl.setVisible(false);

        //centra il frame
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        setResizable(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllaLogin();

            }
        });


        //evento al clic del tasto enter
        passtxt.addKeyListener
                (new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         int key = e.getKeyCode();
                         if(key==10) controllaLogin();
                     }
                 }
                );
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registra registragui = new Registra();

            }
        });


        gestisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Applicazione app = new Applicazione();
            }
        });
        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaCampionato creaCampionato = new CreaCampionato(utente, getFrame());
                getFrame().setVisible(false);
            }
        });
        iscrivitiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!squadratxt.getText().equals("")) {
                    String campionato = (String) pubbliciBox.getSelectedItem();
                    String nomeSquadra = squadratxt.getText();
                    if (db.iscriviti(utente, campionato, nomeSquadra)) {
                        Object[] options = {"OK"};
                        int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Iscrizione effettuata con successo!",
                                "Risposta",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);

                    } else {
                        Object[] options = {"OK"};
                        int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Ci sono stati degli errori nel!",
                                "Risposta",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                    }
                }
            }
        });
    }

    public Login getFrame() {
        return this;
    }


    private void createUIComponents() {
        final Mysql db = new Mysql();
        String[] campionati = db.selectCampionatiPubblici();
        DefaultComboBoxModel pubbliciModel = new DefaultComboBoxModel(campionati);
        pubbliciBox = new JComboBox();
        pubbliciBox.setModel(pubbliciModel);
    }

    private void controllaLogin() {
        if (usertxt.getText().equals("admin")&& utils.passwordString(passtxt.getPassword()).equals("admin")) {
            ApplicazioneAdmin admingui = new ApplicazioneAdmin();
            getFrame().dispose();
        } else {

            utente = new Persona(usertxt.getText(), utils.passwordString(passtxt.getPassword()));
            try {
                if (db.login(utente)) {
                    CardLayout c1 = (CardLayout) (panel1.getLayout());
                    c1.show(panel1, "login2");
                } else infolbl.setVisible(true);
            } catch (SQLException se) {
                Object[] options = {"OK"};
                int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Ci sono dei problemi con il database.\n Codice errore MySQL:" + se.getErrorCode(),
                        "Problemi db",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[0]);
                System.out.print(se.getErrorCode());

            } catch (ClassNotFoundException ce) {

            }
        }
    }

}
