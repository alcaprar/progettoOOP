package interfacce;

import classi.Squadra;
import utils.RenderTableAlternate;
import utils.TableNotEditableModel;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by Giacomo on 24/03/15.
 */
public class Squadre extends JPanel{
    private JComboBox selSqua;
    private JLabel proprietario;
    private JTable tableRosa;
    private JPanel mainPanel;

    private Squadra squadra;

    private ArrayList<DefaultTableModel> listaTableModel = new ArrayList<DefaultTableModel>();

    Utils utils = new Utils();

    public Squadre(){
        selSqua.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int i = selSqua.getSelectedIndex();
                tableRosa.setModel(listaTableModel.get(i));
            }
        });
    }

    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
    }

    public void refresh(){
        setComboBox();
        proprietario.setText(squadra.getProprietario().getNickname());
        setTabellaGiocatori();
    }

    public void setComboBox(){
        DefaultComboBoxModel comboBoxModel;
        comboBoxModel = new DefaultComboBoxModel(squadra.getCampionato().squadreToArray());
        selSqua.setModel(comboBoxModel);
        selSqua.setToolTipText("Scegli la squadra da visualizzare");
    }

    private void setTabellaGiocatori() {
        for(Squadra squadre : squadra.getCampionato().getListaSquadrePartecipanti()){
            Object[][] righeGiocatori = utils.listaGiocatoriToArraySquadre(squadre.getGiocatori());
            Object[] nomeColonne = {"Cognome", "Ruolo", "Squadra Reale", "Prezzo Pagato"};
            TableNotEditableModel giocatoriModel = new TableNotEditableModel(righeGiocatori, nomeColonne);
            listaTableModel.add(giocatoriModel);
        }

        tableRosa.setModel(listaTableModel.get(0));

        //setta il colore delle righe alternato
        tableRosa.setDefaultRenderer(Object.class, new RenderTableAlternate());
    }

}
