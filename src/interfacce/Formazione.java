package interfacce;

import javax.swing.*;
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
    private JPanel p343;
    private JPanel p352;
    private JList lPortieri;
    private JList lCentrocampisti;
    private JList lAttaccanti;
    private JList lDifensori;
    private JPanel pPortieri;
    private JPanel pDifensori;
    private JPanel pCentrocampisti;
    private JPanel pAttaccanti;
    private JPanel pCombobox;
    private JLabel l343;

    private Image bkg_panel = Toolkit.getDefaultToolkit().createImage("./resources/Soccer_Field_Transparant.png");

    public Formazione() {
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

}
