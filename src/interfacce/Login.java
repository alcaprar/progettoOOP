package interfacce;

import classi.*;
import db.Mysql;
import utils.Utils;

import javax.swing.*;
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

    //è l'utente che fa il login
    private Persona utente;

    //è la lista delle squadre che possiede l'utente loggato
    private ArrayList<Squadra> listaSquadre = new ArrayList<Squadra>();

    //serve per le funzioni utili
    public Utils utils = new Utils();

    final private Mysql db;

    public Login() {
        //titolo del frame
        super("Login - Gestore fantacalcio");

        //instanzio il db
        db = new Mysql();
        //setta il contenuto principale del Jframe
        setContentPane(panel1);
        //"impacchetta" il Jframe (vuol dire che trova le dimensioni ideali in base ai componenti che ha dentro)
        pack();
        //info label sarà visibile se l'utente sbaglia le credenziali
        infolbl.setVisible(false);
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
                Squadra squadra = listaSquadre.get(comboBoxSquadre.getSelectedIndex());

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
                    Applicazione app = new Applicazione(squadra);
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
        //se viene premuto il bottone crea cambionato viene creato il Jframe di CreaCampionato e viene nascono il Jframe di login
        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaCampionato creaCampionato = new CreaCampionato(utente, getFrame());
                getFrame().setVisible(false);
            }
        });

    }

    //ritorna Login (serve dentro gli actionListener perchè non si può usare this direttamente)
    public Login getFrame() {
        return this;
    }

    //funzione che controlla il login. viene attivata al clic del bottone o al clic di enter quando il cursore è su passtxt
    private void controllaLogin() {
        //controlla se è l'admin che ha fatto il login
        //-se si apre l'applicazione per l'admin e chiude il frame di login
        if (usertxt.getText().equals("admin") && utils.passwordString(passtxt.getPassword()).equals("admin")) {
            ApplicazioneAdmin admingui = new ApplicazioneAdmin();
            getFrame().dispose();
        }
        //se non è l'admin deve controllare nel db se le credenziali inserite esistono
        else {
            //creo un nuovo oggetto di persona con solo nome e pass
            utente = new Persona(usertxt.getText(), utils.passwordString(passtxt.getPassword()));
            //con un try catch controllo le credenziali
            //try catch serve per prendere le eccezioni sql
            try {
                //db.login restituisce true se le credenziali sono giuste
                if (db.login(utente)) {
                    //se l'utente esiste scarico le squadre di cui è presidente con la funzione selectSquadre
                    //che restituisce un arraylist di squadre
                    listaSquadre = db.selectSquadre(utente);
                    //setto il combobox delle squadre dalla lista di squadre
                    setComboBoxSquadre(listaSquadre);
                    //aggiorno il label con il nome dell'utente
                    nomeutentetxt.setText(utente.getNickname());
                    //cambio la card da mostrare dato che il login è andato bene
                    CardLayout c1 = (CardLayout) (panel1.getLayout());
                    c1.show(panel1, "login2");
                }
                //se il login va male rendo visibile il label delle info(dice che nick e pass sono errati)
                else infolbl.setVisible(true);
            }
            //catturo le eccezioni sql e le gestisco
            catch (SQLException se) {
                //se ci sono state eccezioni mostro un Dialog con il codice degli errori
                if(se.getErrorCode()==0){
                    JOptionPane.showMessageDialog(getContentPane(),"Ci sono dei problemi con la tua connessione internet!","Manca la connessione",JOptionPane.INFORMATION_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(getContentPane(),"Ci sono dei problemi con il database.\nCodice errore database: "+se.getErrorCode()+"\nSe il problema persiste contattare l'amministratore.","Problemi con il db",JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (ClassNotFoundException ce) {

            }
        }
    }
    //setta il combo box a partire dalla lista di squadre
    private void setComboBoxSquadre(ArrayList<Squadra> listaSquadre){
        //inizializzo il model per il combobox
        DefaultComboBoxModel squadreModel = new DefaultComboBoxModel();
        //scorro l'arraylist e popolo il model
        for(Squadra squadra : listaSquadre){
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

    public void refresh(){
        //se l'utente esiste scarico le squadre di cui è presidente con la funzione selectSquadre
        //che restituisce un arraylist di squadre
        listaSquadre = db.selectSquadre(utente);
        //setto il combobox delle squadre dalla lista di squadre
        setComboBoxSquadre(listaSquadre);

    }

}
