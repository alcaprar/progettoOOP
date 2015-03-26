package interfacce;

import classi.*;
import db.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Christian on 11/03/2015.
 */
public class Formazione extends JPanel implements ItemListener {

    private JPanel mainPanel;
    private JPanel cards;
    private JComboBox scegliModulo;
    private JPanel pCombobox;
    private JPanel p343;
    private JPanel p352;
    private JPanel p433;
    private JPanel p442;
    private JPanel p451;
    private JPanel p532;
    private JPanel p541;
    private JList lPortieri;
    private JScrollPane pPortieri;
    private JScrollPane pDifensori;
    private JList lDifensori;
    private JScrollPane pCentrocampisti;
    private JList lCentrocampisti;
    private JScrollPane pAttaccanti;
    private JList lAttaccanti;
    //pannello 3-4-3
    private JLabel p343PorLabel;
    private JLabel p343DifLabel1;
    private JLabel p343DifLabel2;
    private JLabel p343DifLabel3;
    private JLabel p343CenLabel1;
    private JLabel p343CenLabel2;
    private JLabel p343CenLabel3;
    private JLabel p343CenLabel4;
    private JLabel p343AttLabel1;
    private JLabel p343AttLabel2;
    private JLabel p343AttLabel3;
    private JLabel p352PorLabel;
    private JLabel p352DifLabel1;
    private JLabel p352DifLabel2;
    private JLabel p352DifLabel3;
    private JLabel p352CenLabel1;
    private JLabel p352CenLabel2;
    private JLabel p352CenLabel3;
    private JLabel p352CenLabel4;
    private JLabel p352CenLabel5;
    private JLabel p352AttLabel1;
    private JLabel p352AttLabel2;
    private JLabel panPorLabel;
    private JLabel panDifLabel1;
    private JLabel panDifLabel2;
    private JLabel panCenLabel1;
    private JLabel panCenLabel2;
    private JLabel panAttLabel1;
    private JLabel panAttLabel2;
    private JLabel p433PorLabel;
    private JLabel p433DifLabel1;
    private JLabel p433DifLabel2;
    private JLabel p433DifLabel3;
    private JLabel p433DifLabel4;
    private JLabel p433CenLabel1;
    private JLabel p433CenLabel2;
    private JLabel p433CenLabel3;
    private JLabel p433AttLabel1;
    private JLabel p433AttLabel2;
    private JLabel p433AttLabel3;
    private JLabel p442PorLabel;
    private JLabel p442DifLabel1;
    private JLabel p442DifLabel2;
    private JLabel p442DifLabel3;
    private JLabel p442DifLabel4;
    private JLabel p442CenLabel1;
    private JLabel p442CenLabel2;
    private JLabel p442CenLabel3;
    private JLabel p442CenLabel4;
    private JLabel p442AttLabel1;
    private JLabel p442AttLabel2;
    private JLabel p451PorLabel;
    private JLabel p451DifLabel1;
    private JLabel p451DifLabel2;
    private JLabel p451DifLabel3;
    private JLabel p451DifLabel4;
    private JLabel p451CenLabel1;
    private JLabel p451CenLabel2;
    private JLabel p451CenLabel3;
    private JLabel p451CenLabel4;
    private JLabel p451CenLabel5;
    private JLabel p451AttLabel;
    private JLabel p532PorLabel;
    private JLabel p532DifLabel1;
    private JLabel p532DifLabel2;
    private JLabel p532DifLabel3;
    private JLabel p532DifLabel4;
    private JLabel p532DifLabel5;
    private JLabel p532CenLabel1;
    private JLabel p532CenLabel2;
    private JLabel p532CenLabel3;
    private JLabel p532AttLabel1;
    private JLabel p532AttLabel2;
    private JLabel p541PorLabel;
    private JLabel p541DifLabel1;
    private JLabel p541DifLabel2;
    private JLabel p541DifLabel3;
    private JLabel p541DifLabel4;
    private JLabel p541DifLabel5;
    private JLabel p541CenLabel1;
    private JLabel p541CenLabel2;
    private JLabel p541CenLabel3;
    private JLabel p541CenLabel4;
    private JLabel p541AttLabel;
    private JButton p343Button;
    private JButton p352Button;
    private JButton p433Button;
    private JButton p442Button;
    private JButton p451Button;
    private JButton p532Button;
    private JButton p541Button;

    private DefaultListModel listModelP = new DefaultListModel<String>();
    private DefaultListModel listModelD = new DefaultListModel<String>();
    private DefaultListModel listModelC = new DefaultListModel<String>();
    private DefaultListModel listModelA = new DefaultListModel<String>();

    private ArrayList<Giocatore> giocatori;
    private ArrayList<Giocatore> formazione = new ArrayList<Giocatore>();
    private Squadra squadra;
    private Partita partita;
    private int counter;
    private final Mysql db = new Mysql();


    public Formazione() {

        setVisible(true);

        //inizializza il counter per il check sulla formazione
        counter = 0;

        //metodo per popolare le liste di giocatori
        creaListe();

        //Per ogni etichetta del pannello viene generato un Mouselistener per controllare il riempimento della formazione mediante
        //il click del mouse
        ins343();
        ins352();
        ins433();
        ins442();
        ins451();
        ins532();
        ins541();

        //gestione etichette panchina
        inspan();
    }

