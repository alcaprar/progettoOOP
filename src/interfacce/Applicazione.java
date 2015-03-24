package interfacce;

import classi.*;
import db.Mysql;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

    public Applicazione(final Squadra squadra) {
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

        refreshFormazione();

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
        //se ancora non sono stati inseriti i giocatori e si va sulla tab
        //della formazione, invia un avviso
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedpane = (JTabbedPane)e.getSource();
                int formazione = tabbedpane.indexOfTab("Formazione");
                int tab = tabbedpane.getSelectedIndex();
                if(formazione==tab && squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Ancora non sono stati inseriti i giocatori. Non potrai inviare la formazione.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

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

    public Info getInfoPanel(){ return infoPanel;}

    public void refreshFormazione(){
        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
        listaGiocatori = db.selectGiocatori(sqr);

        sqr.setGiocatori(listaGiocatori);

        formazionePanel.setSquadra(sqr);
        formazionePanel.refresh();

    }
}

