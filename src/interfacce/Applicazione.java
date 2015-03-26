package interfacce;

import classi.*;
import db.Mysql;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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
    private JPanel squadreTab;
    private Squadre squadrePanel;
    private GestioneLega gestioneLegaPanel;
    private JPanel gestioneLegaTab;

    private Squadra sqr;

    final Mysql db = new Mysql();

    private boolean formazioneInserita;

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

        //scarico la lista dei giocatori delle squadre del mio campionato
        setListaGiocatori();

        //scarico le formazioni di tutte le giornate
        for(Giornata giorn :sqr.getCampionato().getCalendario()){
            if(giorn.getNumGiornata()<=sqr.getCampionato().getProssimaGiornata()) {
                for (Partita part : giorn.getPartite()) {
                    classi.Formazione formCasa = new classi.Formazione(db.selectFormazioni(part.getID(), part.getCasa()));
                    part.getCasa().setFormazione(formCasa);
                    classi.Formazione formOspite = new classi.Formazione(db.selectFormazioni(part.getID(), part.getOspite()));
                    part.getOspite().setFormazione(formOspite);
                }
            }
        }


        //controllo se è già stata inserita la formazione
        sqr.setFormazioneInserita(db.selectFormazioneInserita(sqr));

        //scarico la lista degli avvisi
        sqr.getCampionato().setListaAvvisi(db.selectAvvisi(sqr));

        //aggiorno il pannello formazione
        refreshFormazione();

        homePanel.setSquadre(sqr);
        classificaPanel.setSquadre(sqr);
        calendarioPanel.setSquadra(sqr);
        squadrePanel.setSquadre(sqr);
        infoPanel.setSquadra(sqr);
        gestioneLegaPanel.setSquadra(sqr);


        homePanel.refresh();
        classificaPanel.refresh();
        calendarioPanel.refresh();
        squadrePanel.refresh();
        infoPanel.refresh();
        gestioneLegaPanel.refresh();

        //se l'utente loggato è il presidente, il tipo di asta è offline e i giocatori sono da inserire popolo le tabelle
        //del pannello gestione giocatori
        //se no rimuovo il pannello
        if(squadra.getProprietario().isPresidenteLega() && squadra.getCampionato().isGiocatoriDaInserire() && !squadra.getCampionato().isAstaLive()){
            gestioneGiocatoriPanel.setSquadra(sqr);
            gestioneGiocatoriPanel.refresh();
        } else{
            tabbedPane1.remove(tabbedPane1.indexOfTab("Gestione Giocatori"));

        }

        //se l'utente non è il presidente di lega tolto gestione lega
        if(!squadra.getProprietario().isPresidenteLega()){
            tabbedPane1.remove(tabbedPane1.indexOfTab("Gestione Lega"));
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
                } else if(formazione==tab && sqr.isFormazioneInserita()){
                    JOptionPane.showMessageDialog(null, "Hai già inviato la formazione per questa partita.\nSe invii un'altra formazione, questa sostituirà quella vecchia.", "Info", JOptionPane.INFORMATION_MESSAGE);
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

    private void setListaGiocatori(){
        for(Squadra squadre: sqr.getCampionato().getListaSquadrePartecipanti()){
            squadre.setGiocatori(db.selectGiocatori(squadre));
        }
    }

   /* private void setFormazioni(){
        for(Squadra squadre : sqr.getCampionato().getListaSquadrePartecipanti()){
            squadre.setFormazione(db.selectFormazioni(sqr.getCampionato()));
        }
    }*/

}

