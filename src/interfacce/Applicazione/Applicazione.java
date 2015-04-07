package interfacce.Applicazione;

import entità.*;
import db.Mysql;
import interfacce.Login.CaricamentoDati;
import interfacce.Login.Login;
import org.joda.time.DateTime;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Applicazione principale del gestore.
 * La classe estende un Jframe.
 * All'interno c'è un JTabbedPane.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Applicazione extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private Home homePanel;
    private JPanel pFormazione;
    private Classifica classificaPanel;
    private GestioneGiocatori gestioneGiocatoriPanel;
    private JPanel classificaTab;
    private JPanel gestioneGiocatoriTab;
    private Formazione formazionePanel;
    private JPanel calendarioTab;
    private Calendario calendarioPanel;
    private JPanel infoTab;
    private Info infoPanel;
    private JPanel squadreTab;
    private Squadre squadrePanel;
    private GestioneLega gestioneLegaPanel;
    private JPanel gestioneLegaTab;

    private JMenuBar menubar;

    private Squadra sqr;

    private Login loginForm;

    final Mysql db = new Mysql();

    public Applicazione(final Squadra squadra, CaricamentoDati caricamento, Login login) {
        super("JFantacalcio");

        this.sqr = squadra;
        this.loginForm = login;

        //scarico la classifica e la inserisco nel campionato
        ArrayList<entità.Classifica> classifica = new ArrayList<entità.Classifica>();
        classifica = db.selectClassifica(sqr.getCampionato());
        sqr.getCampionato().setClassifica(classifica);

        //scarico il calendario e lo inserisco nel campionato
        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();
        listaGiornate = db.selectGiornate(sqr.getCampionato());
        sqr.getCampionato().setCalendario(listaGiornate);

        //scarico la lista dei giocatori delle squadre del mio campionato
        setListaGiocatori();

        //scarico le formazioni di tutte le giornate
        for(Giornata giorn :sqr.getCampionato().getCalendario()){
            if(giorn.getNumGiornata()<=sqr.getCampionato().getProssimaGiornata()) {
                for (Partita part : giorn.getPartite()) {
                    ArrayList<Voto> formCasa = db.selectFormazioni(part.getID(), part.getFormCasa().getSquadra());
                    part.getFormCasa().setListaGiocatori(formCasa);
                    ArrayList<Voto> formOspite = db.selectFormazioni(part.getID(), part.getFormOspite().getSquadra());
                    part.getFormOspite().setListaGiocatori(formOspite);
                }
            }
        }

        //controllo se è già stata inserita la formazione
        sqr.setFormazioneInserita(db.selectFormazioneInserita(sqr));

        //scarico la lista degli avvisi
        sqr.getCampionato().setListaAvvisi(db.selectAvvisi(sqr));

        //scarico la lista dei messaggi se è il presidente di lega
        if(sqr.getProprietario().isPresidenteLega()){
            sqr.getCampionato().setListaMessaggi(db.selectMessaggi(sqr));
        }

        //scarico la lista dei giocatori della squadra loggata
        sqr.setGiocatori(db.selectGiocatori(sqr));

        //setto in tutti i pannelli il riferimento a squadra
        homePanel.setSquadra(sqr);
        formazionePanel.setSquadra(sqr);
        classificaPanel.setSquadra(sqr);
        calendarioPanel.setSquadra(sqr);
        squadrePanel.setSquadre(sqr);
        infoPanel.setSquadra(sqr);


        //fa partire il countdown per l'inserimento della formazione
        homePanel.startCountDown();

        //aggiorno tutti i pannelli con i dati scaricati
        homePanel.refresh();
        formazionePanel.refresh();
        classificaPanel.refresh();
        calendarioPanel.refresh();
        squadrePanel.refresh();
        infoPanel.refresh();

        //se l'utente loggato è il presidente, il tipo di asta è offline e i giocatori sono da inserire popolo le tabelle
        //del pannello gestione giocatori
        //se no rimuovo il pannello
        if(squadra.getProprietario().isPresidenteLega() &&  !squadra.getCampionato().isAstaLive()) {
            gestioneGiocatoriPanel.setSquadra(sqr);
            if (squadra.getCampionato().isGiocatoriDaInserire()) {
                //le rose sono ancora da inserire
                gestioneGiocatoriPanel.refresh();
            } else {
                //giocatori gia inseriti
                //è possibile modificare le singole rose
                gestioneGiocatoriPanel.refreshGiaInseriti();
            }
        }else if(squadra.getProprietario().isPresidenteLega() &&  squadra.getCampionato().isAstaLive()){
            if (squadra.getCampionato().isGiocatoriDaInserire()) {
                //le rose sono ancora da inserire
                gestioneGiocatoriPanel.refreshAstaLive();
            } else {
                //giocatori gia inseriti
                //è possibile modificare le singole rose
                gestioneGiocatoriPanel.refreshGiaInseriti();
            }
        }else {
            tabbedPane1.remove(tabbedPane1.indexOfTab("Gestione Giocatori"));
        }

        //se l'utente non è il presidente di lega tolgo gestione lega
        if(!squadra.getProprietario().isPresidenteLega()){
            tabbedPane1.remove(tabbedPane1.indexOfTab("Gestione Lega"));
        } else{
            gestioneLegaPanel.setSquadra(sqr);
            //setto i riferimento a calendario, home e classifica che servono
            //per fare il refresh dopo che è stata calcolata la giornata
            gestioneLegaPanel.setApplicazione(getFrame());

            gestioneLegaPanel.refresh();
        }

        //quando viene aperta la tab formazione bisogna fare alcuni controlli
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedpane = (JTabbedPane) e.getSource();
                int formazione = tabbedpane.indexOfTab("Formazione");
                int tab = tabbedpane.getSelectedIndex();
                //se ancora non sono state create le rose invia un popup di avviso
                if (formazione == tab && squadra.getCampionato().isGiocatoriDaInserire()) {
                    JOptionPane.showMessageDialog(formazionePanel, "Ancora non sono stati inseriti i giocatori. Non potrai inviare la formazione.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                //se è scaduto il tempo per inserire la formazione mostra un avviso e cambia la tab
                else if (formazione == tab && new DateTime().isAfter(homePanel.getProssimaGiornata())) {
                    JOptionPane.showMessageDialog(formazionePanel, "Non è più possibile inserire la formazione perchè è scaduto il tempo.", "Tempo scaduto", JOptionPane.INFORMATION_MESSAGE);
                    tabbedpane.setSelectedIndex(0);
                }
                //se è già stata inviata la formazione per questa giornata invia un popup di avviso
                else if (formazione == tab && sqr.isFormazioneInserita()) {
                    JOptionPane.showMessageDialog(formazionePanel, "Hai già inviato la formazione per questa partita.\nSe invii un'altra formazione, questa sostituirà quella vecchia.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        setJMenu();

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        caricamento.dispose();
        setVisible(true);
    }

    /**
     * IMPORTANTE: NON RIMUOVERE!
     * Funzione del UI designer di IntelliJ
     * IMPORTANTE: NON RIMUOVERE!
     */
    private void createUIComponents() {
        gestioneGiocatoriPanel = new GestioneGiocatori(getFrame());
        homePanel = new Home(getFrame());
    }

    /**
     * Restituisce il tabbedPane dell'applicazione principale.
     * Serve per spostarsi tra le tab da un'altra classe.
     * @return JTabbedPane
     */
    public JTabbedPane getTabbedPane(){
        return this.tabbedPane1;
    }

    /**
     * Restituisce l'oggetto Applicazione.
     * Serve quando bisogna passarlo all'interno degli Actionlistener;
     * @return Applicazione
     */
    private Applicazione getFrame(){
        return this;
    }

    /**
     * Restituisce il pannello della formazione.
     * @return Formazione
     */
    public Formazione getFormazionePanel(){
        return formazionePanel;
    }

    /**
     * Restituisce il pannello delle informazioni del profilo e della lega.
     * @return Info
     */
    public Info getInfoPanel(){ return infoPanel;}

    /**
     * Scarico la lista dei giocatori per ogni squadra del campionato.
     * Serve per il pannello squadre dove vengono mostrati tutti i giocatori.
     */
    private void setListaGiocatori(){
        for(Squadra squadre: sqr.getCampionato().getListaSquadrePartecipanti()){
            squadre.setGiocatori(db.selectGiocatori(squadre));
        }
    }

    private void setJMenu(){
        menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.getAccessibleContext().setAccessibleDescription("File");

        menubar.add(file);

        JMenuItem cambiaSquadra = new JMenuItem("Cambia squadra");
        cambiaSquadra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().dispose();
                loginForm.setVisible(true);
            }
        });
        file.add(cambiaSquadra);

        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().dispose();
                loginForm.setVisible(true);
                loginForm.mostraLogin();
            }
        });
        file.add(logout);

        JMenuItem esci = new JMenuItem("Esci");
        esci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        file.add(esci);

        JMenu info = new JMenu("?");
        info.getAccessibleContext().setAccessibleDescription("Info");

        menubar.add(info);

        JMenuItem versione = new JMenuItem("Versione");
        versione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showOptionDialog(null, "JFantacalcio\nVersione 1.0\nSviluppatori:\nAlessandro Caprarelli\nGiacomo Grilli\nChristian Manfredi",
                        "Versione", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, new Object[] {},
                        null);
            }
        });
        info.add(versione);

        getFrame().setJMenuBar(menubar);
    }

    private void close(){
        int risultato = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler chiudere?", "Exit", JOptionPane.OK_CANCEL_OPTION);
        if (risultato == JOptionPane.OK_OPTION) {
            getFrame().dispose();
            System.exit(1);
        }
    }

    public Calendario getCalendarioPanel(){
        return calendarioPanel;
    }

    public Classifica getClassificaPanel(){
        return classificaPanel;
    }

    public Home getHomePanel(){
        return homePanel;
    }

    public Login getLoginForm(){
        return loginForm;
    }


}

