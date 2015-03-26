package interfacce;

import classi.Giornata;
import utils.RenderTableAlternate;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Created by alessandro on 23/03/15.
 */
public class TabellaGiornata extends JPanel {

    private JLabel label1;
    private JLabel label2;
    private JLabel numeroGiornata;
    private JLabel data;

    private JPanel panel;

    private JPanel panel1;

    private JTable giornataTable;

    private Giornata giornata;

    public TabellaGiornata(Giornata giorn, int prossimaGiornata){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        giornata = giorn;
        //creo e setto le etichette
        label1 = new JLabel("Giornata nr: ");
        numeroGiornata = new JLabel(String.valueOf(giornata.getNumGiornata()));
        label2 = new JLabel("Data: ");
        if(giorn.getGioReale().getDataOraInizio()!=null){
            data = new JLabel(giorn.getGioReale().getDataOraInizio().toString());
        } else {
            data = new JLabel("DATA MANCANTE");
        }

        //creo e setto la tabella della giornata
        giornataTable = new JTable();
        Object[] nomeColonne ;
        Object[][] righeGiornata;
        if(giorn.getNumGiornata()>=prossimaGiornata) {
            nomeColonne= new Object[2];
            nomeColonne[0]="Casa";
            nomeColonne[1]="Trasferta";
            righeGiornata = giornata.prossimaGiornataToArray();
        } else{
            nomeColonne = new Object[6];
            nomeColonne[0] = "Casa";
            nomeColonne[1]="";
            nomeColonne[2]="";
            nomeColonne[3]="";
            nomeColonne[4]="";
            nomeColonne[5]="Trasferta";
            righeGiornata = giornata.partiteToArray();
        }

        DefaultTableModel classificaModel = new DefaultTableModel(righeGiornata, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        giornataTable.setModel(classificaModel);

        //setta il colore delle righe alternato
        giornataTable.setDefaultRenderer(Object.class, new RenderTableAlternate());

        //non fa visualizzare le righe orizzontali
        giornataTable.setShowHorizontalLines(false);

        //stringe le colonne della tabella in base al contenuto
        giornataTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

        for (int column = 0; column < giornataTable.getColumnCount(); column++)
        {
            TableColumn tableColumn = giornataTable.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < giornataTable.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = giornataTable.getCellRenderer(row, column);
                Component c = giornataTable.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + giornataTable.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);


                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth( preferredWidth );
        }

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label1);
        panel.add(numeroGiornata);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(label2);
        panel1.add(data);

        add(panel);
        add(panel1);
        add(giornataTable);

    }
}
