package interfacce;

import classi.Giornata;
import classi.Squadra;
import db.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by alessandro on 22/03/15.
 */
public class Calendario extends JPanel {
    private JLabel legalbl;
    private JPanel mainPanel;
    private JPanel calendarioPanel;

    private Squadra squadra;

    private Applicazione applicazione;

    private ArrayList<TabellaGiornata> giornata = new ArrayList<TabellaGiornata>();

    public void setSquadra(Squadra sqr){
        this.squadra=sqr;
    }

    public void setApplicazione(Applicazione app){
        this.applicazione = app;
    }

    public void refresh(){
        legalbl.setText(squadra.getCampionato().getNome());
        setCalendario();
    }

    private void setCalendario(){
        calendarioPanel.setLayout(new BoxLayout(calendarioPanel,BoxLayout.Y_AXIS));
        int j=0;
        while(j<squadra.getCampionato().getCalendario().size()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            for(int i=0;i<3 && j<squadra.getCampionato().getCalendario().size();i++){
                TabellaGiornata giornatai = new TabellaGiornata(squadra.getCampionato().getCalendario().get(j),squadra.getCampionato().getProssimaGiornata());
                giornata.add(j,giornatai);
                panel.add(giornata.get(j));
                j++;
            }
            calendarioPanel.add(panel);
        }
    }
}
