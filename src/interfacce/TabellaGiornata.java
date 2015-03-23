package interfacce;

import classi.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by alessandro on 23/03/15.
 */
public class TabellaGiornata extends JPanel{

    private Giornata giornata;

    private JLabel numeroGiornatalbl;
    private JTable giornataTable;
    private JPanel mainPanel;


    public TabellaGiornata(Giornata gior){
        this.giornata = gior;
        numeroGiornatalbl.setText(String.valueOf(giornata.getNumGiornata()));
        setTabella();

    }

    public void setTabella(){
        Object[] nomeColonne = {"Casa", "Punti", "Gol", "Gol", "Punti", "Ospite"};
        Object[][] righeGiornata = giornata.partiteToArray();

        DefaultTableModel classificaModel = new DefaultTableModel(righeGiornata, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        giornataTable.setModel(classificaModel);

        //setta il colore delle righe alternato
        giornataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });

    }
}
