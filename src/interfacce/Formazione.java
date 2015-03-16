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

    Image bkg_panel = Toolkit.getDefaultToolkit().createImage("resources/Soccer_Field_Transparant.png");

    public Formazione() {
        //viene generato il codice di setup dell'UI e richiamato il metodo $$$setupUI$$$() come prima istruzione
        //gestisciListe(squadra);

        setVisible(true);

        //Per ogni etichetta del pannello viene generato un Mouselistener per controllare il riempimento della formazione mediante
        //il click del mouse

        p343PorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (lPortieri.isSelectionEmpty() == false) p343PorLabel.setText((String) lPortieri.getSelectedValue());
            }
        });
        p343DifLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (lDifensori.isSelectionEmpty() == false && temp != p343DifLabel2.getText() && temp != p343DifLabel3.getText())
                    p343DifLabel1.setText((String) lDifensori.getSelectedValue());
            }
        });
        p343DifLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (lDifensori.isSelectionEmpty() == false && temp != p343DifLabel1.getText() && temp != p343DifLabel3.getText())
                    p343DifLabel2.setText((String) lDifensori.getSelectedValue());
            }
        });
        p343DifLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lDifensori.getSelectedValue();
                if (lDifensori.isSelectionEmpty() == false && temp != p343DifLabel1.getText() && temp != p343DifLabel2.getText())
                    p343DifLabel3.setText((String) lDifensori.getSelectedValue());
            }
        });
        p343CenLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel2.getText() && temp != p343CenLabel3.getText() && temp != p343CenLabel4.getText())
                    p343CenLabel1.setText((String) lCentrocampisti.getSelectedValue());
            }
        });
        p343CenLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel1.getText() && temp != p343CenLabel3.getText() && temp != p343CenLabel4.getText())
                    p343CenLabel2.setText((String) lCentrocampisti.getSelectedValue());
            }
        });
        p343CenLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel1.getText() && temp != p343CenLabel2.getText() && temp != p343CenLabel4.getText())
                    p343CenLabel3.setText((String) lCentrocampisti.getSelectedValue());
            }
        });
        p343CenLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lCentrocampisti.getSelectedValue();
                if (lCentrocampisti.isSelectionEmpty() == false && temp != p343CenLabel1.getText() && temp != p343CenLabel2.getText() && temp != p343CenLabel3.getText())
                    p343CenLabel4.setText((String) lCentrocampisti.getSelectedValue());
            }
        });
        p343AttLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false && temp != p343AttLabel2.getText() && temp != p343AttLabel3.getText())
                    p343AttLabel1.setText((String) lAttaccanti.getSelectedValue());
            }
        });
        p343AttLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false && temp != p343AttLabel1.getText() && temp != p343AttLabel3.getText())
                    p343AttLabel2.setText((String) lAttaccanti.getSelectedValue());
            }
        });
        p343AttLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String temp = new String();
                temp = (String) lAttaccanti.getSelectedValue();
                if (lAttaccanti.isSelectionEmpty() == false && temp != p343AttLabel1.getText() && temp != p343AttLabel2.getText())
                    p343AttLabel3.setText((String) lAttaccanti.getSelectedValue());
            }
        });
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


    //metodo per gestire le liste
    //public void gestisciListe (Squadra sqr){

    //lista dei portieri
    //lPortieri.setListData(sqr.getGiocatori());

        /*lPortieri.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {

                    if (lPortieri.getSelectedIndex() == -1) {
                        //No selection, disable fire button.
                        //fireButton.setEnabled(false);

                    } else {
                        //Selection, enable the fire button.
                        //fireButton.setEnabled(true);
                    }
                }
            }
        });*/

    //lista dei difensori

    //lista dei centrocampisti

    //lista degli attaccanti
    //}

}
