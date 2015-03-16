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

    private DefaultListModel listModel;

    Image bkg_panel = Toolkit.getDefaultToolkit().createImage("resources/Soccer_Field_Transparant.png");

    public Formazione() {
        //viene generato il codice di setup dell'UI e richiamato il metodo $$$setupUI$$$() come prima istruzione
        //gestisciListe(squadra);
        setVisible(true);

        //Per ogni etichetta del pannello viene generato un Mouselistener per controllare il riempimento della formazione mediante
        //il click del mouse
        ins343();
        ins352();

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

    //gestori dell'inserimento dei giocatori

    private void ins343(){
        p343PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(lPortieri.isSelectionEmpty() == false) p343PorLabel.setText((String)lPortieri.getSelectedValue());
                lPortieri.clearSelection();
            }
        });
        p343DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != p343DifLabel2.getText() && temp != p343DifLabel3.getText()) p343DifLabel1.setText((String)lDifensori.getSelectedValue());
                if(lDifensori.isSelectionEmpty() == false ){
                    if(temp == p343DifLabel2.getText()) {
                        p343DifLabel2.setText(p343DifLabel1.getText());
                        p343DifLabel1.setText(temp);
                    }
                    else if(temp == p343DifLabel3.getText()){
                        p343DifLabel3.setText(p343DifLabel1.getText());
                        p343DifLabel1.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) p343DifLabel1.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        p343DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != p343DifLabel1.getText() && temp != p343DifLabel3.getText()) p343DifLabel2.setText((String)lDifensori.getSelectedValue());
                if(lDifensori.isSelectionEmpty() == false ){
                    if(temp == p343DifLabel1.getText()) {
                        p343DifLabel1.setText(p343DifLabel2.getText());
                        p343DifLabel2.setText(temp);
                    }
                    else if(temp == p343DifLabel3.getText()){
                        p352DifLabel3.setText(p352DifLabel2.getText());
                        p352DifLabel2.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) p343DifLabel2.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        p343DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != p343DifLabel1.getText() && temp != p343DifLabel2.getText()) p343DifLabel3.setText((String)lDifensori.getSelectedValue());
                if(lDifensori.isSelectionEmpty() == false ){
                    if(temp == p343DifLabel1.getText()) {
                        p343DifLabel1.setText(p343DifLabel3.getText());
                        p343DifLabel3.setText(temp);
                    }
                    else if(temp == p343DifLabel2.getText()){
                        p343DifLabel2.setText(p343DifLabel3.getText());
                        p343DifLabel3.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) p343DifLabel3.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        p343CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel2.getText() && temp != p343CenLabel3.getText() && temp != p343CenLabel4.getText()) p343CenLabel1.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p343CenLabel2.getText()) {
                        p343CenLabel2.setText(p343CenLabel1.getText());
                        p343CenLabel1.setText(temp);
                    }
                    else if(temp == p343CenLabel3.getText()){
                        p343CenLabel3.setText(p343CenLabel1.getText());
                        p343CenLabel1.setText(temp);
                    }
                    else if(temp == p343CenLabel4.getText()){
                        p343CenLabel4.setText(p343CenLabel1.getText());
                        p343CenLabel1.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p343CenLabel1.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel1.getText() && temp != p343CenLabel3.getText() && temp != p343CenLabel4.getText()) p343CenLabel2.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p343CenLabel1.getText()) {
                        p343CenLabel1.setText(p343CenLabel2.getText());
                        p343CenLabel2.setText(temp);
                    }
                    else if(temp == p343CenLabel3.getText()){
                        p343CenLabel3.setText(p343CenLabel2.getText());
                        p343CenLabel2.setText(temp);
                    }
                    else if(temp == p343CenLabel4.getText()){
                        p343CenLabel4.setText(p343CenLabel2.getText());
                        p343CenLabel2.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p343CenLabel2.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel1.getText() && temp != p343CenLabel2.getText() && temp != p343CenLabel4.getText()) p343CenLabel3.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p343CenLabel1.getText()) {
                        p343CenLabel1.setText(p343CenLabel3.getText());
                        p343CenLabel3.setText(temp);
                    }
                    else if(temp == p343CenLabel2.getText()){
                        p343CenLabel2.setText(p343CenLabel3.getText());
                        p343CenLabel3.setText(temp);
                    }
                    else if(temp == p343CenLabel4.getText()){
                        p343CenLabel4.setText(p343CenLabel3.getText());
                        p343CenLabel3.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p343CenLabel3.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p343CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel1.getText() && temp != p343CenLabel2.getText() && temp != p343CenLabel3.getText()) p343CenLabel4.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p343CenLabel1.getText()) {
                        p343CenLabel1.setText(p343CenLabel4.getText());
                        p343CenLabel4.setText(temp);
                    }
                    else if(temp == p343CenLabel2.getText()){
                        p343CenLabel2.setText(p343CenLabel4.getText());
                        p343CenLabel4.setText(temp);
                    }
                    else if(temp == p343CenLabel3.getText()){
                        p343CenLabel3.setText(p343CenLabel4.getText());
                        p343CenLabel4.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p343CenLabel4.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p343AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != p343AttLabel2.getText() && temp != p343AttLabel3.getText()) p343AttLabel1.setText((String)lAttaccanti.getSelectedValue());
                if(lAttaccanti.isSelectionEmpty() == false) {
                    if (temp == p343AttLabel2.getText()) {
                        p343AttLabel2.setText(p343AttLabel1.getText());
                        p343AttLabel1.setText(temp);
                    }
                    else if(temp == p343AttLabel3.getText()){
                        p343AttLabel3.setText(p343AttLabel1.getText());
                        p343AttLabel1.setText(temp);
                    }
                }
                if (lAttaccanti.isSelectionEmpty() == true) p343AttLabel1.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
        p343AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != p343AttLabel1.getText() && temp != p343AttLabel3.getText()) p343AttLabel2.setText((String)lAttaccanti.getSelectedValue());
                if(lAttaccanti.isSelectionEmpty() == false) {
                    if (temp == p343AttLabel1.getText()) {
                        p343AttLabel1.setText(p343AttLabel2.getText());
                        p343AttLabel2.setText(temp);
                    }
                    else if(temp == p343AttLabel3.getText()){
                        p343AttLabel3.setText(p343AttLabel2.getText());
                        p343AttLabel2.setText(temp);
                    }
                }
                if (lAttaccanti.isSelectionEmpty() == true) p343AttLabel2.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
        p343AttLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != p343AttLabel1.getText() && temp != p343AttLabel2.getText()) p343AttLabel3.setText((String)lAttaccanti.getSelectedValue());
                if(lAttaccanti.isSelectionEmpty() == false) {
                    if (temp == p343AttLabel1.getText()) {
                        p343AttLabel1.setText(p343AttLabel3.getText());
                        p343AttLabel3.setText(temp);
                    }
                    else if(temp == p343AttLabel2.getText()){
                        p343AttLabel2.setText(p343AttLabel3.getText());
                        p343AttLabel3.setText(temp);
                    }
                }
                if (lAttaccanti.isSelectionEmpty() == true) p343AttLabel3.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
    }

    private void ins352(){
        p352PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(lPortieri.isSelectionEmpty() == false) p352PorLabel.setText((String)lPortieri.getSelectedValue());
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
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p352CenLabel2.getText() && temp != p352CenLabel3.getText() && temp != p352CenLabel4.getText() && temp != p352CenLabel5.getText()) p352CenLabel1.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p352CenLabel2.getText()) {
                        p352CenLabel2.setText(p352CenLabel1.getText());
                        p352CenLabel1.setText(temp);
                    }
                    else if(temp == p352CenLabel3.getText()){
                        p352CenLabel3.setText(p352CenLabel1.getText());
                        p352CenLabel1.setText(temp);
                    }
                    else if(temp == p352CenLabel4.getText()){
                        p352CenLabel4.setText(p352CenLabel1.getText());
                        p352CenLabel1.setText(temp);
                    }
                    else if(temp == p352CenLabel5.getText()){
                        p352CenLabel5.setText(p352CenLabel1.getText());
                        p352CenLabel1.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p352CenLabel1.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p352CenLabel1.getText() && temp != p352CenLabel3.getText() && temp != p352CenLabel4.getText() && temp != p352CenLabel5.getText()) p352CenLabel2.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p352CenLabel1.getText()) {
                        p352CenLabel1.setText(p352CenLabel2.getText());
                        p352CenLabel2.setText(temp);
                    }
                    else if(temp == p352CenLabel3.getText()){
                        p352CenLabel3.setText(p352CenLabel2.getText());
                        p352CenLabel2.setText(temp);
                    }
                    else if(temp == p352CenLabel4.getText()){
                        p352CenLabel4.setText(p352CenLabel2.getText());
                        p352CenLabel2.setText(temp);
                    }
                    else if(temp == p352CenLabel5.getText()){
                        p352CenLabel5.setText(p352CenLabel2.getText());
                        p352CenLabel2.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p352CenLabel2.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p352CenLabel1.getText() && temp != p352CenLabel2.getText() && temp != p352CenLabel4.getText() && temp != p352CenLabel5.getText()) p352CenLabel3.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p352CenLabel1.getText()) {
                        p352CenLabel1.setText(p352CenLabel3.getText());
                        p352CenLabel3.setText(temp);
                    }
                    else if(temp == p352CenLabel2.getText()){
                        p352CenLabel2.setText(p352CenLabel3.getText());
                        p352CenLabel3.setText(temp);
                    }
                    else if(temp == p352CenLabel4.getText()){
                        p352CenLabel4.setText(p352CenLabel3.getText());
                        p352CenLabel3.setText(temp);
                    }
                    else if(temp == p352CenLabel5.getText()){
                        p352CenLabel5.setText(p352CenLabel3.getText());
                        p352CenLabel3.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p352CenLabel3.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p352CenLabel1.getText() && temp != p352CenLabel2.getText() && temp != p352CenLabel3.getText() && temp != p352CenLabel5.getText()) p352CenLabel4.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p352CenLabel1.getText()) {
                        p352CenLabel1.setText(p352CenLabel4.getText());
                        p352CenLabel4.setText(temp);
                    }
                    else if(temp == p352CenLabel2.getText()){
                        p352CenLabel2.setText(p352CenLabel4.getText());
                        p352CenLabel4.setText(temp);
                    }
                    else if(temp == p352CenLabel3.getText()){
                        p352CenLabel3.setText(p352CenLabel4.getText());
                        p352CenLabel4.setText(temp);
                    }
                    else if(temp == p352CenLabel5.getText()){
                        p352CenLabel5.setText(p352CenLabel4.getText());
                        p352CenLabel4.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p352CenLabel4.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p352CenLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != p352CenLabel1.getText() && temp != p352CenLabel2.getText() && temp != p352CenLabel3.getText() && temp != p352CenLabel4.getText()) p352CenLabel5.setText((String)lCentrocampisti.getSelectedValue());
                if(lCentrocampisti.isSelectionEmpty() == false ){
                    if(temp == p352CenLabel1.getText()) {
                        p352CenLabel2.setText(p352CenLabel5.getText());
                        p352CenLabel5.setText(temp);
                    }
                    else if(temp == p352CenLabel2.getText()){
                        p352CenLabel2.setText(p352CenLabel5.getText());
                        p352CenLabel5.setText(temp);
                    }
                    else if(temp == p352CenLabel3.getText()){
                        p352CenLabel3.setText(p352CenLabel5.getText());
                        p352CenLabel5.setText(temp);
                    }
                    else if(temp == p352CenLabel4.getText()){
                        p352CenLabel4.setText(p352CenLabel5.getText());
                        p352CenLabel5.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) p352CenLabel5.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        p352AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != p352AttLabel2.getText())
                    p352AttLabel1.setText((String) lAttaccanti.getSelectedValue());
                if (lAttaccanti.isSelectionEmpty() == false) {
                    if (temp == p352AttLabel2.getText()) {
                        p352AttLabel2.setText(p352AttLabel1.getText());
                        p352AttLabel1.setText(temp);
                    }
                }
                if (lAttaccanti.isSelectionEmpty() == true) p352AttLabel1.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
        p352AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != p352AttLabel1.getText()) p352AttLabel2.setText((String)lAttaccanti.getSelectedValue());
                if(lAttaccanti.isSelectionEmpty() == false ){
                    if(temp == p352AttLabel1.getText()) {
                        p352AttLabel1.setText(p352AttLabel2.getText());
                        p352AttLabel2.setText(temp);
                    }
                }
                if(lAttaccanti.isSelectionEmpty() == true) p352AttLabel2.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
    }

    private void inspan(){
        panPorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(lPortieri.isSelectionEmpty() == false) panPorLabel.setText((String)lPortieri.getSelectedValue());
                lPortieri.clearSelection();
            }
        });
        panDifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (lDifensori.isSelectionEmpty() == false && temp != panDifLabel2.getText()) panDifLabel1.setText(temp);
                if (lDifensori.isSelectionEmpty() == false) {
                    if (temp == panDifLabel2.getText()) {
                        panDifLabel2.setText(panDifLabel1.getText());
                        panDifLabel1.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) panDifLabel1.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        panDifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if(lDifensori.isSelectionEmpty() == false && temp != panDifLabel1.getText()) panDifLabel2.setText(temp);
                if(lDifensori.isSelectionEmpty() == false ) {
                    if (temp == panDifLabel1.getText()) {
                        panDifLabel1.setText(panDifLabel2.getText());
                        panDifLabel2.setText(temp);
                    }
                }
                if(lDifensori.isSelectionEmpty() == true) panDifLabel2.setText("Difensore");
                lDifensori.clearSelection();
            }
        });
        panCenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != panCenLabel2.getText()) panCenLabel1.setText(temp);
                if(lCentrocampisti.isSelectionEmpty() == false ) {
                    if (temp == panCenLabel2.getText()) {
                        panCenLabel2.setText(panCenLabel1.getText());
                        panCenLabel1.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) panCenLabel2.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        panCenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if(lCentrocampisti.isSelectionEmpty() == false && temp != panCenLabel1.getText()) panCenLabel2.setText(temp);
                if(lCentrocampisti.isSelectionEmpty() == false ) {
                    if (temp == panCenLabel1.getText()) {
                        panCenLabel1.setText(panCenLabel2.getText());
                        panCenLabel2.setText(temp);
                    }
                }
                if(lCentrocampisti.isSelectionEmpty() == true) panCenLabel1.setText("Centrocampista");
                lCentrocampisti.clearSelection();
            }
        });
        panAttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != panAttLabel2.getText()) panAttLabel1.setText(temp);
                if(lAttaccanti.isSelectionEmpty() == false ) {
                    if (temp == panAttLabel2.getText()) {
                        panAttLabel2.setText(panAttLabel1.getText());
                        panAttLabel1.setText(temp);
                    }
                }
                if(lAttaccanti.isSelectionEmpty() == true) panAttLabel1.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
        panAttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if(lAttaccanti.isSelectionEmpty() == false && temp != panAttLabel1.getText()) panAttLabel2.setText(temp);
                if(lAttaccanti.isSelectionEmpty() == false ) {
                    if (temp == panAttLabel1.getText()) {
                        panAttLabel1.setText(panAttLabel2.getText());
                        panAttLabel2.setText(temp);
                    }
                }
                if(lAttaccanti.isSelectionEmpty() == true) panAttLabel2.setText("Attaccante");
                lAttaccanti.clearSelection();
            }
        });
    }

}
