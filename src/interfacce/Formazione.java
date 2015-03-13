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
public class Formazione extends JPanel implements ItemListener, MouseListener {

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

    String selP = new String();
    String selD = new String();
    String selC = new String();
    String selA = new String();

    public Formazione() {
        //viene generato il codice di setup dell'UI e richiamato il metodo $$$setupUI$$$() come prima istruzione
        //gestisciListe(squadra);
        setVisible(true);
        lPortieri.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listSelectionEvent.getValueIsAdjusting() == false) {
                    if(lPortieri.isSelectionEmpty() == false) selP = (String)lPortieri.getSelectedValue();
                }
            }
        });
        lDifensori.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listSelectionEvent.getValueIsAdjusting() == false){
                    if(lDifensori.isSelectionEmpty() == false) selD = (String)lDifensori.getSelectedValue();
                }
            }
        });
    }



    //creazione custom dei componenti
    private void createUIComponents() {
        scegliModulo = new JComboBox();
        scegliModulo.addItemListener(this);
        p343PorLabel = new JLabel();
        p343PorLabel.addMouseListener(this);
        p343DifLabel1 = new JLabel();
        p343DifLabel1.addMouseListener(this);
        p343DifLabel2 = new JLabel();
        p343DifLabel2.addMouseListener(this);
        p343DifLabel3 = new JLabel();
        p343DifLabel3.addMouseListener(this);
        p343CenLabel1 = new JLabel();
        p343CenLabel1.addMouseListener(this);
        p343CenLabel2 = new JLabel();
        p343CenLabel2.addMouseListener(this);
        p343CenLabel3 = new JLabel();
        p343CenLabel3.addMouseListener(this);
        p343CenLabel4 = new JLabel();
        p343CenLabel4.addMouseListener(this);
        p343AttLabel1 = new JLabel();
        p343AttLabel1.addMouseListener(this);
        p343AttLabel2 = new JLabel();
        p343AttLabel2.addMouseListener(this);
        p343AttLabel3 = new JLabel();
        p343AttLabel3.addMouseListener(this);
    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        CardLayout c1 = (CardLayout) (cards.getLayout());
        c1.show(cards, (String) itemEvent.getItem());
    }

    //override metodi Mouselistener
    @Override
    public void mouseClicked(MouseEvent e) {
        if(selP != null) p343PorLabel.setText(selP);
        if(selD != null) p343DifLabel1.setText(selD);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

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
