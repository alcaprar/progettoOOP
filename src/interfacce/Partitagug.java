package interfacce;

import classi.Partita;
import classi.Squadra;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Giacomo on 25/03/15.
 */
public class Partitagug extends JPanel{
    private JPanel partita;
    private JTable formCasa;
    private JTable formOspite;
    private JLabel bonusCasa;
    private JLabel puntiOspite;
    private JLabel puntiCasa;
    private JLabel squadraCasa;
    private JLabel utenteCasa;
    private JLabel squadraOspite;
    private JLabel utenteOspite;

    private Partita p;

    public void refresh(){
        utenteCasa.setText(p.getCasa().getProprietario().getNickname());
        utenteOspite.setText(p.getOspite().getProprietario().getNickname());
        squadraCasa.setText(p.getCasa().getNome());
        squadraOspite.setText(p.getOspite().getNome());
        puntiCasa.setText(String.valueOf(p.getPuntiCasa()));
        puntiOspite.setText(String.valueOf(p.getPuntiFuori()));
        setTable();
    }

    private void setTable(){
        Object[] nomeColonne = {"Giocatore", "Voto"};
        Object[][] righeFormCasa = p.getCasa().getFormazione().listaFormToArray();
        Object[][] righeFormOspite = p.getOspite().getFormazione().listaFormToArray();

        DefaultTableModel formCasaModel = new DefaultTableModel(righeFormCasa, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableModel formOspiteModel = new DefaultTableModel(righeFormOspite, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        formCasa.setModel(formCasaModel);
        formOspite.setModel(formOspiteModel);

        //setta il colore delle righe alternato
        formCasa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });

        formOspite.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });

    }
}
