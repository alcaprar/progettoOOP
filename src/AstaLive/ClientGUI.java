package AstaLive;

import entità.*;
import interfacce.Applicazione.Applicazione;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe per l'interfaccia grafica del client.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField indirizzotxt;
    private JSpinner spinnerPorta;
    private JButton connettiButton;
    private JTextArea consoleArea;
    private JTextField usernametxt;
    private JLabel attualelbl;
    private JLabel cognomelbl;
    private JLabel ruololbl;
    private JLabel squadraRealelbl;
    private JLabel inizialelbl;
    private JSpinner spinnerOfferta;
    private JButton buttonOfferta;
    private JLabel tempolbl;
    private JComboBox squadreCombobox;
    private JTable portieriTable;
    private JTable difensoriTable;
    private JTable centrocampistiTable;
    private JTable attaccantiTable;
    private JLabel soldiSpesilbl;
    private JTable portieriDisponibiliTable;
    private JTable difensoriDisponibiliTable;
    private JTable centrocampistiDisponibiliTable;
    private JTable attaccantiDisponibiliTable;
    private JButton lasciaButton;
    private JCheckBox mostraConsoleCheckBox;
    private JScrollPane consolePanel;
    private JLabel nicknamelbl;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private boolean offerto;

    private ArrayList<TableNotEditableModel> listaPortieriSquadra;
    private ArrayList<TableNotEditableModel> listaDifensoriSquadra;
    private ArrayList<TableNotEditableModel> listaCentrocampistiSquadra;
    private ArrayList<TableNotEditableModel> listaAttaccantiSquadra;
    private TableNotEditableModel portieriDispModel;
    private TableNotEditableModel difensoriDispModel;
    private TableNotEditableModel centrocampistiDispModel;
    private TableNotEditableModel attaccantiDispModel;

    //soldi spesi dai singoli partecipanti
    private ArrayList<Integer> soldiSpesi;

    private ArrayList<Persona> listaPartecipanti;

    private Applicazione applicazione;
    private Campionato campionato;
    private Persona utente;

    //counter dei giocatori acquistati dal client
    private int giocatoriAcquistati = 0;
    //riferimento ai soldi spesi dal client
    private Integer soldiSpesiMiei;

    private Client client;

    public ClientGUI(Applicazione app, Campionato camp, Persona ut){
        super("Client - "+ut.getNickname());
        this.applicazione = app;
        this.campionato = camp;
        this.utente=ut;

        nicknamelbl.setText(utente.getNickname());

        setSpinnerPorta();

        connettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Validator.indirizzoIP(indirizzotxt.getText())) {
                    String indirizzo = indirizzotxt.getText();
                    int porta = (Integer) spinnerPorta.getValue();
                    appendConsole("Tentativo di connessione a: " + indirizzo + ":" + porta);
                    client = new Client(indirizzo, porta, utente, getFrame());
                }
            }
        });

        buttonOfferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offerto = true;
                setOffertaNotEnabled();
            }
        });

        lasciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOffertaNotEnabled();
            }
        });

        squadreCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int i = squadreCombobox.getSelectedIndex();
                portieriTable.setModel(listaPortieriSquadra.get(i));
                difensoriTable.setModel(listaDifensoriSquadra.get(i));
                centrocampistiTable.setModel(listaCentrocampistiSquadra.get(i));
                attaccantiTable.setModel(listaAttaccantiSquadra.get(i));

                aggiornaSoldi(soldiSpesi.get(i));
            }
        });

        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int risultato = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler chiudere?", "Exit", JOptionPane.OK_CANCEL_OPTION);
                if (risultato == JOptionPane.OK_OPTION) {
                    close();
                }
            }
        });

        mostraConsoleCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (mostraConsoleCheckBox.isSelected()) {
                    consolePanel.setVisible(true);
                } else {
                    consolePanel.setVisible(false);
                }
            }
        });



        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Scrive nella console.
     * @param str stringa da stampare
     */
    public void appendConsole(String str){
        consoleArea.append(sdf.format(new Date()) + ">> " + str + "\n");
        consoleArea.setCaretPosition(consoleArea.getText().length());
    }

    /**
     * Setta il panel per l'offerta con il giocatore attuale.
     * @param giocatore giocatore per cui si sta offrendo
     * @param prezzo prezzo attuale del giocatore
     * @param utenteOfferta utente che sta offrendo per questo giocatore
     */
    public void setGiocatoreAttuale(Giocatore giocatore, int prezzo, Persona utenteOfferta){
        cognomelbl.setText(giocatore.getCognome());
        ruololbl.setText(String.valueOf(giocatore.getRuolo()));
        squadraRealelbl.setText(giocatore.getSquadraReale());
        inizialelbl.setText(String.valueOf(giocatore.getPrezzoBase()));

        if(prezzo<giocatore.getPrezzoBase()){
            attualelbl.setText("Prezzo base");
        } else {
            attualelbl.setText(String.valueOf(prezzo)+" - "+utenteOfferta.getNickname());
        }

        //setto lo spinner in base hai soldi rimanenti
        //(serve per non rischiare di far sforare con le offerte
        int max = campionato.getCreditiIniziali() - soldiSpesiMiei -(25-giocatoriAcquistati) ;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(prezzo+1,prezzo+1,max,1);
        spinnerOfferta.setModel(spinnerModel);
    }

    /**
     * Rende attivi i bottoni per l'offerta.
     */
    public void setOffertaEnabled(){
        spinnerOfferta.setEnabled(true);
        buttonOfferta.setEnabled(true);
        lasciaButton.setEnabled(true);
        offerto = false;
    }

    /**
     * Disattiva i bottoni per l'offerta.
     */
    public void setOffertaNotEnabled(){
        spinnerOfferta.setEnabled(false);
        buttonOfferta.setEnabled(false);
        lasciaButton.setEnabled(false);
    }

    /**
     * Disattiva il pannello per la connessione.
     */
    public void setConnettiNotEnabled(){
        indirizzotxt.setEnabled(false);
        spinnerPorta.setEnabled(false);
        connettiButton.setEnabled(false);
    }

    /**
     * Aggiorna il label per il tempo rimanente.
     * @param secondi secondi rimanenti
     */
    public void setCountdown(int secondi){
        tempolbl.setText(String.valueOf(secondi));
        if(secondi<=3){
            tempolbl.setForeground(Color.RED);
        } else{
            tempolbl.setForeground(Color.BLACK);
        }
    }

    /**
     * Setta lo spinner per la porta.
     * Utilizza un editor modificato per non far vedere il punto delle migliaia.
     */
    private void setSpinnerPorta(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1500,1000,2000,1);
        spinnerPorta.setModel(spinnerModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerPorta, "#");
        spinnerPorta.setEditor(editor);
    }

    /**
     * Setta il combobox e la tabella per i giocatori dei singoli partecipanti.
     * @param listaPartecipanti
     */
    public void setComboBoxTable(ArrayList<Persona> listaPartecipanti){
        //inizializzo il model del combobox
        DefaultComboBoxModel squadreBoxModel = new DefaultComboBoxModel();
        //inizializzo il model delle tabelle
        listaPortieriSquadra = new ArrayList<TableNotEditableModel>();
        listaDifensoriSquadra = new ArrayList<TableNotEditableModel>();
        listaCentrocampistiSquadra = new ArrayList<TableNotEditableModel>();
        listaAttaccantiSquadra = new ArrayList<TableNotEditableModel>();

        this.listaPartecipanti = listaPartecipanti;

        soldiSpesi = new ArrayList<Integer>();
        for(Persona partecipante : listaPartecipanti) {
            squadreBoxModel.addElement(partecipante.getNickname());

            String[] colonne = {"Giocatore","Prezzo pagato"};
            //portieri model
            TableNotEditableModel portieriModel = new TableNotEditableModel();
            portieriModel.setColumnIdentifiers(colonne);
            listaPortieriSquadra.add(portieriModel);
            //difensori model
            TableNotEditableModel difensoriModel = new TableNotEditableModel();
            difensoriModel.setColumnIdentifiers(colonne);
            listaDifensoriSquadra.add(difensoriModel);
            //centrocampisti
            TableNotEditableModel centrocampistiModel = new TableNotEditableModel();
            centrocampistiModel.setColumnIdentifiers(colonne);
            listaCentrocampistiSquadra.add(centrocampistiModel);
            //attaccanti
            TableNotEditableModel attaccantiModel = new TableNotEditableModel();
            attaccantiModel.setColumnIdentifiers(colonne);
            listaAttaccantiSquadra.add(centrocampistiModel);

            soldiSpesi.add(0);
        }
        squadreCombobox.setModel(squadreBoxModel);
        portieriTable.setModel(listaPortieriSquadra.get(0));
        difensoriTable.setModel(listaDifensoriSquadra.get(0));
        centrocampistiTable.setModel(listaCentrocampistiSquadra.get(0));
        attaccantiTable.setModel(listaAttaccantiSquadra.get(0));

        int index=0;
        for(int i=0;i<listaPartecipanti.size();i++){
            if(listaPartecipanti.get(i).equals(utente)) index = i;
        }

        soldiSpesiMiei = soldiSpesi.get(index);
    }

    /**
     * Aggiunge un giocatore alla tabella dei giocatori acquistati.
     * @param giocatore giocatore acquistato
     * @param prezzo prezzo d'acquisto del giocatore
     * @param persona persona che ha acquistato il giocatore
     */
    public void aggiungiGiocatore(Giocatore giocatore, int prezzo, Persona persona){

        int i=0;
        for(int j=0;j<listaPartecipanti.size();j++){
            if(listaPartecipanti.get(j).equals(persona)) i=j;
        }
        Object[] rigaGiocatore = {giocatore.getCognome(),prezzo};

        if(giocatore.getRuolo()=='P'){
            listaPortieriSquadra.get(i).addRow(rigaGiocatore);
            for(int k=0;k<portieriDispModel.getRowCount();k++){
                if(portieriDispModel.getValueAt(k,0).equals(giocatore.getCognome())){
                    portieriDispModel.removeRow(k);
                }
            }
        } else if(giocatore.getRuolo()=='D'){
            listaDifensoriSquadra.get(i).addRow(rigaGiocatore);
            for(int k=0;k<difensoriDispModel.getRowCount();k++){
                if(difensoriDispModel.getValueAt(k,0).equals(giocatore.getCognome())){
                    difensoriDispModel.removeRow(k);
                }
            }
        } else if(giocatore.getRuolo()=='C'){
            listaCentrocampistiSquadra.get(i).addRow(rigaGiocatore);
            for(int k=0;k<centrocampistiDispModel.getRowCount();k++){
                if(centrocampistiDispModel.getValueAt(k,0).equals(giocatore.getCognome())){
                    centrocampistiDispModel.removeRow(k);
                }
            }
        } else {
            listaAttaccantiSquadra.get(i).addRow(rigaGiocatore);
            for(int k=0;k<attaccantiDispModel.getRowCount();k++){
                if(attaccantiDispModel.getValueAt(k,0).equals(giocatore.getCognome())){
                    attaccantiDispModel.removeRow(k);
                }
            }
        }

        //se l'ho acquistato io aggiorno il contatore
        if(persona.equals(utente)){
            giocatoriAcquistati++;
        }
        soldiSpesi.set(i, soldiSpesi.get(i) + prezzo);

        aggiornaSoldi(soldiSpesi.get(squadreCombobox.getSelectedIndex()));
    }

    /**
     * Setta la tabella per la lista dei giocatori disponibili durante l'asta.
     * @param listaGiocatori lista dei giocatori disponibili
     */
    public void setListaGiocatoriDisponibili(ArrayList<Giocatore> listaGiocatori){
        Object[] colonne = {"Cognome","Prezzo","Squadra"};

        portieriDispModel = new TableNotEditableModel();
        portieriDispModel.setColumnIdentifiers(colonne);
        difensoriDispModel = new TableNotEditableModel();
        difensoriDispModel.setColumnIdentifiers(colonne);
        centrocampistiDispModel = new TableNotEditableModel();
        centrocampistiDispModel.setColumnIdentifiers(colonne);
        attaccantiDispModel = new TableNotEditableModel();
        attaccantiDispModel.setColumnIdentifiers(colonne);

        for(Giocatore giocatore : listaGiocatori){
            if(giocatore.getRuolo()=='P'){
                portieriDispModel.addRow(new Object[]{giocatore.getCognome(),giocatore.getPrezzoBase(),giocatore.getSquadraReale()});
            } else if(giocatore.getRuolo()=='D'){
                difensoriDispModel.addRow(new Object[]{giocatore.getCognome(),giocatore.getPrezzoBase(),giocatore.getSquadraReale()});
            } else if(giocatore.getRuolo()=='C'){
                centrocampistiDispModel.addRow(new Object[]{giocatore.getCognome(),giocatore.getPrezzoBase(),giocatore.getSquadraReale()});
            } else if(giocatore.getRuolo()=='A'){
                attaccantiDispModel.addRow(new Object[]{giocatore.getCognome(),giocatore.getPrezzoBase(),giocatore.getSquadraReale()});
            }
        }

        portieriDisponibiliTable.setModel(portieriDispModel);
        difensoriDisponibiliTable.setModel(difensoriDispModel);
        centrocampistiDisponibiliTable.setModel(centrocampistiDispModel);
        attaccantiDisponibiliTable.setModel(attaccantiDispModel);
    }

    /**
     * Ritorna l'oggetto principale.
     * @return this
     */
    private ClientGUI getFrame(){
        return this;
    }

    /**
     * Chiude la finestra e rende visibile l'applicazione principale.
     */
    public void close(){
        client.close();
        getFrame().dispose();
        applicazione.setVisible(true);
    }

    /**
     * Chiude l'applicazione.
     */
    public void astaFinita(){
        getFrame().dispose();
        System.exit(1);
    }

    /**
     * Ritorna true se il client ha offerto, false altrimenti.
     * @return true se ha offerto, false altrimenti
     */
    public boolean haOfferto(){
        return offerto;
    }

    /**
     * Ritorna il valore dello spinner per l'offerta.
     * @return valore offerta
     */
    public int getValoreOfferta(){
        return (Integer)spinnerOfferta.getValue();
    }

    /**
     * Aggiorna il label dei soldi in base alla persona selezionata nel combobox.
     * @param soldi soldi della persona
     */
    private void aggiornaSoldi(Integer soldi){
        soldiSpesilbl.setText(String.valueOf(soldi));
    }
}
