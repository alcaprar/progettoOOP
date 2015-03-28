package interfacce;

import classi.*;
import db.Mysql;
import org.joda.time.DateTime;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Date;

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

    private Squadra sqr;

    final Mysql db = new Mysql();

    public Applicazione(final Squadra squadra) {
        super("Gestore Fantacalcio");

        this.sqr = squadra;

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

        //scarico la lista dei giocatori della squadra loggata
        sqr.setGiocatori(db.selectGiocatori(sqr));

        //aggiorno il pannello formazione
        //refreshFormazione();

        //setto in tutti i pannelli il riferimento a squadra
        homePanel.setSquadra(sqr);
        formazionePanel.setSquadra(sqr);
        classificaPanel.setSquadre(sqr);
        calendarioPanel.setSquadra(sqr);
        squadrePanel.setSquadre(sqr);
        infoPanel.setSquadra(sqr);
        gestioneLegaPanel.setSquadra(sqr);
        //setto i riferimento a calendario, home e classifica che servono
        //per fare il refresh dopo che è stata calcolata la giornata
        gestioneLegaPanel.setCalendario(calendarioPanel);
        gestioneLegaPanel.setHome(homePanel);
        gestioneLegaPanel.setClassifica(classificaPanel);

        //fa partire il countdown per l'inserimento della formazione
        homePanel.startCountDown();

        //aggiorno tutti i pannelli con i dati scaricati
        homePanel.refresh();
        formazionePanel.refresh();
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

        //se l'utente non è il presidente di lega tolgo gestione lega
        if(!squadra.getProprietario().isPresidenteLega()){
            tabbedPane1.remove(tabbedPane1.indexOfTab("Gestione Lega"));
        }

        //quando viene aperta la tab formazione bisogna fare alcuni controlli
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedpane = (JTabbedPane)e.getSource();
                int formazione = tabbedpane.indexOfTab("Formazione");
                int tab = tabbedpane.getSelectedIndex();
                //se ancora non sono state create le rose invia un popup di avviso
                if(formazione==tab && squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Ancora non sono stati inseriti i giocatori. Non potrai inviare la formazione.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                //se è già stata inviata la formazione per questa giornata invia un popup di avviso
                else if(formazione==tab && sqr.isFormazioneInserita()){
                    JOptionPane.showMessageDialog(null, "Hai già inviato la formazione per questa partita.\nSe invii un'altra formazione, questa sostituirà quella vecchia.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                //se è scaduto il tempo per inserire la formazione mostra un avviso e cambia la tab
                else if(formazione==tab && new DateTime().isAfter(homePanel.getProssimaGiornata())){
                    System.out.println("tempo passato");
                    JOptionPane.showMessageDialog(null, "Non è più possibile inserire la formazione perchè è scaduto il tempo.","Tempo scaduto", JOptionPane.INFORMATION_MESSAGE);
                    tabbedpane.setSelectedIndex(0);
                }
            }
        });

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 650);
        setLocationRelativeTo(null);
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


}

