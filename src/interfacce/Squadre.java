package interfacce;

import classi.Squadra;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Giacomo on 24/03/15.
 */
public class Squadre extends JPanel{
    private JComboBox selSqua;
    private JLabel proprietario;
    private JTable tableRosa;
    private JPanel mainPanel;

    private Squadra squadra;

    Utils utils = new Utils();

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
        selSqua.setToolTipText("ID-Nome Squadra-Nick Proprietario");
    }

    private void setTabellaGiocatori() {
        Object[][] righeGiocatori = utils.listaGiocatoriToArraySquadre(squadra.getGiocatori());
        Object[] nomeColonne = {"Cognome", "Ruolo", "Squadra Reale", "Prezzo Pagato"};
        DefaultTableModel giocatoriModel = new DefaultTableModel(righeGiocatori, nomeColonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        tableRosa.setModel(giocatoriModel);

        //setta il colore delle righe alternato
        tableRosa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });
    }

}
