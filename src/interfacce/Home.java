package interfacce;

import classi.Persona;
import classi.Squadra;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 11/03/15.
 */
public class Home extends JPanel {
    private JPanel panel1;
    private JButton inviaLaFormazioneButton;
    private JPanel ultimaGiornata;
    private JList list1;
    private JLabel nomeSquadra;
    private JLabel nomeUtente;

    private Squadra squadra;

    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
    }

    public void refresh(){
        nomeSquadra.setText(squadra.getNome());
        nomeUtente.setText(squadra.getProprietario().getNickname());
    }

}
