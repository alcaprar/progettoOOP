package interfacce;

import classi.Squadra;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

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

    private DefaultListModel listModelP = new DefaultListModel();
    private DefaultListModel listModelD = new DefaultListModel();
    private DefaultListModel listModelC = new DefaultListModel();
    private DefaultListModel listModelA = new DefaultListModel();


    Image bkg_panel = Toolkit.getDefaultToolkit().createImage("resources/Soccer_Field_Transparant.png");

    public Formazione() {
        //viene generato il codice di setup dell'UI e richiamato il metodo $$$setupUI$$$() come prima istruzione
        //gestisciListe(squadra);
        setVisible(true);

        //metodo per popolare le liste di giocatori
        crealiste();

        //Per ogni etichetta del pannello viene generato un Mouselistener per controllare il riempimento della formazione mediante
        //il click del mouse
        ins343();
        ins352();
        ins433();
        ins442();
        ins451();
        ins532();

        //gestione etichette panchina
        inspan();
    }


    //creazione custom dei componenti
    private void createUIComponents() {
        scegliModulo = new JComboBox();
        scegliModulo.addItemListener(this);
    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        CardLayout c1 = (CardLayout) (cards.getLayout());
        c1.show(cards, (String) itemEvent.getItem());
    }

    //metodo per la creazione delle liste
    private void crealiste(){
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

    //gestori dell'inserimento dei giocatori

    private void ins343(){
        p343PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    p343PorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(p343PorLabel.getText() != "Portiere"){
                    listModelP.add(0, p343PorLabel.getText());
                    p343PorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        p343DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p343DifLabel1.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p343DifLabel1.getText() != "Difensore"){
                    listModelD.add(0, p343DifLabel1.getText());
                    p343DifLabel1.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p343DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p343DifLabel2.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p343DifLabel2.getText() != "Difensore"){
                    listModelD.add(0, p343DifLabel2.getText());
                    p343DifLabel2.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p343DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p343DifLabel3.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p343DifLabel3.getText() != "Difensore"){
                    listModelD.add(0, p343DifLabel3.getText());
                    p343DifLabel3.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p343CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p343CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p343CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p343CenLabel1.getText());
                    p343CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p343CenLabel3.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p343CenLabel3.getText() != "Centrocampista") {
                    listModelC.add(0, p343CenLabel3.getText());
                    p343CenLabel3.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p343CenLabel3.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p343CenLabel3.getText() != "Centrocampista") {
                    listModelC.add(0, p343CenLabel3.getText());
                    p343CenLabel3.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p343CenLabel4.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p343CenLabel4.getText() != "Centrocampista") {
                    listModelC.add(0, p343CenLabel4.getText());
                    p343CenLabel4.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p343AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p343AttLabel1.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p343AttLabel1.getText() != "Attaccante") {
                    listModelA.add(0, p343AttLabel1.getText());
                    p343AttLabel1.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p343AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p343AttLabel2.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p343AttLabel2.getText() != "Attaccante") {
                    listModelA.add(0, p343AttLabel2.getText());
                    p343AttLabel2.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p343AttLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p343AttLabel3.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p343AttLabel3.getText() != "Attaccante") {
                    listModelA.add(0, p343AttLabel3.getText());
                    p343AttLabel3.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    private void ins352(){
        p352PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    p352PorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(p352PorLabel.getText() != "Portiere"){
                    listModelP.add(0, p352PorLabel.getText());
                    p352PorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        p352DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != p352DifLabel2.getText() && temp != p352DifLabel3.getText()) p352DifLabel1.setText((String)lDifensori.getSelectedValue());
                if(lDifensori.isSelectionEmpty() == false ){
                    if(temp == p352DifLabel2.getText()) {
                        p352DifLabel2.setText(p352DifLabel1.getText());
                        p352DifLabel1.setText(temp);
                    }
                    else if(temp == p352DifLabel3.getText()){
                        p352DifLabel3.setText(p352DifLabel1.getText());
                        p352DifLabel1.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) p352DifLabel1.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        p352DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != p352DifLabel1.getText() && temp != p352DifLabel3.getText()) p352DifLabel2.setText((String)lDifensori.getSelectedValue());
                if(lDifensori.isSelectionEmpty() == false ){
                    if(temp == p352DifLabel1.getText()) {
                        p352DifLabel1.setText(p352DifLabel2.getText());
                        p352DifLabel2.setText(temp);
                    }
                    else if(temp == p352DifLabel3.getText()){
                        p352DifLabel3.setText(p352DifLabel2.getText());
                        p352DifLabel2.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) p352DifLabel2.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        p352DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != p352DifLabel1.getText() && temp != p352DifLabel2.getText()) p352DifLabel3.setText((String)lDifensori.getSelectedValue());
                if(lDifensori.isSelectionEmpty() == false ){
                    if(temp == p352DifLabel1.getText()) {
                        p352DifLabel1.setText(p352DifLabel3.getText());
                        p352DifLabel3.setText(temp);
                    }
                    else if(temp == p352DifLabel2.getText()){
                        p352DifLabel2.setText(p352DifLabel3.getText());
                        p352DifLabel3.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) p352DifLabel3.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        p352CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p352CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p352CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p352CenLabel1.getText());
                    p352CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p352CenLabel2.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p352CenLabel2.getText() != "Centrocampista") {
                    listModelC.add(0, p352CenLabel2.getText());
                    p352CenLabel2.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p352CenLabel3.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p352CenLabel3.getText() != "Centrocampista") {
                    listModelC.add(0, p352CenLabel3.getText());
                    p352CenLabel3.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p352CenLabel4.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p352CenLabel4.getText() != "Centrocampista") {
                    listModelC.add(0, p352CenLabel4.getText());
                    p352CenLabel4.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p352CenLabel5.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p352CenLabel5.getText() != "Centrocampista") {
                    listModelC.add(0, p352CenLabel5.getText());
                    p352CenLabel5.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p352AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p352AttLabel1.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p352AttLabel1.getText() != "Attaccante") {
                    listModelA.add(0, p352AttLabel1.getText());
                    p352AttLabel1.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p352AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p352AttLabel2.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p352AttLabel2.getText() != "Attaccante") {
                    listModelA.add(0, p352AttLabel2.getText());
                    p352AttLabel2.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    private void ins433(){
        p433PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    p433PorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(p433PorLabel.getText() != "Portiere"){
                    listModelP.add(0, p433PorLabel.getText());
                    p433PorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        p433DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p433DifLabel1.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p433DifLabel1.getText() != "Difensore"){
                    listModelD.add(0, p433DifLabel1.getText());
                    p433DifLabel1.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p433DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p433DifLabel2.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p433DifLabel2.getText() != "Difensore"){
                    listModelD.add(0, p433DifLabel2.getText());
                    p433DifLabel2.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p433DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p433DifLabel3.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p433DifLabel3.getText() != "Difensore"){
                    listModelD.add(0, p433DifLabel3.getText());
                    p433DifLabel3.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p433DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p433DifLabel4.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p433DifLabel4.getText() != "Difensore"){
                    listModelD.add(0, p433DifLabel4.getText());
                    p433DifLabel4.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p433CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p433CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p433CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p433CenLabel1.getText());
                    p433CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p433CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p433CenLabel2.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p433CenLabel2.getText() != "Centrocampista") {
                    listModelC.add(0, p433CenLabel2.getText());
                    p433CenLabel2.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p433CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p433CenLabel3.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p433CenLabel3.getText() != "Centrocampista") {
                    listModelC.add(0, p433CenLabel3.getText());
                    p433CenLabel3.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p433AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p433AttLabel1.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p433AttLabel1.getText() != "Attaccante") {
                    listModelA.add(0, p433AttLabel1.getText());
                    p433AttLabel1.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p433AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p433AttLabel2.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p433AttLabel2.getText() != "Attaccante") {
                    listModelA.add(0, p433AttLabel2.getText());
                    p433AttLabel2.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p433AttLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p433AttLabel3.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p433AttLabel3.getText() != "Attaccante") {
                    listModelA.add(0, p433AttLabel3.getText());
                    p433AttLabel3.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    private void ins442(){
        p442PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    p442PorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(p442PorLabel.getText() != "Portiere"){
                    listModelP.add(0, p442PorLabel.getText());
                    p442PorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        p442DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p442DifLabel1.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p442DifLabel1.getText() != "Difensore"){
                    listModelD.add(0, p442DifLabel1.getText());
                    p442DifLabel1.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p442DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p442DifLabel2.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p442DifLabel2.getText() != "Difensore"){
                    listModelD.add(0, p442DifLabel2.getText());
                    p442DifLabel2.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p442DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p442DifLabel3.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p442DifLabel3.getText() != "Difensore"){
                    listModelD.add(0, p442DifLabel3.getText());
                    p442DifLabel3.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p442DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p442DifLabel4.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p442DifLabel1.getText() != "Difensore"){
                    listModelD.add(0, p442DifLabel4.getText());
                    p442DifLabel4.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p442CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p442CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p442CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p442CenLabel1.getText());
                    p442CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p442CenLabel2.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p442CenLabel2.getText() != "Centrocampista") {
                    listModelC.add(0, p442CenLabel2.getText());
                    p442CenLabel2.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p442CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p442CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p442CenLabel1.getText());
                    p442CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p442CenLabel4.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p442CenLabel4.getText() != "Centrocampista") {
                    listModelC.add(0, p442CenLabel4.getText());
                    p442CenLabel4.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p442AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p442AttLabel1.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p442AttLabel1.getText() != "Attaccante") {
                    listModelA.add(0, p442AttLabel1.getText());
                    p442AttLabel1.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p442AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p442AttLabel2.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p442AttLabel2.getText() != "Attaccante") {
                    listModelA.add(0, p442AttLabel2.getText());
                    p442AttLabel2.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    private void ins451(){
        p451PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    p451PorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(p451PorLabel.getText() != "Portiere"){
                    listModelP.add(0, p451PorLabel.getText());
                    p451PorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        p451DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p451DifLabel1.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p451DifLabel1.getText() != "Difensore"){
                    listModelD.add(0, p451DifLabel1.getText());
                    p451DifLabel1.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p451DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p451DifLabel2.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p451DifLabel2.getText() != "Difensore"){
                    listModelD.add(0, p451DifLabel2.getText());
                    p451DifLabel2.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p451DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p451DifLabel3.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p451DifLabel3.getText() != "Difensore"){
                    listModelD.add(0, p451DifLabel3.getText());
                    p451DifLabel3.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p451DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p451DifLabel4.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p451DifLabel4.getText() != "Difensore"){
                    listModelD.add(0, p451DifLabel4.getText());
                    p451DifLabel4.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p451CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p451CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p451CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p451CenLabel1.getText());
                    p451CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p451CenLabel2.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p451CenLabel2.getText() != "Centrocampista") {
                    listModelC.add(0, p442CenLabel2.getText());
                    p451CenLabel2.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p451CenLabel3.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p451CenLabel3.getText() != "Centrocampista") {
                    listModelC.add(0, p451CenLabel3.getText());
                    p451CenLabel3.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p451CenLabel4.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p451CenLabel4.getText() != "Centrocampista") {
                    listModelC.add(0, p451CenLabel4.getText());
                    p451CenLabel4.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451CenLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p451CenLabel5.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p451CenLabel5.getText() != "Centrocampista") {
                    listModelC.add(0, p451CenLabel5.getText());
                    p451CenLabel5.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p451AttLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p451AttLabel.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p451AttLabel.getText() != "Attaccante") {
                    listModelA.add(0, p451AttLabel.getText());
                    p451AttLabel.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    private void ins532(){
        p532PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    p532PorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(p532PorLabel.getText() != "Portiere"){
                    listModelP.add(0, p532PorLabel.getText());
                    p532PorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        p532DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p532DifLabel1.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p532DifLabel1.getText() != "Difensore"){
                    listModelD.add(0, p532DifLabel1.getText());
                    p532DifLabel1.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p532DifLabel2.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p532DifLabel2.getText() != "Difensore"){
                    listModelD.add(0, p532DifLabel2.getText());
                    p532DifLabel2.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p532DifLabel3.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p532DifLabel3.getText() != "Difensore"){
                    listModelD.add(0, p532DifLabel3.getText());
                    p532DifLabel3.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p532DifLabel4.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p532DifLabel4.getText() != "Difensore"){
                    listModelD.add(0, p532DifLabel4.getText());
                    p532DifLabel4.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p532DifLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    p532DifLabel5.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(p532DifLabel5.getText() != "Difensore"){
                    listModelD.add(0, p532DifLabel5.getText());
                    p532DifLabel5.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        p532CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p532CenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p532CenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, p532CenLabel1.getText());
                    p532CenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p532CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p532CenLabel2.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p532CenLabel2.getText() != "Centrocampista") {
                    listModelC.add(0, p532CenLabel2.getText());
                    p532CenLabel2.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p532CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    p532CenLabel3.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (p532CenLabel3.getText() != "Centrocampista") {
                    listModelC.add(0, p532CenLabel3.getText());
                    p532CenLabel3.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        p532AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p532AttLabel1.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p532AttLabel1.getText() != "Attaccante") {
                    listModelA.add(0, p532AttLabel1.getText());
                    p532AttLabel1.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        p532AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    p532AttLabel2.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (p532AttLabel2.getText() != "Attaccante") {
                    listModelA.add(0, p532AttLabel2.getText());
                    p532AttLabel2.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

    private void inspan(){
        panPorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false){
                    panPorLabel.setText((String)lPortieri.getSelectedValue());
                    listModelP.removeElement(lPortieri.getSelectedValue());
                }
                else if(panPorLabel.getText() != "Portiere"){
                    listModelP.add(0, panPorLabel.getText());
                    panPorLabel.setText("Portiere");
                }
                lPortieri.clearSelection();
            }
        });
        panDifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    panDifLabel1.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(panDifLabel1.getText() != "Difensore"){
                    listModelD.add(0, panDifLabel1.getText());
                    panDifLabel1.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        panDifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false){
                    panDifLabel2.setText(temp);
                    listModelD.removeElement(lDifensori.getSelectedValue());
                }
                else if(panDifLabel2.getText() != "Difensore"){
                    listModelD.add(0, panDifLabel2.getText());
                    panDifLabel2.setText("Difensore");
                }
                lDifensori.clearSelection();
            }
        });
        panCenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    panCenLabel1.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (panCenLabel1.getText() != "Centrocampista") {
                    listModelC.add(0, panCenLabel1.getText());
                    panCenLabel1.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        panCenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false) {
                    panCenLabel2.setText(temp);
                    listModelC.removeElement(lCentrocampisti.getSelectedValue());
                } else if (panCenLabel2.getText() != "Centrocampista") {
                    listModelC.add(0, panCenLabel2.getText());
                    panCenLabel2.setText("Centrocampista");
                }
                lCentrocampisti.clearSelection();
            }
        });
        panAttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    panAttLabel1.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (panAttLabel1.getText() != "Attaccante") {
                    listModelA.add(0, panAttLabel1.getText());
                    panAttLabel1.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
        panAttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false) {
                    panAttLabel2.setText(temp);
                    listModelA.removeElement(lAttaccanti.getSelectedValue());
                } else if (panAttLabel2.getText() != "Attaccante") {
                    listModelA.add(0, panAttLabel2.getText());
                    panAttLabel2.setText("Attaccante");
                }
                lAttaccanti.clearSelection();
            }
        });
    }

}
