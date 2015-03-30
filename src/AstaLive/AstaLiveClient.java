package AstaLive;

import classi.Giocatore;
import classi.Squadra;
import utils.RenderTableAlternate;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.Socket;
import java.util.ArrayList;

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
    private JTextArea serverConsole;
    private JSpinner spinnerPorta;
    private JLabel serverL;

    private Object[] colonne1 = {"ID", "Cognome", "Ruolo", "Squadra Reale", "Prezzo Iniziale"};
    private String[] colonne2 = {"Cognome", "Prezzo d'Acquisto"};
    private Utils utils = new Utils();
    private Squadra squadra;
    private ArrayList<Giocatore> listaGiocatori;
    private ArrayList<DefaultTableModel> tabellaSquadraModel;
    private ArrayList<Integer> soldiSpesi = new ArrayList<Integer>();
    private DefaultComboBoxModel comboBoxModel;
    private SpinnerModel spinnerModel;
    private SpinnerModel spinnerModel1;
    private Client client;
    private int l;

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

    public AstaLiveClient(int l) {

        this.l=l;
        nomeL.setText("Attendi");
        cognomeL.setText("Attendi");
        ruoloL.setText("Attendi");
        squadraRealeL.setText("Attendi");
        inizialeL.setText("Attendi");
        attualeL.setText("Attendi");

        serverConsole = new JTextArea(80,80);
        serverConsole.setEditable(false);
        serverConsole.append("Console degli eventi server");

        spinnerModel = new SpinnerNumberModel(0, 0, 6000, 1);
        spinnerPorta.setModel(spinnerModel);

        spinnerModel1 = new SpinnerNumberModel(1,1,1000,1);
        spinnerOfferta.setModel(spinnerModel1);

        if(l == 1){
            startStopButton.setVisible(true);
            serverL.setVisible(true);
            spinnerPorta.setVisible(true);
        } else {
            startStopButton.setVisible(false);
            serverL.setVisible(false);
            spinnerPorta.setVisible(false);
            // default values
            int portNumber = 1500;
            String serverAddress = "192.168.1.50";
            String userName = squadra.getProprietario().getNickname();
            boolean ok = false;
            while(true){
                try {
                    Socket socket = new Socket(serverAddress, portNumber);
                    ok = true;
                }
                catch (Exception e){ok = false;}
                if(ok){
                    client = new Client(serverAddress, portNumber, userName, AstaLiveClient.this);
                    if(!client.start()) {
                        JOptionPane.showMessageDialog(null, "Il client ha fallito durante l'avvio.", "Errore", JOptionPane.ERROR_MESSAGE);
                        System.exit(-1);
                    } else client.run();
                }
            }
        }

        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int port;
                port = (Integer) spinnerPorta.getValue();
                if(port == 0){
                    port = 1500;
                }
                Server server = new Server(port, AstaLiveClient.this);
                server.start();
            }
        });

        buttonOfferta.addActionListener(new ActionListener() {
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

    public void eventoServer(String s){
        serverConsole.append(s);
        serverConsole.setCaretPosition(serverConsole.getText().length() - 1);
    }

    /* public void displayMessage(ChatMessage chatMessage){
        cognomeL.setText(chatMessage.getGiocatore().getCognome());
        ruoloL.setText(String.valueOf(chatMessage.getGiocatore().getRuolo()));
        squadraRealeL.setText(chatMessage.getGiocatore().getSquadraReale());
        inizialeL.setText(String.valueOf(chatMessage.getGiocatore().getPrezzoBase()));
        attualeL.setText(String.valueOf(chatMessage.getGiocatore().getPrezzoAcquisto()));

        spinnerModel1.setValue((Integer)chatMessage.getGiocatore().getPrezzoAcquisto());
    }*/

    public static void main(String[] args){
        AstaLiveClient asta = new AstaLiveClient(1);
    }
}
