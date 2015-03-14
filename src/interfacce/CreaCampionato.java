package interfacce;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classi.*;
import db.Mysql;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 14/03/15.
 */
public class CreaCampionato extends JFrame {
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel nomePresidente;
    private JSpinner inizioSpinner;
    private JSpinner limiteSpinner;
    private JSpinner fineSpinner;
    private JSpinner primafSpinner;
    private JSpinner fasceSpinner;
    private JComboBox numeroBox;
    private JPanel panel;
    private JButton creaCampionatoButton;
    private JLabel nomeInfo;
    private JLabel numeroInfo;
    private JLabel astaInfo;
    private JLabel campionatoInfo;
    private JLabel inizioInfo;
    private JLabel fineInfo;
    private JLabel creditiInfo;
    private JLabel limiteInfo;
    private JLabel primafInfo;
    private JLabel fasceInfo;
    private JSpinner bonuscSpinner;
    private JLabel bcasaInfo;
    private JSpinner creditiSpinner;
    private JList partecipantiList;
    private JList utentiList;
    private JButton aggiungiButton;
    private JButton rimuoviButton;
    private String[] listaUtenti;
    private DefaultListModel partecipantiModel, utentiModel;

    public CreaCampionato(Persona utente){
        //titolo del frame
        super("Crea Campionato - Gestore fantacalcio");



        setContentPane(panel);

        nomePresidente.setText(utente.getNickname());

        pack();

        //centra il frame
        setLocationRelativeTo(null);

        setVisible(true);

        setResizable(false);

        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!utentiList.isSelectionEmpty()) {
                    //trovo l'indice dell'utente selezionato
                    int indice = utentiList.getSelectedIndex();
                    //trovo i valori dell'utente selezionato
                    Object utente = utentiList.getSelectedValue();

                    //aggiungo l'utente ai partecipanti
                    partecipantiModel.addElement(utente);
                    //rimuovo l'utente dai disponibili
                    utentiModel.remove(indice);
                }
            }
        });

        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!partecipantiList.isSelectionEmpty()) {
                    //trovo l'indice dell'utente selezionato
                    int indice = partecipantiList.getSelectedIndex();
                    //trovo i valori dell'utente selezionato
                    Object utente = partecipantiList.getSelectedValue();

                    //aggiungo l'utente ai disponibili
                    utentiModel.addElement(utente);
                    //rimuovo l'utente dai partecipanti
                    partecipantiModel.remove(indice);
                }
            }
        });

    }




    private void createUIComponents() {

        setInfoIcon();

        setSpinner();

        setJlist();

    }

    private void setJlist(){
        //db
        final Mysql db = new Mysql();
        //scarica la lista degli utenti e li metto in un array di stringhe
        listaUtenti = db.selectUtenti();

        //inizializzo i modelli per le jlist
        utentiModel = new DefaultListModel();
        partecipantiModel = new DefaultListModel();


        //aggiungo al modello utenti gli elementi dell'array
        for(String utente : listaUtenti){
            utentiModel.addElement(utente);
        }

        //creo le jlist dal modello
        utentiList = new JList(utentiModel);
        partecipantiList = new JList(partecipantiModel);

        //permette una selezione alla volta anche premendo CTRL
        utentiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partecipantiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void setSpinner(){
        //costruttore spinnermodel-->(valore da visualizzare, min, max, incremento)
        SpinnerNumberModel inizioModel = new SpinnerNumberModel(1, 1, 38, 1);
        //creo il jspinner dal modello
        inizioSpinner = new JSpinner(inizioModel);

        SpinnerNumberModel fineModel = new SpinnerNumberModel(38, 1, 38, 1);
        fineSpinner = new JSpinner(fineModel);

        SpinnerNumberModel creditiModel = new SpinnerNumberModel(800, 1, 2000, 10);
        creditiSpinner = new JSpinner(creditiModel);

        SpinnerNumberModel limiteModel = new SpinnerNumberModel(30,0,360,1);
        limiteSpinner = new JSpinner(limiteModel);

        SpinnerNumberModel primafModel = new SpinnerNumberModel(66,50,80,1);
        primafSpinner = new JSpinner(primafModel);

        SpinnerNumberModel fasceModel = new SpinnerNumberModel(6,1,10,1);
        fasceSpinner = new JSpinner(fasceModel);

        SpinnerNumberModel bonuscModel = new SpinnerNumberModel(0,0,5,1);
        bonuscSpinner = new JSpinner(bonuscModel);
    }


    private void setInfoIcon(){
        //icona di info
        ImageIcon icon = (ImageIcon) UIManager.getIcon("OptionPane.informationIcon");

        //è un label senza testo, con solo l'icona di info
        nomeInfo = new JLabel(icon);

        //quando il mouse è sopra l'icona spiega cosa bisogna fare
        nomeInfo.setToolTipText("Nome del campionato");


        numeroInfo = new JLabel(icon);
        numeroInfo.setToolTipText("Numero dei partecipanti al campionato");

        astaInfo = new JLabel(icon);
        astaInfo.setToolTipText("<html>Se si sceglie live l'asta verrà fatta tramite l'applicazione.<br> Se si sceglie offline bisogna inserire manualmente le singole rose</html>");

        campionatoInfo = new JLabel(icon);
        campionatoInfo.setToolTipText("<html>Se si sceglie pubblico chiunque si può iscrivere al campionato.<br> Se si sceglie privato solo il presidente di lega può iscrivere.</html>");

        inizioInfo = new JLabel(icon);
        inizioInfo.setToolTipText("Giornata di inizio del fantacampionato rispetto alla giornata del campionato di Serie A");

        //int fineConsigliata = 38 - (38 % ((Integer) numeroBox.getSelectedItem()-1)) - ((Integer) inizioSpinner.getValue()-1);

        fineInfo = new JLabel(icon);
        fineInfo.setToolTipText("<html>Giornata di fine consigliata per far giocare ad ogni giocatore<br> lo stesso numero di volte contro gli altri giocatori:</html>");

        creditiInfo = new JLabel(icon);
        creditiInfo.setToolTipText("Crediti Iniziali");

        limiteInfo = new JLabel(icon);
        limiteInfo.setToolTipText("<html>Numero di minuti prima della prima partita di <br>ogni giornata entro cui bisogna inviare la formazione.</html>");

        primafInfo = new JLabel(icon);
        primafInfo.setToolTipText("Valore prima fascia gol. Consigliato: 66");

        fasceInfo = new JLabel(icon);
        fasceInfo.setToolTipText("Valore larghezza fasce gol. Consigliato: 4 o 6");

        bcasaInfo = new JLabel(icon);
        bcasaInfo.setToolTipText("Lasciare a zero se non si vuole il bonus casa.");

    }
}
