package interfacce;

import classi.Campionato;
import classi.Giocatore;
import classi.Persona;
import classi.Squadra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by alessandro on 19/03/15.
 */
public class GestioneGiocatori extends JPanel implements ItemListener{
    private JTable tabellaGiocatori;
    private JButton removeButton;
    private JButton addButton;
    private JTable tabellaSquadra;
    private JComboBox comboBox;
    private JButton inviaModificheButton;
    private JSpinner spinner;

    private DefaultComboBoxModel<String> comboBoxModel;
    private DefaultTableModel tabellaGiocatoriModel;
    private ArrayList<DefaultTableModel> tabellaSquadraModel;
    private Campionato campionato;
    String[] colonne1 = {"Giocatore", "Prezzo Base"};
    String[] colonne2 = {"Giocatore", "Prezzo di Acquisto"};

    public GestioneGiocatori(Campionato camp, ArrayList<Giocatore> gioc){

        campionato = camp;
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
        spinner.setModel(spinnerModel);

        //la funzione crea un combobox in base al vettore di squadre che viene passato
        creaComboBox(campionato.getListaSquadrePartecipanti());
        comboBox.setModel(comboBoxModel);
        comboBox.addItemListener(this);

        //gestione lista dei giocatori
        creaTabellaGiocatori(gioc);
        tabellaGiocatori.setModel(tabellaGiocatoriModel);

        //crea le tabelle per ogni squadra partecipante, si selezione la tabella desidarata attraverso il combobox
        creaTabelleSquadre(campionato.getListaSquadrePartecipanti());
        tabellaSquadra.setModel(tabellaSquadraModel.get(0));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    int i = comboBox.getSelectedIndex();
                    int r = tabellaGiocatori.getSelectedRow();
                    String nomeGiocatore = (String) tabellaGiocatori.getValueAt(r, 0);
                    tabellaSquadraModel.get(i).addRow(new String[]{nomeGiocatore, String.valueOf(spinner.getValue())});
            }
        });
    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        int i;
        i = comboBox.getSelectedIndex();
        tabellaSquadra.setModel(tabellaSquadraModel.get(i));
    }

    private void creaComboBox(ArrayList<Squadra> squadre){
        for(Squadra i : squadre) comboBoxModel.addElement(i.getNome());
    }

    private void creaTabellaGiocatori(ArrayList<Giocatore> giocatori){
        tabellaGiocatoriModel = new DefaultTableModel();
        tabellaGiocatoriModel.setColumnIdentifiers(colonne1);
        for(Giocatore g : giocatori){
            tabellaGiocatoriModel.addRow(new String[]{g.getCognome(),String.valueOf(g.getPrezzoBase())});
        }
    }

    private void creaTabelleSquadre(ArrayList<Squadra> squadre){
        tabellaSquadraModel = new ArrayList<DefaultTableModel>();
        int i = 0;
        for(Squadra s : squadre) {
            tabellaSquadraModel.set(i, new DefaultTableModel());
            tabellaSquadraModel.get(i).setColumnIdentifiers(colonne2);
            i++;
        }
    }






}