    //creazione custom dei componenti
    private void createUIComponents() {
        //inizializzazione del combobox con i moduli permessi ed itemListener
        scegliModulo = new JComboBox();
        scegliModulo.addItemListener(this);
    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        //quando viene cambiato l'oggetto selezionato nel combobox viene visualizzato il relativo pannello
        //e vengono resettate le liste giocatori, le etichette ed il counter per la verifica della corretta
        //immissione della formazione
        CardLayout c1 = (CardLayout) (cards.getLayout());
        c1.show(cards, (String) itemEvent.getItem());
        refresh();
        resetLabel();
        counter = 0;
    }

    /*metodo per la creazione delle liste
    prende come parametro la squadra di cui si vuole stabilire la formazione da schierare
    inserisce poi le stringhe dei cognomi dei giocatori nelle differenti liste in base al ruolo che coprono*/
    private void creaListe(){
        //inizializza le liste dei giocatori a valori di default nel caso in cui non siano stati inseriti
        //i giocatori nelle squadre dal presidente di lega
        String[] gioc = {"Por1", "Por2", "Por3", "Dif1", "Dif2", "Dif3", "Dif4", "Dif5", "Dif6", "Dif7", "Dif8", "Cen1", "Cen2", "Cen3", "Cen4", "Cen5", "Cen6", "Cen7", "Cen8", "Att1", "Att2", "Att3", "Att4", "Att5", "Att6"};
        lPortieri.setModel(listModelP);
        for(int i = 0; i < 3; i++) listModelP.addElement(gioc[i]);
        lDifensori.setModel(listModelD);
        for(int i = 3; i < 11; i++) listModelD.addElement(gioc[i]);
        lCentrocampisti.setModel(listModelC);
        for(int i = 11; i < 19; i++) listModelC.addElement(gioc[i]);
        lAttaccanti.setModel(listModelA);
        for(int i = 19; i < 25; i++) listModelA.addElement(gioc[i]);
    }

    //metodo per resettare i label
    private void resetLabel(){
        //reset etichette modulo 343
        p343PorLabel.setText("Portiere");
        p343DifLabel1.setText("Difensore");
        p343DifLabel2.setText("Difensore");
        p343DifLabel3.setText("Difensore");
        p343CenLabel1.setText("Centrocampista");
        p343CenLabel2.setText("Centrocampista");
        p343CenLabel3.setText("Centrocampista");
        p343CenLabel4.setText("Centrocampista");
        p343AttLabel1.setText("Attaccante");
        p343AttLabel2.setText("Attaccante");
        p343AttLabel3.setText("Attaccante");
        //reset modulo etichette 352
        p352PorLabel.setText("Portiere");
        p352DifLabel1.setText("Difensore");
        p352DifLabel2.setText("Difensore");
        p352DifLabel3.setText("Difensore");
        p352CenLabel1.setText("Centrocampista");
        p352CenLabel2.setText("Centrocampista");
        p352CenLabel3.setText("Centrocampista");
        p352CenLabel4.setText("Centrocampista");
        p352CenLabel5.setText("Centrocampista");
        p352AttLabel1.setText("Attaccante");
        p352AttLabel2.setText("Attaccante");
        //reset etichette modulo 433
        p433PorLabel.setText("Portiere");
        p433DifLabel1.setText("Difensore");
        p433DifLabel2.setText("Difensore");
        p433DifLabel3.setText("Difensore");
        p433DifLabel4.setText("Difensore");
        p433CenLabel1.setText("Centrocampista");
        p433CenLabel2.setText("Centrocampista");
        p433CenLabel3.setText("Centrocampista");
        p433AttLabel1.setText("Attaccante");
        p433AttLabel2.setText("Attaccante");
        p433AttLabel3.setText("Attaccante");
        //reset etichette modulo 442
        p442PorLabel.setText("Portiere");
        p442DifLabel1.setText("Difensore");
        p442DifLabel2.setText("Difensore");
        p442DifLabel3.setText("Difensore");
        p442DifLabel4.setText("Difensore");
        p442CenLabel1.setText("Centrocampista");
        p442CenLabel2.setText("Centrocampista");
        p442CenLabel3.setText("Centrocampista");
        p442CenLabel4.setText("Centrocampista");
        p442AttLabel1.setText("Attaccante");
        p442AttLabel2.setText("Attaccante");
        //reset etichette modulo 451
        p451PorLabel.setText("Portiere");
        p451DifLabel1.setText("Difensore");
        p451DifLabel2.setText("Difensore");
        p451DifLabel3.setText("Difensore");
        p451DifLabel4.setText("Difensore");
        p451CenLabel1.setText("Centrocampista");
        p451CenLabel2.setText("Centrocampista");
        p451CenLabel3.setText("Centrocampista");
        p451CenLabel4.setText("Centrocampista");
        p451CenLabel5.setText("Centrocampista");
        p451AttLabel.setText("Attaccante");
        //reset etichiette modulo 532
        p532PorLabel.setText("Portiere");
        p532DifLabel1.setText("Difensore");
        p532DifLabel2.setText("Difensore");
        p532DifLabel3.setText("Difensore");
        p532DifLabel4.setText("Difensore");
        p532DifLabel5.setText("Difensore");
        p532CenLabel1.setText("Centrocampista");
        p532CenLabel2.setText("Centrocampista");
        p532CenLabel3.setText("Centrocampista");
        p532AttLabel1.setText("Attaccante");
        p532AttLabel2.setText("Attaccante");
        //reset etichette modulo 541
        p541PorLabel.setText("Portiere");
        p541DifLabel1.setText("Difensore");
        p541DifLabel2.setText("Difensore");
        p541DifLabel3.setText("Difensore");
        p541DifLabel4.setText("Difensore");
        p541DifLabel5.setText("Difensore");
        p541CenLabel1.setText("Centrocampista");
        p541CenLabel2.setText("Centrocampista");
        p541CenLabel3.setText("Centrocampista");
        p541CenLabel4.setText("Centrocampista");
        p541AttLabel.setText("Attaccante");
        //reset panchina
        panPorLabel.setText("Portiere");
        panDifLabel1.setText("Difensore");
        panDifLabel2.setText("Difensore");
        panCenLabel1.setText("Centrocampista");
        panCenLabel2.setText("Centrocampista");
        panAttLabel1.setText("Attaccante");
        panAttLabel2.setText("Attaccante");
    }

