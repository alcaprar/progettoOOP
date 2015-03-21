package interfacce;

import classi.*;
import db.Mysql;

import javax.swing.*;
import java.awt.*;
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

    private Squadra sqr;

    final Mysql db = new Mysql();

    public Applicazione(Squadra squadra) {
        super("Gestore Fantacalcio");

        sqr = squadra;

        //scarico la classifica e la inserisco nel campionato
        ArrayList<classi.Classifica> classifica = new ArrayList<classi.Classifica>();
        classifica = db.selectClassifica(sqr.getCampionato());
        sqr.getCampionato().setClassifica(classifica);

        homePanel.setSquadre(sqr);
        classificaPanel.setSquadre(sqr);

        homePanel.refresh();
        classificaPanel.refresh();

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

}
