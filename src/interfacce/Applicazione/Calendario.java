package interfacce.Applicazione;

import entità.Squadra;

import javax.swing.*;

/**
 * Pagina del calendario del campionato.
 * Estende un Jpanel.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Calendario extends JPanel {
    private JLabel legalbl;
    private JPanel mainPanel;
    private JPanel calendarioPanel;

    private Squadra squadra;

    /**
     * Setta il riferimento alla squadra loggato.
     * Viene utilizzato da Applicazione.
     * @param squadra
     */
    public void setSquadra(Squadra squadra){
        this.squadra=squadra;
    }

    /**
     * Aggiorna il panel con i dati della squadra e del campionato.
     * Viene chiamata da Applicazione dopo che è stato settato il riferimento
     * interno a squadra.
     */
    public void refresh(){
        legalbl.setText(squadra.getCampionato().getNome());
        setCalendario();
    }

    /**
     * Setta il panel del calendario. Prende una giornata alla volta dal calendario
     * e per ognuna crea un oggetto di tipo TabellaGiornata.
     * @see TabellaGiornata
     */
    private void setCalendario(){
        calendarioPanel.setLayout(new BoxLayout(calendarioPanel,BoxLayout.Y_AXIS));
        int j=0;
        while(j<squadra.getCampionato().getCalendario().size()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            for(int i=0;i<3 && j<squadra.getCampionato().getCalendario().size();i++){
                TabellaGiornata giornataIesima = new TabellaGiornata(squadra.getCampionato().getCalendario().get(j),squadra.getCampionato().getProssimaGiornata(),squadra.getCampionato().getBonusCasa());
                panel.add(giornataIesima);
                j++;
            }
            calendarioPanel.add(panel);
        }
    }
}
