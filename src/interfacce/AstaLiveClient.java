package interfacce;

import classi.Giocatore;
import classi.Squadra;
import utils.RenderTableAlternate;
import utils.Utils;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.jar.JarFile;

/**
 * Created by Christian on 25/03/2015.
 */
public class AstaLiveClient extends JFrame implements ItemListener{

    private JPanel mainPanel;
    private JSpinner spinnerOfferta;
    private JButton buttonOfferta;
    private JTable tabellaGiocatori;
    private JTable tabellaSquadra;
    private JComboBox comboBoxSquadre;
    private JLabel salutoL;
    private JLabel squadraL;
    private JLabel campionatoL;
    private JLabel cognomeL;
    private JLabel nomeL;
    private JLabel ruoloL;
    private JLabel squadraRealeL;
    private JLabel inizialeL;
    private JLabel attualeL;
    private JLabel soldiSpesiL;
    private JButton startStopButton;

    private Object[] colonne1 = {"ID", "Cognome", "Ruolo", "Squadra Reale", "Prezzo Iniziale"};
    private String[] colonne2 = {"Cognome", "Prezzo d'Acquisto"};
    private Utils utils = new Utils();
    private Squadra squadra;
    private ArrayList<Giocatore> listaGiocatori;
    private ArrayList<DefaultTableModel> tabellaSquadraModel;
    private ArrayList<Integer> soldiSpesi = new ArrayList<Integer>();
    private DefaultComboBoxModel comboBoxModel;

    public void setSquadra(Squadra sqr) {
        this.squadra = sqr;
    }

    public void refresh(){
        salutoL.setText("Benvenuto " + squadra.getProprietario().getNickname());
        squadraL.setText("Squadra: " + squadra.getNome());
        campionatoL.setText("Asta live per il campionato: " + squadra.getCampionato().getNome());
        setComboBoxSquadre();
        setTabellaGiocatori();
        setTabelleSquadre();
    }

    public AstaLiveClient() {

        nomeL.setText("Attendi");
        cognomeL.setText("Attendi");
        ruoloL.setText("Attendi");
        squadraRealeL.setText("Attendi");
        inizialeL.setText("Attendi");
        attualeL.setText("Attendi");

        if(squadra.getProprietario().isPresidenteLega()){
            startStopButton.setVisible(true);
        } else startStopButton.setVisible(false);

        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setComboBoxSquadre() {
        comboBoxModel = new DefaultComboBoxModel(squadra.getCampionato().squadreToArray());
        comboBoxSquadre.setModel(comboBoxModel);
        comboBoxSquadre.addItemListener(this);
    }

    //override del metodo itemStateChanged che descrive cosa accade quando cambia l'elemento selezionato nella combobox
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        int i;
        i = comboBoxSquadre.getSelectedIndex();
        //mostro i soldi spesi per questa squadra
        soldiSpesiL.setText(soldiSpesi.get(i).toString());
        //imposta il modella per la tabella della squadra secondo l'indice del combobox
        tabellaSquadra.setModel(tabellaSquadraModel.get(i));
    }

    private void setTabellaGiocatori() {
        Object[][] righeGiocatori = utils.listaGiocatoriToArray(listaGiocatori);

        DefaultTableModel giocatoriModel = new DefaultTableModel(righeGiocatori, colonne1) {
            //rende non modificabili le colonne dell'ID,cognome e ruolo
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //permette di selezionare una riga alla volta nella tabella giocatori
        DefaultListSelectionModel giocatoriListModel = new DefaultListSelectionModel(){
            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        };
        giocatoriListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaGiocatori.setSelectionModel(giocatoriListModel);

        tabellaGiocatori.setModel(giocatoriModel);
        tabellaGiocatori.setDefaultRenderer(Object.class, new RenderTableAlternate());

    }

    private void setTabelleSquadre(){
        //crea una tabella vuota per ogni squadra del campionato
        tabellaSquadraModel = new ArrayList<DefaultTableModel>();
        int i = 0;
        for(Squadra s : squadra.getCampionato().getListaSquadrePartecipanti()) {
            tabellaSquadraModel.add(new DefaultTableModel(){
                //rende non modificabili le colonne dell'ID,cognome e ruolo
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            tabellaSquadraModel.get(i).setColumnIdentifiers(colonne2);
            //inizializzo a zero il contatore dei soldi spesi
            soldiSpesi.add(new Integer(0));
            i++;
        }
        //permette di selezionare una riga alla volta nella tabella giocatori
        DefaultListSelectionModel squadreListModel = new DefaultListSelectionModel(){
            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        };
        squadreListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaSquadra.setSelectionModel(squadreListModel);

        tabellaSquadra.setModel(tabellaSquadraModel.get(0));
        tabellaSquadra.setDefaultRenderer(Object.class, new RenderTableAlternate());

        aggiornaSoldi(soldiSpesi.get(0));
    }

    private void aggiornaSoldi(Integer soldi){
        if(soldi<=squadra.getCampionato().getCreditiIniziali()){
            soldiSpesiL.setText(soldi.toString());
            soldiSpesiL.setForeground(Color.black);
        }
        else{
            soldiSpesiL.setText(soldi.toString());
            soldiSpesiL.setForeground(Color.RED);
            soldiSpesiL.setToolTipText("Questa squadra ha superato il budget consentito");
            JOptionPane.showMessageDialog(null, "Questa squadra ha superato il budget consentito.", "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }
}
