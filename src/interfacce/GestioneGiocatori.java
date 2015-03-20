package interfacce;

import classi.Campionato;
import classi.Giocatore;
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
    private JButton confermaRoseButton;
    private JSpinner spinner;

    private DefaultComboBoxModel<String> comboBoxModel;
    private DefaultTableModel tabellaGiocatoriModel;
    private ArrayList<DefaultTableModel> tabellaSquadraModel;
    private Campionato campionato;
    //intestazioni delle due tabelle del panel, la prima per la tabella dei
    //giocatori mentre la seconda per la tabella delle squadre
    String[] colonne1 = {"ID", "Giocatore", "Ruolo", "Prezzo Base"};
    String[] colonne2 = {"ID", "Giocatore", "Ruolo", "Prezzo Base", "Prezzo di Acquisto"};

    public GestioneGiocatori(Campionato camp, ArrayList<Giocatore> gioc){

        campionato = camp;
        /*model per l'oggetto spinner che si occupa dell'inserimento del prezzo d'acquisto
        dei giocatori al momento dell'inserimento nella tabella della squadra.
        Gli argomenti del costruttore sono; (partenza, minimo, massimo, incremento)
        */
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
        spinner.setModel(spinnerModel);

        //la funzione crea un combobox in base al vettore di squadre che viene passato
        creaComboBox(campionato.getListaSquadrePartecipanti());
        //si imposta il modello del combobox e si aggiunge il listener per il cambio degli elementi
        comboBox.setModel(comboBoxModel);
        comboBox.addItemListener(this);

        //gestione lista dei giocatori
        creaTabellaGiocatori(gioc);
        tabellaGiocatori.setModel(tabellaGiocatoriModel);

        //crea le tabelle per ogni squadra partecipante, si selezione la tabella desidarata attraverso il combobox
        creaTabelleSquadre(campionato.getListaSquadrePartecipanti());
        tabellaSquadra.setModel(tabellaSquadraModel.get(0));


        //Pulsante aggiungi giocatore
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //si prende l'indice dell'elemento nel combobox che è lo stesso del modello associato alla tabella
                //della rispettiva squadra dato che le tabelle sono create in ordine
                int i = comboBox.getSelectedIndex();
                //controlla se è stata selezionata una riga nella tabella dei giocatori, altrimenti mostra un
                //messaggio d'errore per invitare a selezionare una riga prima di premere il pulsante
                if(tabellaGiocatori.getSelectedRow() != -1) {
                    int j = 0, k = 0;
                    //indice riga nella tabella dei giocatori
                    int r = tabellaGiocatori.getSelectedRow();
                    //memorizza i parametri in variabili per rendere il codice più chiaro
                    String ID = (String) tabellaGiocatori.getValueAt(r, 0);
                    String nomeGiocatore = (String) tabellaGiocatori.getValueAt(r, 1);
                    String ruolo = (String) tabellaGiocatori.getValueAt(r, 2);
                    /*fa scorrere un indice lungo le righe della tabella della squadra
                    //ad ogni iterazione se il valore del campo ruolo della riga della tabella squadra è uguale al ruolo
                    //del giocatore che si vuole inserire, incrementa un contatore per evitare che si inseriscano più
                    //giocatori di quelli consentiti per un determinato ruolo*/
                    while ( k < tabellaSquadraModel.get(i).getRowCount()){
                        if(((String)tabellaSquadraModel.get(i).getValueAt(j, 2)).equals(ruolo)) j++;
                        k++;
                    }
                    /*a seconda del ruolo controlla il contatore incrementato precedentemente e se c'è un
                    //errore nel numero di giocatori apre una finestra d'errore per segnalarlo. Se non c'è nessun errore,
                    //popola la nuova riga aggiungendola in fondo alla tabella*/
                    if(ruolo.equals("p")){
                        if(j >= 3) JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 3 portieri alla squadra.\nRimuovere un portiere per aggiungerne un altro.", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else if(ruolo.equals("d")){
                        if(j >= 8) JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 8 difensori alla squadra.\nRimuovere un difensore per aggiungerne un altro", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else if(ruolo.equals("c")){
                        if(j >= 8) JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 8 centrocampisti alla squadra.\nRimuovere un centrocampista per aggiungerne un altro", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else if(ruolo.equals("a")){
                        if(j >=6) JOptionPane.showMessageDialog(null, "Sono già stati aggiunti 6 attaccanti alla squadra.\nRimuovere un attaccante per aggiungerne un altro", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        tabellaSquadraModel.get(i).addRow(new String[]{ID, nomeGiocatore, ruolo, String.valueOf(spinner.getValue())});
                        tabellaGiocatoriModel.removeRow(r);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nessuna riga della tabella dei giocatori è stata selezionata! \n Seleziona una riga prima di premere il pulsante", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                //controlla che la squadra non sia al completo. Nel caso lo sia, disabilita il pulsante per aggiungere nuovi elementi
                if(tabellaSquadraModel.get(i).getRowCount() == 25){
                   addButton.setEnabled(false);
                }
                //dopo l'inserimento della prima riga della tabella abilita il pulsante di rimozione
                if(tabellaSquadraModel.get(i).getRowCount() == 1){
                    removeButton.setEnabled(true);
                }
            }
        });

        //Pulsante rimuovi giocatore, commenti come sopra se non specificato
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = comboBox.getSelectedIndex();
                //aggiunge una nuova riga nella tabella dei giocatori e la elimina da quella delle squadre
                if(tabellaSquadra.getSelectedRow() != -1){
                    int r = tabellaSquadra.getSelectedRow();
                    tabellaGiocatoriModel.addRow(new String[]{String.valueOf(tabellaSquadra.getValueAt(r, 0)), (String)tabellaSquadra.getValueAt(r, 1), String.valueOf(tabellaSquadra.getValueAt(r, 2)), String.valueOf(tabellaSquadra.getValueAt(r, 3))});
                    tabellaSquadraModel.get(i).removeRow(r);
                } else {
                    JOptionPane.showMessageDialog(null, "Nessuna riga della tabella della squadra è stata selezionata! \n Seleziona una riga prima di premere il pulsante", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                if(tabellaSquadraModel.get(i).getRowCount() == 0){
                    removeButton.setEnabled(false);
                }
                //nel caso in cui si sia rimosso il 25esimo elemento si riattiva il pulsante per aggiungere un giocatore alla squadra
                if(tabellaSquadraModel.get(i).getRowCount() == 24){
                    addButton.setEnabled(true);
                }
            }
        });

        //Pulsante per confermare le squadre del campionato
        confermaRoseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //booleano per verificare che tutte le rose siano complete
                boolean completo = false;
                //numero degli elementi del combobox (numero di squadre del campionato)
                int n = comboBox.getItemCount();
                //se la tabella della relativa squadra ha 25 elementi setta completo come true.
                //Per inviare le modifiche tutte le rose devono essere complete
                for(int i = 0; i < n; i++){
                    if(tabellaSquadraModel.get(i).getRowCount() == 25) completo = true;
                    else completo = false;
                }
                if(completo){
                    //TODO richiamare la funzione che salva sul database le formazioni
                } else {
                    JOptionPane.showMessageDialog(null, "La rosa di alcune squadre non è completa.\nPrima di confermare è necessario completare tutte le rose.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //override del metodo itemStateChanged che descrive cosa accade quando cambia l'elemento selezionato nella combobox
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        int i;
        i = comboBox.getSelectedIndex();
        //imposta il modella per la tabella della squadra secondo l'indice del combobox
        tabellaSquadra.setModel(tabellaSquadraModel.get(i));
        //controlla che la lista della squadra non sia completa
        if(tabellaSquadraModel.get(i).getRowCount() == 25){
            addButton.setEnabled(false);
        }
        //controlla che la lista della squadra non sia vuota
        if(tabellaSquadraModel.get(i).getRowCount() == 0){
            removeButton.setEnabled(false);
        }
    }

    private void creaComboBox(ArrayList<Squadra> squadre){
        //per ogni squadra del campionato crea un elemento del combobox
        for(Squadra i : squadre) comboBoxModel.addElement(i.getNome());
    }

    private void creaTabellaGiocatori(ArrayList<Giocatore> giocatori){
        //crea la tabella con tutti i giocatori disponibili per il campionato
        tabellaGiocatoriModel = new DefaultTableModel();
        tabellaGiocatoriModel.setColumnIdentifiers(colonne1);
        for(Giocatore g : giocatori){
            tabellaGiocatoriModel.addRow(new String[]{String.valueOf(g.getID()), g.getCognome(), String.valueOf(g.getRuolo()), String.valueOf(g.getPrezzoBase())});
        }
    }

    private void creaTabelleSquadre(ArrayList<Squadra> squadre){
        //crea una tabella vuota per ogni squadra del campionato
        tabellaSquadraModel = new ArrayList<DefaultTableModel>();
        int i = 0;
        for(Squadra s : squadre) {
            tabellaSquadraModel.set(i, new DefaultTableModel());
            tabellaSquadraModel.get(i).setColumnIdentifiers(colonne2);
            i++;
        }
    }






}
