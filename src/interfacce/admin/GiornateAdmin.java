package interfacce.admin;

import db.Mysql;
import entità.GiornataReale;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alessandro on 18/03/15.
 */
public class GiornateAdmin extends JPanel {
    private JTable tabellaGiornate;
    private JPanel giornatePanel;
    private JLabel nrGiornataLabel;
    private JButton aggiornaButton;
    private JPanel dataInizioPanel;
    private JSpinner spinnerInizio;
    private JSpinner spinnerFine;

    private ArrayList<GiornataReale> listaGiornate = new ArrayList<GiornataReale>();

    public GiornateAdmin() {

        listaGiornate = Mysql.selectGiornateAdmin();

        setTabella();

        //jspinner data e time
        spinnerInizio.setModel(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorInizio = new JSpinner.DateEditor(spinnerInizio, "dd/MM/yyyy HH:mm");
        spinnerInizio.setEditor(timeEditorInizio);
        spinnerInizio.setValue(new Date());

        spinnerFine.setModel(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorFine = new JSpinner.DateEditor(spinnerFine, "dd/MM/yyyy HH:mm");
        spinnerFine.setEditor(timeEditorFine);
        spinnerFine.setValue(new Date());

        tabellaGiornate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int riga = tabellaGiornate.getSelectedRow();
                nrGiornataLabel.setText(String.valueOf(riga + 1));
                Date dataInizio;
                if(listaGiornate.get(riga).getDataOraInizio()==null){
                    dataInizio = new Date();
                } else{
                    dataInizio = listaGiornate.get(riga).getDataOraInizio();
                }
                Date dataFine;
                if(listaGiornate.get(riga).getDataOraFine()==null){
                    dataFine = new Date();
                } else{
                    dataFine = listaGiornate.get(riga).getDataOraFine();
                }
                spinnerFine.setValue(dataFine);
                spinnerInizio.setValue(dataFine);
            }
        });

        aggiornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int giornata = Integer.parseInt(nrGiornataLabel.getText())-1;
                listaGiornate.get(giornata).setDataOraInizio((Date)spinnerInizio.getValue());
                listaGiornate.get(giornata).setDataOraFine((Date) spinnerFine.getValue());

                Mysql.aggiornaGiornataReale(listaGiornate.get(giornata));
                setTabella();
            }
        });




    }

    /**
     * Crea la tabella e la popola con i dati provenienti dal database.
     */
    private void setTabella() {
        Object[] nomeColonne = {"Numero Giornata", "Data Ora Inizio","Data Ora fine"};
        //listaToArray ritorna un array di object che serve per il model
        Object[][] righeGiornate = Utils.listaGiornateToArray(listaGiornate);
        //creo il modello con le colonne e i giocatori
        DefaultTableModel giocatoriModel = new DefaultTableModel(righeGiornate, nomeColonne) {
            //rende non modificabili le celle della tabella
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //permette di selezionare una riga alla volta
        DefaultListSelectionModel giornateListModel = new DefaultListSelectionModel(){
            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        };
        giornateListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaGiornate.setSelectionModel(giornateListModel);

        tabellaGiornate.setModel(giocatoriModel);

    }

}
