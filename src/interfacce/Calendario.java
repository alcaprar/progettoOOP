package interfacce;

import classi.Squadra;

import javax.swing.*;

/**
 * Created by alessandro on 22/03/15.
 */
public class Calendario extends JPanel {
    private JLabel legalbl;
    private JPanel mainPanel;

    private Squadra squadra;

    public void setSquadra(Squadra sqr){
        this.squadra=sqr;
    }

    public void refresh(){
        legalbl.setText(squadra.getCampionato().getNome());
    }

}
