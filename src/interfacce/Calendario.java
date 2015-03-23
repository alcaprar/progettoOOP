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

    private ArrayList<TabellaGiornata> giornata = new ArrayList<TabellaGiornata>();

    public void setSquadra(Squadra sqr){
        this.squadra=sqr;
    }

    public void refresh(){
        legalbl.setText(squadra.getCampionato().getNome());
        setCalendario();
    }

    private void setCalendario(){
        int i=0;
        for(Giornata giorn:squadra.getCampionato().getCalendario()){
            TabellaGiornata giornatai = new TabellaGiornata(giorn);
            giornata.add(i,giornatai);
            giornata.get(i).setVisible(true);
            calendarioPanel.add(giornata.get(i));
            i++;
        }
    }
}
