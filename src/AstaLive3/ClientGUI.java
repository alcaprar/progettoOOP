package AstaLive3;

import classi.Giocatore;
import utils.TableNotEditableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private JLabel soldiSpesilbl;
    private JTable portieriDisponibiliTable;
    private JTable difensoriDisponibiliTable;
    private JTable centrocampistiDisponibiliTable;
    private JTable attaccantiDisponibiliTable;
    private JButton lasciaButton;
    private JCheckBox mostraConsoleCheckBox;
    private JScrollPane consolePanel;

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

    private ArrayList<Integer> soldiSpesi;

    private ArrayList<String> partecipanti;

    private int creditiIniziali = 800;
    private int giocatoriAcquistati = 0;
    //miei soldi spesi
    private Integer soldiSpesiMiei;

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
                    getFrame().dispose();
                    System.exit(1);
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

    public void setGiocatoreAttuale(Giocatore giocatore, int prezzo, String utenteOfferta){
        cognomelbl.setText(giocatore.getCognome());
        ruololbl.setText(String.valueOf(giocatore.getRuolo()));
        squadraRealelbl.setText(giocatore.getSquadraReale());
        inizialelbl.setText(String.valueOf(giocatore.getPrezzoBase()));

        if(prezzo<giocatore.getPrezzoBase()){
            attualelbl.setText("-");
        } else {
            attualelbl.setText(String.valueOf(prezzo)+" - "+utenteOfferta);
        }
        int max = creditiIniziali - soldiSpesiMiei -(25-giocatoriAcquistati) ;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(prezzo+1,prezzo+1,max,1);
        spinnerOfferta.setModel(spinnerModel);
    }

    public void setOffertaEnabled(){
        spinnerOfferta.setEnabled(true);
        buttonOfferta.setEnabled(true);
        lasciaButton.setEnabled(true);
        offerto = false;
    }

    public void setOffertaNotEnabled(){
        spinnerOfferta.setEnabled(false);
        buttonOfferta.setEnabled(false);
        lasciaButton.setEnabled(false);
    }

    public void setCountdown(int secondi){
        tempolbl.setText(String.valueOf(secondi));
    }

    private void setSpinnerPorta(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1500,1000,2000,1);
        spinnerPorta.setModel(spinnerModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerPorta, "#");
        spinnerPorta.setEditor(editor);
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

        soldiSpesi = new ArrayList<Integer>();
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

            soldiSpesi.add(new Integer(0));
        }
        squadreCombobox.setModel(squadreBoxModel);
        portieriTable.setModel(listaAttaccantiSquadra.get(0));
        difensoriTable.setModel(listaDifensoriSquadra.get(0));
        centrocampistiTable.setModel(listaCentrocampistiSquadra.get(0));
        attaccantiTable.setModel(listaAttaccantiSquadra.get(0));

        soldiSpesiMiei = soldiSpesi.get(partecipanti.indexOf(usernametxt.getText()));
    }

    public void aggiungiGiocatore(Giocatore giocatore, int prezzo, String persona){
        int i = partecipanti.indexOf(persona);
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
        if(persona.equals(usernametxt.getText())){
            giocatoriAcquistati++;
        }
        soldiSpesi.set(i, soldiSpesi.get(i) + prezzo);

        aggiornaSoldi(soldiSpesi.get(squadreCombobox.getSelectedIndex()));
    }

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

    private ClientGUI getFrame(){
        return this;
    }

    public boolean haOfferto(){
        return offerto;
    }

    public int getValoreOfferta(){
        return (Integer)spinnerOfferta.getValue();
    }

    private void aggiornaSoldi(Integer soldi){
        soldiSpesilbl.setText(String.valueOf(soldi));
    }
}
