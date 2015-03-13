package interfacce;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

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

    Image bkg_panel = Toolkit.getDefaultToolkit().createImage("resources/Soccer_Field_Transparant.png");

    public Formazione() {
        //viene generato il codice di setup dell'UI e richiamato il metodo $$$setupUI$$$() come prima istruzione
        gestisciListe();
        setVisible(true);
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
    public void gestisciListe (){

        //lista dei portieri

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
    }

}
