package interfacce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Christian on 11/03/2015.
 */
public class Formazione extends JPanel implements ItemListener{


    //stringhe dei moduli ammissibili
    private String s343 = new String("Modulo 3-4-3");
    private String s352 = new String("Modulo 3-5-2");
    private String s433 = new String("Modulo 4-3-3");
    private String s442 = new String("Modulo 4-4-2");
    private String s451 = new String("Modulo 4-5-1");
    private String s532 = new String("Modulo 5-3-2");
    private String s541 = new String("Modulo 5-4-1");
    //vettore dei moduli
    //private String[] comboBoxItems = new String[]{s343, s352, s433, s442, s451, s532, s541};

    private JPanel panel1;
    private JPanel cards;
    private JComboBox scegliModulo;
    private JPanel p343;
    private JPanel p352;
    private JList list1;

    public Formazione(){
        setVisible(true);
    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        CardLayout c1 = (CardLayout)(cards.getLayout());
        c1.show(cards, (String)itemEvent.getItem());
    }
}
