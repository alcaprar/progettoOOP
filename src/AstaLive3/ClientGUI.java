package AstaLive3;

import classi.Giocatore;
import utils.TableNotEditableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alessandro on 03/04/15.
 */
public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField indirizzotxt;
    private JSpinner spinnerPorta;
    private JButton connettiButton;
    private JTextArea consoleArea;
    private JTextField usernametxt;
    private JLabel campionatoL;
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

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private boolean offerto;

    private ArrayList<TableNotEditableModel> listaPortieriSquadra;
    private ArrayList<TableNotEditableModel> listaDifensoriSquadra;
    private ArrayList<TableNotEditableModel> listaCentrocampistiSquadra;
    private ArrayList<TableNotEditableModel> listaAttaccantiSquadra;

    private ArrayList<String> partecipanti;

    public ClientGUI(){
        super("Client");
        setSpinnerPorta();

        connettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indirizzo = indirizzotxt.getText();
                int porta = (Integer) spinnerPorta.getValue();
                String username = usernametxt.getText();
                appendConsole("Tentativo di connessione a: " + indirizzo + ":" + porta + " Username: " + username);
                new Client(indirizzo, porta, username, getFrame());
            }
        });

        buttonOfferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offerto=true;
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
            }
        });

        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String args[]){
        new ClientGUI();
    }

    /**
     * Scrive nella console.
     * @param str stringa da stampare
     */
    public void appendConsole(String str){
        consoleArea.append(sdf.format(new Date()) + ">> " + str + "\n");
        consoleArea.setCaretPosition(consoleArea.getText().length());
    }

    public void setGiocatoreAttuale(Giocatore giocatore, int prezzo){
        cognomelbl.setText(giocatore.getCognome());
        ruololbl.setText(String.valueOf(giocatore.getRuolo()));
        squadraRealelbl.setText(giocatore.getSquadraReale());
        inizialelbl.setText(String.valueOf(giocatore.getPrezzoBase()));

        if(prezzo<giocatore.getPrezzoBase()){
            attualelbl.setText("-");
        } else {
            attualelbl.setText(String.valueOf(prezzo));
        }
        int max = 100;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(prezzo+1,prezzo+1,max,1);
        spinnerOfferta.setModel(spinnerModel);
    }

    public void setOffertaEnabled(){
        spinnerOfferta.setEnabled(true);
        buttonOfferta.setEnabled(true);
        offerto = false;
    }

    public void setOffertaNotEnabled(){
        spinnerOfferta.setEnabled(false);
        buttonOfferta.setEnabled(false);
    }

    public void setCountdown(int secondi){
        tempolbl.setText(String.valueOf(secondi));
    }

    private void setSpinnerPorta(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1500,1000,2000,1);
        spinnerPorta.setModel(spinnerModel);
    }

    public void setComboBox(ArrayList<String> listaPartecipanti){
        //inizializzo il model del combobox
        DefaultComboBoxModel squadreBoxModel = new DefaultComboBoxModel();
        //inizializzo il model delle tabelle
        listaPortieriSquadra = new ArrayList<TableNotEditableModel>();
        listaDifensoriSquadra = new ArrayList<TableNotEditableModel>();
        listaCentrocampistiSquadra = new ArrayList<TableNotEditableModel>();
        listaAttaccantiSquadra = new ArrayList<TableNotEditableModel>();

        partecipanti = new ArrayList<String>();
        for(String partecipante : listaPartecipanti) {
            squadreBoxModel.addElement(partecipante);
            partecipanti.add(partecipante);

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



        }

        squadreCombobox.setModel(squadreBoxModel);
    }

    public void aggiungiGiocatore(Giocatore giocatore, int prezzo, String persona){
        int i = partecipanti.indexOf(persona);
        Object[] rigaGiocatore = {giocatore.getCognome(),prezzo};

        if(giocatore.getRuolo()=='P'){
            listaPortieriSquadra.get(i).addRow(rigaGiocatore);
        } else if(giocatore.getRuolo()=='D'){
            listaDifensoriSquadra.get(i).addRow(rigaGiocatore);
        } else if(giocatore.getRuolo()=='C'){
            listaCentrocampistiSquadra.get(i).addRow(rigaGiocatore);
        } else {
            listaAttaccantiSquadra.get(i).addRow(rigaGiocatore);
        }
    }

    public void aggiungiPortiere(Giocatore giocatore, int prezzo,String persona){
        int i=0;
        int pers=0;
        for(String partecipante : partecipanti){
            if(partecipante.equals(persona)) pers =i;
            i++;
        }
        Object[] rigaGiocatore = {giocatore.getCognome(),prezzo};
        listaPortieriSquadra.get(pers).addRow(rigaGiocatore);
    }

    public void aggiungiDifensore(Giocatore giocatore, int prezzo,String persona){
        int i=0;
        int pers=0;
        for(String partecipante : partecipanti){
            if(partecipante.equals(persona)) pers =i;
            i++;
        }
        Object[] rigaGiocatore = {giocatore.getCognome(),prezzo};
        listaDifensoriSquadra.get(pers).addRow(rigaGiocatore);
    }

    public void aggiungiCentrocampista(Giocatore giocatore, int prezzo,String persona){
        int i=0;
        int pers=0;
        for(String partecipante : partecipanti){
            if(partecipante.equals(persona)) pers =i;
            i++;
        }
        Object[] rigaGiocatore = {giocatore.getCognome(),prezzo};
        listaPortieriSquadra.get(pers).addRow(rigaGiocatore);
    }

    private ClientGUI getFrame(){
        return this;
    }

    public boolean haOfferto(){
        return offerto;
    }

    public int getValoreOfferta(){
        return (Integer)spinnerOfferta.getValue();
    }
}
