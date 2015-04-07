package interfacce.Login;

import entità.*;
import db.Mysql;
import interfacce.Admin.ApplicazioneAdmin;
import interfacce.Applicazione.Applicazione;
import interfacce.Applicazione.CreaCampionato;
import interfacce.Applicazione.HomeStorico;
import utils.Utils;
import utils.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
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
    private JTextField squadratxt;
    private JLabel nomeutentetxt;
    private JComboBox comboBoxStorico;
    private JButton storicoButton;
    private JButton buttonLogout;

    //utente che fa il login
    private Persona utente;

    //serve per le funzioni utili
    public Utils utils = new Utils();

    final private Mysql db;

    public Login() {
        //titolo del frame
        super("JFantacalcio - Login");

        //instanzio il db
        db = new Mysql();
        //setta il contenuto principale del Jframe
        setContentPane(panel1);
        //"impacchetta" il Jframe (vuol dire che trova le dimensioni ideali in base ai componenti che ha dentro)
        pack();
        //centra il frame
        setLocationRelativeTo(null);
        //imposta l'operazione quando si chiude con la X
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        //rende non ridimensionabile il Jfram
        setResizable(false);
        //quando viene premuto il bottone di login parte la funzione per controllare le credenziali
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllaLogin();

            }
        });

        //se viene premuto enter sul bottone login parte la funzione per controllare le credenziali
        loginButton.addKeyListener
                (new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         int key = e.getKeyCode();
                         if (key == 10) controllaLogin();
                     }
                 }
                );


        //se viene premuto enter quando si sta scrivendo la pass parte la funzione per controllare le credenziali
        passtxt.addKeyListener
                (new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         int key = e.getKeyCode();
                         if (key == 10) controllaLogin();
                     }
                 }
                );
        //se viene cliccato registrati si crea il Jframe per la registrazione
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registra registragui = new Registra();

            }
        });

        //se viene premuto enter sul bottone registrati si crea il jframe per la registrazione
        registratiButton.addKeyListener
                (new KeyAdapter() {
                     public void keyPressed(KeyEvent e) {
                         int key = e.getKeyCode();
                         if (key == 10){ Registra registragui = new Registra();}

                     }
                 }
                );

        //quando viene cliccato il bottone per gestire le squadre vengono fatte alcune operazioni
        gestisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //prendo la squadra che è stata selezionata nel combobox
                Squadra squadra = utente.getPresidenza().get(comboBoxSquadre.getSelectedIndex());

                //se la squadra non ha il nome, cioè è il primo login,
                //viene mostrato un input dialog dove viene chiesto di inserire il nome
                if(squadra.getNome()==null){
                    squadra.setNome(JOptionPane.showInputDialog(getContentPane(), "Questo è il tuo primo login!\n Dai un nome alla tua squadra:"));
                }
                //se è stato il inserito il nome nel InputDialog:
                //-viene aggiornata il nome della squadra nel db
                //-viene chiuso il Jframe del login
                //-viene creato l'oggetto Applicazione passandogli la squadra
                if(squadra.getNome()!= null && squadra.getNome().length()>0){
                    db.aggiornaNomeSquadra(squadra);
                    if(utente.equals(squadra.getCampionato().getPresidente())) squadra.getProprietario().setPresidenteLega(true);
                    getFrame().dispose();
                    CaricamentoDati caricamento = new CaricamentoDati();
                    Applicazione app = new Applicazione(squadra, caricamento,getFrame());
                }
                //se non è stato inserito il nome nel dialog ne viene mostrato un altro che dice che non è stata inserita il nome
                else {
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(getContentPane(), "Non hai inserito il nome!",
                            "Nome mancante",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            options,
                            options[0]);

                }
            }
        });

        storicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Storico storico = utente.getListaStorico().get(comboBoxStorico.getSelectedIndex());

                getFrame().dispose();
                CaricamentoDati caricamento = new CaricamentoDati();
                HomeStorico AppStorico = new HomeStorico(storico,caricamento,getFrame());
            }
        });

        //se viene premuto il bottone crea cambionato viene creato il Jframe di CreaCampionato e viene nascono il Jframe di login
        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaCampionato creaCampionato = new CreaCampionato(utente, getFrame());
                getFrame().setVisible(false);
            }
        });

        buttonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mostraLogin();
            }
        });

    }

    /**
     * Ritorna this.
     * @return
     */
    public Login getFrame() {
        return this;
    }

    /**
     * Funzione che controlla il login.
     * Viene attivata al clic del bottone o al clic di enter quando il cursore è su passtxt
     */
    private void controllaLogin() {
        //creo un nuovo oggetto di persona con solo nome e pass
        utente = new Persona(usertxt.getText(), utils.passwordString(passtxt.getPassword()));

        //db.login restituisce true se le credenziali sono giuste
        if (db.login(utente)) {
            //se è l'admin controllo se è il primo login.
            if(utente.getNickname().equals("admin")){
                if(utente.getPassword().equals("password")){
                    JLabel jPassword = new JLabel("Questo è il tuo primo login. Cambia password.");
                    JPasswordField passwordField = new JPasswordField();
                    Object[] ob = {jPassword, passwordField};
                    int result = JOptionPane.showConfirmDialog(null, ob, "Password", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        String passwordValue = Utils.passwordString(passwordField.getPassword());
                        if(Validator.password(passwordValue)){
                            if(db.aggiornaPasswordAdmin(passwordValue)){
                                ApplicazioneAdmin admingui = new ApplicazioneAdmin();
                                getFrame().dispose();
                            }
                        }
                    }
                } else{
                    ApplicazioneAdmin admingui = new ApplicazioneAdmin();
                    getFrame().dispose();
                }
            } else {
                //se l'utente esiste scarico le squadre di cui è presidente con la funzione selectSquadre
                //che restituisce un arraylist di squadre
                utente.setPresidenza(db.selectSquadre(utente));
                //setto il combobox delle squadre dalla lista di squadre
                setComboBoxSquadre();
                //scarico la lista dei campionati a cui ha partecipato l'utente
                utente.setListaStorico(db.selectStorico(utente));
                //setto il combobox dello storico
                setComboBoxStorico();
                //aggiorno il label con il nome dell'utente
                nomeutentetxt.setText(utente.getNickname());
                //cambio la card da mostrare dato che il login è andato bene
                mostraSquadre();
            }
        }
    }


    /**
     * Setta il combobox a partire dalla lista di squadre.
     */
    private void setComboBoxSquadre(){
        //inizializzo il model per il combobox
        DefaultComboBoxModel squadreModel = new DefaultComboBoxModel();
        //scorro l'arraylist e popolo il model
        for(Squadra squadra : utente.getPresidenza()){
            int ID = squadra.getID();
            //se il nome della squadra è null, cioè non è stato ancora inserito il nome,
            //cioè non è stato fatto ancora il primo login, lo setto a NomeDaInserire giusto per farlo notare
            String nome  = squadra.getNome()==null ? "NomeDaInserire" : squadra.getNome();
            String campionato = squadra.getCampionato().getNome();
            //se l'utente loggato è il presidente del campionato aggiungo (P) per indicare che è presidente
            //e scrivo il nome del campionato in maiuscolo
            String presidente = squadra.getCampionato().getPresidente().equals(utente) ? "(P)" : "";
            if(presidente.equals("(P)")) campionato = campionato.toUpperCase();
            //aggiungo l'elemento al model
            squadreModel.addElement(ID+" - "+nome+" - "+campionato+presidente);
        }
        //setto il modello del combobox
        comboBoxSquadre.setModel(squadreModel);
        //aggiungo un tooltip alla combobox per spiegare cosa sono i campi che vengono visualizzati
        comboBoxSquadre.setToolTipText("<html>I campionati in cui sei presidente sono in maiuscolo<br> e indicati da (P).<br>Se compare NomeDaInserire vuol dire che è il primo login.</html>");
        getFrame().pack();

    }

    /**
     * Setta il combobox a partire dalla lista degli storici.
     */
    private void setComboBoxStorico(){
        //inizializzo il model per il combobox
        DefaultComboBoxModel storicoModel = new DefaultComboBoxModel();
        //scorro l'arraylist e popolo il model
        for(Storico storico : utente.getListaStorico()){
            int anno = storico.getAnno();
            String nome = storico.getNome();

            storicoModel.addElement(nome + " - "+String.valueOf(anno));
        }

        comboBoxStorico.setModel(storicoModel);
    }

    /**
     * Aggiorna la pagina.
     * Viene utilizzata dopo che è stato creato un campionato per farlo
     * vedere nella lista.
     */
    public void refresh() {
        //se l'utente esiste scarico le squadre di cui è presidente con la funzione selectSquadre
        //che restituisce un arraylist di squadre
        utente.setPresidenza(db.selectSquadre(utente));
        //setto il combobox delle squadre dalla lista di squadre
        setComboBoxSquadre();

        getFrame().pack();

    }

    public void mostraLogin(){
        CardLayout c1 = (CardLayout) (panel1.getLayout());
        c1.show(panel1, "Login");
    }

    public void mostraSquadre(){
        CardLayout c1 = (CardLayout) (panel1.getLayout());
        c1.show(panel1, "login2");
    }

}
