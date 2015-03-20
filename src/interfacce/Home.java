package interfacce;

import classi.*;
import com.sun.org.apache.bcel.internal.classfile.LineNumberTable;
import utils.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
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
    private JTable tableClassifica;
    private JScrollPane scrollpaneClassifica;

    private Squadra squadra;

    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
    }

    Utils utils = new Utils();

    public void refresh(){
        nomeSquadra.setText(squadra.getNome());
        nomeUtente.setText(squadra.getProprietario().getNickname());
        setTableClassifica();
    }

    private void setTableClassifica(){
        Object[] nomeColonne = {"Squadra", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArrayPiccola(squadra.getCampionato().getClassifica());

        DefaultTableModel classificaModel = new DefaultTableModel(righeClassifica, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableClassifica.setModel(classificaModel);
    }

}
