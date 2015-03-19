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
import java.util.ArrayList;

/**
 * Created by alessandro on 12/03/15.
 */
public class Login extends JFrame {

    private JTextField usertxt;
    private JPasswordField passtxt;
    private JButton loginButton;
    private JButton registratiButton;
    private JComboBox comboBoxSquadre;
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

    public ArrayList<Squadra> listaSquadre = new ArrayList<Squadra>();

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
                         if (key == 10) controllaLogin();
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

                Squadra squadra = listaSquadre.get(comboBoxSquadre.getSelectedIndex());
                Applicazione app = new Applicazione(squadra);
            }
        });
        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaCampionato creaCampionato = new CreaCampionato(utente, getFrame());
                getFrame().setVisible(false);
            }
        });

    }

    public Login getFrame() {
        return this;
    }




    private void controllaLogin() {
        if (usertxt.getText().equals("admin") && utils.passwordString(passtxt.getPassword()).equals("admin")) {
            ApplicazioneAdmin admingui = new ApplicazioneAdmin();
            getFrame().dispose();
        } else {

            utente = new Persona(usertxt.getText(), utils.passwordString(passtxt.getPassword()));
            try {
                if (db.login(utente)) {
                    listaSquadre = db.selectSquadre(utente);
                    setComboBoxSquadre(listaSquadre);
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

    private void setComboBoxSquadre(ArrayList<Squadra> listaSquadre){
        DefaultComboBoxModel squadreModel = new DefaultComboBoxModel();
        for(Squadra squadra : listaSquadre){
            int ID = squadra.getID();
            String nome  = squadra.getNome()==null ? "NomeDaInserire" : squadra.getNome();
            String campionato = squadra.getCampionato().getNome();
            String presidente = squadra.getCampionato().getPresidente().getNickname().equals(utente.getNickname()) ? "(P)" : "";
            if(presidente.equals("(P)")) campionato = campionato.toUpperCase();
            squadreModel.addElement(ID+" - "+nome+" - "+campionato+presidente);
        }
        comboBoxSquadre.setModel(squadreModel);
        comboBoxSquadre.setToolTipText("<html>I campionati in cui sei presidente sono in maiuscolo<br> e indicati da (P).<br>Se compare NomeDaInserire vuol dire che è il primo login.</html>");
        getFrame().pack();

    }

}
