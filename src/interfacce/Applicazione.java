package interfacce;

import classi.*;
import db.Mysql;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by alessandro on 11/03/15.
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

    private Squadra sqr;

    final Mysql db = new Mysql();

    public Applicazione(Squadra squadra) {
        super("Gestore Fantacalcio");

        sqr = squadra;

        //scarico la classifica e la inserisco nel campionato
        ArrayList<classi.Classifica> classifica = new ArrayList<classi.Classifica>();
        classifica = db.selectClassifica(sqr.getCampionato());
        sqr.getCampionato().setClassifica(classifica);

        //scarico il calendario e lo inserisco nel campionato
        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();
        listaGiornate = db.selectGiornate(sqr.getCampionato());
        sqr.getCampionato().setCalendario(listaGiornate);


        //se sono stati inseriti i giocatori nell squadre
        //scarico la lista dei giocatori della squadra appena loggata
        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
        listaGiocatori = db.selectGiocatori(sqr);

        sqr.setGiocatori(listaGiocatori);

        formazionePanel.setSquadra(sqr);
        formazionePanel.refresh();

        homePanel.setSquadre(sqr);
        classificaPanel.setSquadre(sqr);
        calendarioPanel.setSquadra(sqr);

        homePanel.refresh();
        classificaPanel.refresh();
        calendarioPanel.refresh();

        infoPanel.setSquadra(sqr);
        infoPanel.refresh();

        //se l'utente loggato è il presidente, il tipo di asta è offline e i giocatori sono da inserire popolo le tabelle
        //del pannello gestione giocatori
        //se no rimuovo il pannello
        if(squadra.getProprietario().isPresidenteLega() && squadra.getCampionato().isGiocatoriDaInserire() && !squadra.getCampionato().isAstaLive()){
            gestioneGiocatoriPanel.setSquadra(sqr);
            gestioneGiocatoriPanel.refresh();
        } else{
            tabbedPane1.remove(tabbedPane1.indexOfTab("Gestione Giocatori"));

        }

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTabbedPane getTabbedPane(){
        return this.tabbedPane1;
    }

    private void createUIComponents() {
        gestioneGiocatoriPanel = new GestioneGiocatori(getFrame());
        homePanel = new Home(getFrame());
    }

    private Applicazione getFrame(){
        return this;
    }

    public Formazione getFormazionePanel(){
        return formazionePanel;
    }
}