    //gestori dell'inserimento dei giocatori, viene commentato solo un giocatore, il procedimento è il medesimo per
    //ogni etichetta
    private void ins343(){
        //azione al click del mouse
        p343PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //controlla che sia selezionato un portiere nella lista dei giocatori della rosa
                if (!lPortieri.isSelectionEmpty()){
                    //controlla se l'etichetta è quella di default oppure c'è già stato un inserimento
                    if(p343PorLabel.getText().equals("Portiere")){
                        //imposta l'etichetta per visualizzare il cognome del giocatore scelto
                        p343PorLabel.setText((String) lPortieri.getSelectedValue());
                        //aumenta il counter poichè si è inserito un giocatore
                        counter++;
                    }
                    else {
                        //se l'etichetta non è quella di default aggiunge nuovamente alla lista il giocatore che si
                        //sta sostituendo
                        listModelP.add(0, p343PorLabel.getText());
                        //inserisce il nuovo giocatore(imposta la label)
                        p343PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    //rimuove il giocatore inserito dalla lista
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                //se non è selezionato un portiere nella lista e l'etichetta è quella di default non fa nulla
                else if(!p343PorLabel.getText().equals("Portiere")){
                    //se non è quella di default aggiunge nuovamente il giocatore già inserito nella lista di appartenenza
                    listModelP.add(0, p343PorLabel.getText());
                    //reimposta a default l'etichetta
                    p343PorLabel.setText("Portiere");
                    //decrementa il contatore poichè è stato eliminato un giocatore dalla formazione
                    counter--;
                }
                //annulla la selezione nella lista
                lPortieri.clearSelection();
            }
        });
        p343DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p343DifLabel1.getText().equals("Difensore")){
                        p343DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p343DifLabel1.getText());
                        p343DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p343DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p343DifLabel1.getText());
                    p343DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p343DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p343DifLabel2.getText().equals("Difensore")){
                        p343DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p343DifLabel2.getText());
                        p343DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p343DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p343DifLabel2.getText());
                    p343DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p343DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p343DifLabel3.getText().equals("Difensore")){
                        p343DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p343DifLabel3.getText());
                        p343DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p343DifLabel3.getText().equals("Difensore")){
                    listModelD.add(0, p343DifLabel3.getText());
                    p343DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p343CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p343CenLabel1.getText().equals("Centrocampista")){
                        p343CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p343CenLabel1.getText());
                        p343CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                }
                else if (!p343CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p343CenLabel1.getText());
                    p343CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p343CenLabel2.getText().equals("Centrocampista")){
                        p343CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p343CenLabel2.getText());
                        p343CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p343CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p343CenLabel2.getText());
                    p343CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p343CenLabel3.getText().equals("Centrocampista")){
                        p343CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p343CenLabel3.getText());
                        p343CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                }  else if (!p343CenLabel3.getText().equals("Centrocampista")) {
                    listModelC.add(0, p343CenLabel3.getText());
                    p343CenLabel3.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p343CenLabel4.getText().equals("Centrocampista")){
                        p343CenLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p343CenLabel4.getText());
                        p343CenLabel4.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                }  else if (!p343CenLabel4.getText().equals("Centrocampista")) {
                    listModelC.add(0, p343CenLabel4.getText());
                    p343CenLabel4.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p343AttLabel1.getText().equals("Attaccante")){
                        p343AttLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p343AttLabel1.getText());
                        p343AttLabel1.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p343AttLabel1.getText().equals("Attaccante")) {
                    listModelA.add(0, p343AttLabel1.getText());
                    p343AttLabel1.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p343AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p343AttLabel2.getText().equals("Attaccante")){
                        p343AttLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p343AttLabel2.getText());
                        p343AttLabel2.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p343AttLabel2.getText().equals("Attaccante")) {
                    listModelA.add(0, p343AttLabel2.getText());
                    p343AttLabel2.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p343AttLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p343AttLabel3.getText().equals("Attaccante")){
                        p343AttLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p343AttLabel3.getText());
                        p343AttLabel3.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p343AttLabel3.getText().equals("Attaccante")) {
                    listModelA.add(0, p343AttLabel3.getText());
                    p343AttLabel3.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        //pulsante per confermare la formazione per la prossima giornata
        p343Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //controlla che i giocatori siano stati inseriti nelle squadre (ovver che il presidente di lega abbia
                //popolato le rose) e che siano stati inseriti 18 giocatori nella formazione
                if (counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    //per ogni label cerca la corrispondenza ed inserisce il giocatore nel vettore della formazione
                    formazione.add(0, cercaGiocatore(p343PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p343DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p343DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p343DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p343CenLabel1.getText()));
                    formazione.add(5, cercaGiocatore(p343CenLabel2.getText()));
                    formazione.add(6, cercaGiocatore(p343CenLabel3.getText()));
                    formazione.add(7, cercaGiocatore(p343CenLabel4.getText()));
                    formazione.add(8, cercaGiocatore(p343AttLabel1.getText()));
                    formazione.add(9, cercaGiocatore(p343AttLabel2.getText()));
                    formazione.add(10, cercaGiocatore(p343AttLabel3.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    //crea l'oggetto formazione che contiene il vettore di giocatori ed il modulo scelto
                    classi.Formazione form = new classi.Formazione(formazione, "3-4-3");
                    //associa la formazione appena creata alla squadra
                    squadra.setFormazione(form);
                    //salva sul database la formazione
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    //se non sono ancora stati inseriti i giocatori nelle squadre visualizza un errore
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    //ugualmente se counter non è uguale a 18 visualizza un errore
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void ins352(){
        p352PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(p352PorLabel.getText().equals("Portiere")){
                        p352PorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, p352PorLabel.getText());
                        p352PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!p352PorLabel.getText().equals("Portiere")){
                    listModelP.add(0, p352PorLabel.getText());
                    p352PorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        p352DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p352DifLabel1.getText().equals("Difensore")){
                        p352DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p352DifLabel1.getText());
                        p352DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p352DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p352DifLabel1.getText());
                    p352DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p352DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p352DifLabel2.getText().equals("Difensore")){
                        p352DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p352DifLabel2.getText());
                        p352DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p352DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p352DifLabel2.getText());
                    p352DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p352DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p352DifLabel3.getText().equals("Difensore")){
                        p352DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p352DifLabel3.getText());
                        p352DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p352DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p352DifLabel3.getText());
                    p352DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p352CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p352CenLabel1.getText().equals("Centrocampista")){
                        p352CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p352CenLabel1.getText());
                        p352CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p352CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p352CenLabel1.getText());
                    p352CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p352CenLabel2.getText().equals("Centrocampista")){
                        p352CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p352CenLabel2.getText());
                        p352CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p352CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p352CenLabel2.getText());
                    p352CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p352CenLabel3.getText().equals("Centrocampista")){
                        p352CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p352CenLabel3.getText());
                        p352CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p352CenLabel3.getText().equals("Centrocampista")) {
                    listModelC.add(0, p352CenLabel3.getText());
                    p352CenLabel3.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p352CenLabel4.getText().equals("Centrocampista")){
                        p352CenLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p352CenLabel4.getText());
                        p352CenLabel4.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p352CenLabel4.getText().equals("Centrocampista")) {
                    listModelC.add(0, p352CenLabel4.getText());
                    p352CenLabel4.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p352CenLabel5.getText().equals("Centrocampista")){
                        p352CenLabel5.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p352CenLabel5.getText());
                        p352CenLabel5.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p352CenLabel5.getText().equals("Centrocampista")) {
                    listModelC.add(0, p352CenLabel5.getText());
                    p352CenLabel5.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p352AttLabel1.getText().equals("Attaccante")){
                        p352AttLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p352AttLabel1.getText());
                        p352AttLabel1.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p352AttLabel1.getText().equals("Attaccante")) {
                    listModelA.add(0, p352AttLabel1.getText());
                    p352AttLabel1.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p352AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p352AttLabel2.getText().equals("Attaccante")){
                        p352AttLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p352AttLabel2.getText());
                        p352AttLabel2.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p352AttLabel2.getText().equals("Attaccante")) {
                    listModelA.add(0, p352AttLabel2.getText());
                    p352AttLabel2.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p352Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    formazione.add(0, cercaGiocatore(p352PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p352DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p352DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p352DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p352CenLabel1.getText()));
                    formazione.add(5, cercaGiocatore(p352CenLabel2.getText()));
                    formazione.add(6, cercaGiocatore(p352CenLabel3.getText()));
                    formazione.add(7, cercaGiocatore(p352CenLabel4.getText()));
                    formazione.add(8, cercaGiocatore(p352CenLabel5.getText()));
                    formazione.add(9, cercaGiocatore(p352AttLabel1.getText()));
                    formazione.add(10, cercaGiocatore(p352AttLabel2.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    classi.Formazione form = new classi.Formazione(formazione, "3-5-2");
                    squadra.setFormazione(form);
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void ins433(){
        p433PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(p433PorLabel.getText().equals("Portiere")){
                        p433PorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, p433PorLabel.getText());
                        p433PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!p433PorLabel.getText().equals("Portiere")){
                    listModelP.add(0, p433PorLabel.getText());
                    p433PorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        p433DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p433DifLabel1.getText().equals("Difensore")){
                        p433DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p433DifLabel1.getText());
                        p433DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p433DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p433DifLabel1.getText());
                    p433DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p433DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p433DifLabel2.getText().equals("Difensore")){
                        p433DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p433DifLabel2.getText());
                        p433DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p433DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p433DifLabel2.getText());
                    p433DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p433DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p433DifLabel3.getText().equals("Difensore")){
                        p433DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p433DifLabel3.getText());
                        p433DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p433DifLabel3.getText().equals("Difensore")){
                    listModelD.add(0, p433DifLabel3.getText());
                    p433DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p433DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p433DifLabel4.getText().equals("Difensore")){
                        p433DifLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p433DifLabel4.getText());
                        p433DifLabel4.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p433DifLabel4.getText().equals("Difensore")){
                    listModelD.add(0, p433DifLabel4.getText());
                    p433DifLabel4.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p433CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p433CenLabel1.getText().equals("Centrocampista")){
                        p433CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p433CenLabel1.getText());
                        p433CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p433CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p433CenLabel1.getText());
                    p433CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p433CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p433CenLabel2.getText().equals("Centrocampista")){
                        p433CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p433CenLabel2.getText());
                        p433CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p433CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p433CenLabel2.getText());
                    p433CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p433CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p433CenLabel3.getText().equals("Centrocampista")){
                        p433CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p433CenLabel3.getText());
                        p433CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p433CenLabel3.getText().equals("Centrocampista")) {
                    listModelC.add(0, p433CenLabel3.getText());
                    p433CenLabel3.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p433AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p433AttLabel1.getText().equals("Attaccante")){
                        p433AttLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p433AttLabel1.getText());
                        p433AttLabel1.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p433AttLabel1.getText().equals("Attaccante")) {
                    listModelA.add(0, p433AttLabel1.getText());
                    p433AttLabel1.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p433AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p433AttLabel2.getText().equals("Attaccante")){
                        p433AttLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p433AttLabel2.getText());
                        p433AttLabel2.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p433AttLabel2.getText().equals("Attaccante")) {
                    listModelA.add(0, p433AttLabel2.getText());
                    p433AttLabel2.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p433AttLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p433AttLabel3.getText().equals("Attaccante")){
                        p433AttLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p433AttLabel3.getText());
                        p433AttLabel3.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p433AttLabel3.getText().equals("Attaccante")) {
                    listModelA.add(0, p433AttLabel3.getText());
                    p433AttLabel3.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p433Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    formazione.add(0, cercaGiocatore(p433PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p433DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p433DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p433DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p433DifLabel4.getText()));
                    formazione.add(5, cercaGiocatore(p433CenLabel1.getText()));
                    formazione.add(6, cercaGiocatore(p433CenLabel2.getText()));
                    formazione.add(7, cercaGiocatore(p433CenLabel3.getText()));
                    formazione.add(8, cercaGiocatore(p433AttLabel1.getText()));
                    formazione.add(9, cercaGiocatore(p433AttLabel2.getText()));
                    formazione.add(10, cercaGiocatore(p433AttLabel3.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    classi.Formazione form = new classi.Formazione(formazione, "4-3-3");
                    squadra.setFormazione(form);
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void ins442(){
        p442PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(p442PorLabel.getText().equals("Portiere")){
                        p442PorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, p442PorLabel.getText());
                        p442PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!p442PorLabel.getText().equals("Portiere")){
                    listModelP.add(0, p442PorLabel.getText());
                    p442PorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        p442DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p442DifLabel1.getText().equals("Difensore")){
                        p442DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p442DifLabel1.getText());
                        p442DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p442DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p442DifLabel1.getText());
                    p442DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p442DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p442DifLabel2.getText().equals("Difensore")){
                        p442DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p442DifLabel2.getText());
                        p442DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p442DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p442DifLabel2.getText());
                    p442DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p442DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p442DifLabel3.getText().equals("Difensore")){
                        p442DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p442DifLabel3.getText());
                        p442DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p442DifLabel3.getText().equals("Difensore")){
                    listModelD.add(0, p442DifLabel3.getText());
                    p442DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p442DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p442DifLabel4.getText().equals("Difensore")){
                        p442DifLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p442DifLabel4.getText());
                        p442DifLabel4.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p442DifLabel4.getText().equals("Difensore")){
                    listModelD.add(0, p442DifLabel4.getText());
                    p442DifLabel4.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p442CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p442CenLabel1.getText().equals("Centrocampista")){
                        p442CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p442CenLabel1.getText());
                        p442CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p442CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p442CenLabel1.getText());
                    p442CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p442CenLabel2.getText().equals("Centrocampista")){
                        p442CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p442CenLabel2.getText());
                        p442CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p442CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p442CenLabel2.getText());
                    p442CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p442CenLabel3.getText().equals("Centrocampista")){
                        p442CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p442CenLabel3.getText());
                        p442CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p442CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p442CenLabel1.getText());
                    p442CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p442CenLabel4.getText().equals("Centrocampista")){
                        p442CenLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p442CenLabel4.getText());
                        p442CenLabel4.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p442CenLabel4.getText().equals("Centrocampista")) {
                    listModelC.add(0, p442CenLabel4.getText());
                    p442CenLabel4.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p442AttLabel1.getText().equals("Attaccante")){
                        p442AttLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p442AttLabel1.getText());
                        p442AttLabel1.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p442AttLabel1.getText().equals("Attaccante")) {
                    listModelA.add(0, p442AttLabel1.getText());
                    p442AttLabel1.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p442AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p442AttLabel2.getText().equals("Attaccante")){
                        p442AttLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p442AttLabel2.getText());
                        p442AttLabel2.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p442AttLabel2.getText().equals("Attaccante")) {
                    listModelA.add(0, p442AttLabel2.getText());
                    p442AttLabel2.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p442Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    formazione.add(0, cercaGiocatore(p442PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p442DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p442DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p442DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p442DifLabel4.getText()));
                    formazione.add(5, cercaGiocatore(p442CenLabel1.getText()));
                    formazione.add(6, cercaGiocatore(p442CenLabel2.getText()));
                    formazione.add(7, cercaGiocatore(p442CenLabel3.getText()));
                    formazione.add(8, cercaGiocatore(p442CenLabel4.getText()));
                    formazione.add(9, cercaGiocatore(p442AttLabel1.getText()));
                    formazione.add(10, cercaGiocatore(p442AttLabel2.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    classi.Formazione form = new classi.Formazione(formazione, "4-4-2");
                    squadra.setFormazione(form);
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void ins451(){
        p451PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(p451PorLabel.getText().equals("Portiere")){
                        p451PorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, p451PorLabel.getText());
                        p451PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!p451PorLabel.getText().equals("Portiere")){
                    listModelP.add(0, p451PorLabel.getText());
                    p451PorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        p451DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p451DifLabel1.getText().equals("Difensore")){
                        p451DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p451DifLabel1.getText());
                        p451DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p451DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p451DifLabel1.getText());
                    p451DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p451DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p451DifLabel2.getText().equals("Difensore")){
                        p451DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p451DifLabel2.getText());
                        p451DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p451DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p451DifLabel2.getText());
                    p451DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p451DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p451DifLabel3.getText().equals("Difensore")){
                        p451DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p451DifLabel3.getText());
                        p451DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p451DifLabel3.getText().equals("Difensore")){
                    listModelD.add(0, p451DifLabel3.getText());
                    p451DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p451DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p451DifLabel4.getText().equals("Difensore")){
                        p451DifLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p451DifLabel4.getText());
                        p451DifLabel4.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p451DifLabel4.getText().equals("Difensore")){
                    listModelD.add(0, p451DifLabel4.getText());
                    p451DifLabel4.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p451CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p451CenLabel1.getText().equals("Centrocampista")){
                        p451CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p451CenLabel1.getText());
                        p451CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p451CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p451CenLabel1.getText());
                    p451CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p451CenLabel2.getText().equals("Centrocampista")){
                        p451CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p451CenLabel2.getText());
                        p451CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p451CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p442CenLabel2.getText());
                    p451CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p451CenLabel3.getText().equals("Centrocampista")){
                        p451CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p451CenLabel3.getText());
                        p451CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p451CenLabel3.getText().equals("Centrocampista")) {
                    listModelC.add(0, p451CenLabel3.getText());
                    p451CenLabel3.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p451CenLabel4.getText().equals("Centrocampista")){
                        p451CenLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p451CenLabel4.getText());
                        p451CenLabel4.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p451CenLabel4.getText().equals("Centrocampista")) {
                    listModelC.add(0, p451CenLabel4.getText());
                    p451CenLabel4.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p451CenLabel5.getText().equals("Centrocampista")){
                        p451CenLabel5.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p451CenLabel5.getText());
                        p451CenLabel5.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p451CenLabel5.getText().equals("Centrocampista")) {
                    listModelC.add(0, p451CenLabel5.getText());
                    p451CenLabel5.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451AttLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p451AttLabel.getText().equals("Attaccante")){
                        p451AttLabel.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p451AttLabel.getText());
                        p451AttLabel.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p451AttLabel.getText().equals("Attaccante")) {
                    listModelA.add(0, p451AttLabel.getText());
                    p451AttLabel.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p451Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    formazione.add(0, cercaGiocatore(p451PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p451DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p451DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p451DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p451DifLabel4.getText()));
                    formazione.add(5, cercaGiocatore(p451CenLabel1.getText()));
                    formazione.add(6, cercaGiocatore(p451CenLabel2.getText()));
                    formazione.add(7, cercaGiocatore(p451CenLabel3.getText()));
                    formazione.add(8, cercaGiocatore(p451CenLabel4.getText()));
                    formazione.add(9, cercaGiocatore(p451CenLabel5.getText()));
                    formazione.add(10, cercaGiocatore(p451AttLabel.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    classi.Formazione form = new classi.Formazione(formazione, "4-5-1");
                    squadra.setFormazione(form);
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void ins532(){
        p532PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(p532PorLabel.getText().equals("Portiere")){
                        p532PorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, p532PorLabel.getText());
                        p532PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!p532PorLabel.getText().equals("Portiere")){
                    listModelP.add(0, p532PorLabel.getText());
                    p532PorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        p532DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p532DifLabel1.getText().equals("Difensore")){
                        p532DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p532DifLabel1.getText());
                        p532DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p532DifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, p532DifLabel1.getText());
                    p532DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p532DifLabel2.getText().equals("Difensore")){
                        p532DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p532DifLabel2.getText());
                        p532DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p532DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p532DifLabel2.getText());
                    p532DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p532DifLabel3.getText().equals("Difensore")){
                        p532DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p532DifLabel3.getText());
                        p532DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p532DifLabel3.getText().equals("Difensore")){
                    listModelD.add(0, p532DifLabel3.getText());
                    p532DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p532DifLabel4.getText().equals("Difensore")){
                        p532DifLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p532DifLabel4.getText());
                        p532DifLabel4.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p532DifLabel4.getText().equals("Difensore")){
                    listModelD.add(0, p532DifLabel4.getText());
                    p532DifLabel4.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p532DifLabel5.getText().equals("Difensore")){
                        p532DifLabel5.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p532DifLabel5.getText());
                        p532DifLabel5.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p532DifLabel5.getText().equals("Difensore")){
                    listModelD.add(0, p532DifLabel5.getText());
                    p532DifLabel5.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p532CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p532CenLabel1.getText().equals("Centrocampista")){
                        p532CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p532CenLabel1.getText());
                        p532CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p532CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p532CenLabel1.getText());
                    p532CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p532CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p532CenLabel2.getText().equals("Centrocampista")){
                        p532CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p532CenLabel2.getText());
                        p532CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p532CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p532CenLabel2.getText());
                    p532CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p532CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p532CenLabel3.getText().equals("Centrocampista")){
                        p532CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p532CenLabel3.getText());
                        p532CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p532CenLabel3.getText().equals("Centrocampista")) {
                    listModelC.add(0, p532CenLabel3.getText());
                    p532CenLabel3.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p532AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p532AttLabel1.getText().equals("Attaccante")){
                        p532AttLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p532AttLabel1.getText());
                        p532AttLabel1.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p532AttLabel1.getText().equals("Attaccante")) {
                    listModelA.add(0, p532AttLabel1.getText());
                    p532AttLabel1.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p532AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p532AttLabel2.getText().equals("Attaccante")){
                        p532AttLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p532AttLabel2.getText());
                        p532AttLabel2.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p532AttLabel2.getText().equals("Attaccante")) {
                    listModelA.add(0, p532AttLabel2.getText());
                    p532AttLabel2.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p532Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    formazione.add(0, cercaGiocatore(p532PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p532DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p532DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p532DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p532DifLabel4.getText()));
                    formazione.add(5, cercaGiocatore(p532DifLabel5.getText()));
                    formazione.add(6, cercaGiocatore(p532CenLabel1.getText()));
                    formazione.add(7, cercaGiocatore(p532CenLabel2.getText()));
                    formazione.add(8, cercaGiocatore(p532CenLabel3.getText()));
                    formazione.add(9, cercaGiocatore(p532AttLabel1.getText()));
                    formazione.add(10, cercaGiocatore(p532AttLabel2.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    classi.Formazione form = new classi.Formazione(formazione, "5-3-2");
                    squadra.setFormazione(form);
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void ins541(){
        p541PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(p541PorLabel.getText().equals("Portiere")){
                        p541PorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, p541PorLabel.getText());
                        p541PorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!p541PorLabel.getText().equals("Portiere")){
                    listModelP.add(0, p541PorLabel.getText());
                    p541PorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        p541DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p541DifLabel1.getText().equals("Difensore")){
                        p541DifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p541DifLabel1.getText());
                        p541DifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                } else if (!p541DifLabel1.getText().equals("Difensore")) {
                    listModelD.add(0, p541DifLabel1.getText());
                    p541DifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p541DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p541DifLabel2.getText().equals("Difensore")){
                        p541DifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p541DifLabel2.getText());
                        p541DifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p541DifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, p541DifLabel2.getText());
                    p541DifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p541DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p541DifLabel3.getText().equals("Difensore")){
                        p541DifLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p541DifLabel3.getText());
                        p541DifLabel3.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p541DifLabel3.getText().equals("Difensore")){
                    listModelD.add(0, p541DifLabel3.getText());
                    p541DifLabel3.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p541DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p541DifLabel4.getText().equals("Difensore")){
                        p541DifLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p541DifLabel4.getText());
                        p541DifLabel4.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p541DifLabel4.getText().equals("Difensore")){
                    listModelD.add(0, p541DifLabel4.getText());
                    p541DifLabel4.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p541DifLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(p541DifLabel5.getText().equals("Difensore")){
                        p541DifLabel5.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, p541DifLabel5.getText());
                        p541DifLabel5.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!p541DifLabel5.getText().equals("Difensore")){
                    listModelD.add(0, p541DifLabel5.getText());
                    p541DifLabel5.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        p541CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p541CenLabel1.getText().equals("Centrocampista")){
                        p541CenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p541CenLabel1.getText());
                        p541CenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p541CenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, p541CenLabel1.getText());
                    p541CenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p541CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p541CenLabel2.getText().equals("Centrocampista")){
                        p541CenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p541CenLabel2.getText());
                        p541CenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p541CenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, p541CenLabel2.getText());
                    p541CenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p541CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p541CenLabel3.getText().equals("Centrocampista")){
                        p541CenLabel3.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p541CenLabel3.getText());
                        p541CenLabel3.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p541CenLabel3.getText().equals("Centrocampista")) {
                    listModelC.add(0, p541CenLabel3.getText());
                    p541CenLabel3.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p541CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(p541CenLabel4.getText().equals("Centrocampista")){
                        p541CenLabel4.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, p541CenLabel4.getText());
                        p541CenLabel4.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!p541CenLabel4.getText().equals("Centrocampista")) {
                    listModelC.add(0, p541CenLabel4.getText());
                    p541CenLabel4.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        p541AttLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(p541AttLabel.getText().equals("Attaccante")){
                        p541AttLabel.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, p541AttLabel.getText());
                        p541AttLabel.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!p541AttLabel.getText().equals("Attaccante")) {
                    listModelA.add(0, p541AttLabel.getText());
                    p541AttLabel.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        p541Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(counter == 18 && !squadra.getCampionato().isGiocatoriDaInserire()) {
                    formazione.add(0, cercaGiocatore(p541PorLabel.getText()));
                    formazione.add(1, cercaGiocatore(p541DifLabel1.getText()));
                    formazione.add(2, cercaGiocatore(p541DifLabel2.getText()));
                    formazione.add(3, cercaGiocatore(p541DifLabel3.getText()));
                    formazione.add(4, cercaGiocatore(p541DifLabel4.getText()));
                    formazione.add(5, cercaGiocatore(p541DifLabel5.getText()));
                    formazione.add(6, cercaGiocatore(p541CenLabel1.getText()));
                    formazione.add(7, cercaGiocatore(p541CenLabel2.getText()));
                    formazione.add(8, cercaGiocatore(p541CenLabel3.getText()));
                    formazione.add(9, cercaGiocatore(p541CenLabel4.getText()));
                    formazione.add(10, cercaGiocatore(p541AttLabel.getText()));
                    formazione.add(11, cercaGiocatore(panPorLabel.getText()));
                    formazione.add(12, cercaGiocatore(panDifLabel1.getText()));
                    formazione.add(13, cercaGiocatore(panDifLabel2.getText()));
                    formazione.add(14, cercaGiocatore(panCenLabel1.getText()));
                    formazione.add(15, cercaGiocatore(panCenLabel2.getText()));
                    formazione.add(16, cercaGiocatore(panAttLabel1.getText()));
                    formazione.add(17, cercaGiocatore(panAttLabel2.getText()));
                    classi.Formazione form = new classi.Formazione(formazione, "5-4-1");
                    squadra.setFormazione(form);
                    if(db.inserisciFormazione(squadra, partita)){
                        JOptionPane.showMessageDialog(null, "Hai inserito correttamente la formazione", "Completato", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(null, "Non è stato possibile inserire la formazione.\nErrore MySQL", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if(squadra.getCampionato().isGiocatoriDaInserire()){
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devono essere inseriti i giocatori nella squadra.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Prima di confermare la formazione devi inserire tutti i giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void inspan(){
        panPorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!lPortieri.isSelectionEmpty()){
                    if(panPorLabel.getText().equals("Portiere")){
                        panPorLabel.setText((String) lPortieri.getSelectedValue());
                        counter++;
                    }
                    else {
                        listModelP.add(0, panPorLabel.getText());
                        panPorLabel.setText((String) lPortieri.getSelectedValue());
                    }
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(!panPorLabel.getText().equals("Portiere")){
                    listModelP.add(0, panPorLabel.getText());
                    panPorLabel.setText("Portiere");
                    counter--;
                }
                lPortieri.clearSelection();
            }
        });
        panDifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(panDifLabel1.getText().equals("Difensore")){
                        panDifLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, panDifLabel1.getText());
                        panDifLabel1.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!panDifLabel1.getText().equals("Difensore")){
                    listModelD.add(0, panDifLabel1.getText());
                    panDifLabel1.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        panDifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (!lDifensori.isSelectionEmpty()){
                    if(panDifLabel2.getText().equals("Difensore")){
                        panDifLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelD.add(0, panDifLabel2.getText());
                        panDifLabel2.setText(temp);
                    }
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(!panDifLabel2.getText().equals("Difensore")){
                    listModelD.add(0, panDifLabel2.getText());
                    panDifLabel2.setText("Difensore");
                    counter--;
                }
                lDifensori.clearSelection();
            }
        });
        panCenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(panCenLabel1.getText().equals("Centrocampista")){
                        panCenLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, panCenLabel1.getText());
                        panCenLabel1.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!panCenLabel1.getText().equals("Centrocampista")) {
                    listModelC.add(0, panCenLabel1.getText());
                    panCenLabel1.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        panCenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (!lCentrocampisti.isSelectionEmpty()) {
                    if(panCenLabel2.getText().equals("Centrocampista")){
                        panCenLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelC.add(0, panCenLabel2.getText());
                        panCenLabel2.setText(temp);
                    }
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (!panCenLabel2.getText().equals("Centrocampista")) {
                    listModelC.add(0, panCenLabel2.getText());
                    panCenLabel2.setText("Centrocampista");
                    counter--;
                }
                lCentrocampisti.clearSelection();
            }
        });
        panAttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(panAttLabel1.getText().equals("Attaccante")){
                        panAttLabel1.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, panAttLabel1.getText());
                        panAttLabel1.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!panAttLabel1.getText().equals("Attaccante")) {
                    listModelA.add(0, panAttLabel1.getText());
                    panAttLabel1.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
        panAttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (!lAttaccanti.isSelectionEmpty()) {
                    if(panAttLabel2.getText().equals("Attaccante")){
                        panAttLabel2.setText(temp);
                        counter++;
                    }
                    else {
                        listModelA.add(0, panAttLabel2.getText());
                        panAttLabel2.setText(temp);
                    }
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (!panAttLabel2.getText().equals("Attaccante")) {
                    listModelA.add(0, panAttLabel2.getText());
                    panAttLabel2.setText("Attaccante");
                    counter--;
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    public void setSquadra(Squadra sqr){
        this.squadra = sqr;
        this.giocatori = sqr.getGiocatori();
        this.partita = sqr.prossimaPartita();
    }

    public void refresh(){
        //se la squadra è stata popolata riempie le liste con i giocatori reali sostituendo quelli di default
        if(!squadra.getCampionato().isGiocatoriDaInserire()) {
            listModelP.removeAllElements();
            listModelD.removeAllElements();
            listModelC.removeAllElements();
            listModelA.removeAllElements();

            for (Giocatore giocatore : giocatori) {
                if (giocatore.getRuolo() == 'P') listModelP.addElement(giocatore.getCognome());
                if (giocatore.getRuolo() == 'D') listModelD.addElement(giocatore.getCognome());
                if (giocatore.getRuolo() == 'C') listModelC.addElement(giocatore.getCognome());
                if (giocatore.getRuolo() == 'A') listModelA.addElement(giocatore.getCognome());
            }
        }

    }

    private Giocatore cercaGiocatore(String label){
        //dato il cognome del giocatore inserito nella formazione, cerca il giocatore corrispondente e restituisce
        //l'oggetto giocatore associato
        Giocatore giocatore = null;
        for(Giocatore g : giocatori){
            if(label.equals(g.getCognome())) giocatore = g;
        }
        return giocatore;
    }

}
