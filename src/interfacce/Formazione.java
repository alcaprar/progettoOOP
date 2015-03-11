package interfacce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Christian on 11/03/2015.
 */
public class Formazione extends JFrame implements ItemListener{


    //stringhe dei moduli ammissibili
    String s343 = new String("Modulo 3-4-3");
    String s352 = new String("Modulo 3-5-2");
    String s433 = new String("Modulo 4-3-3");
    String s442 = new String("Modulo 4-4-2");
    String s451 = new String("Modulo 4-5-1");
    String s532 = new String("Modulo 5-3-2");
    String s541 = new String("Modulo 5-4-1");
    //vettore dei moduli
    String[] comboBoxItems = new String[]{s343, s352, s433, s442, s451, s532, s541};

    private JPanel panel1;
    private JList list1;
    private JPanel cards;
    private JComboBox scegliModulo = new JComboBox(comboBoxItems);
    private JPanel p343;
    private JPanel p352;

    public Formazione(){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        CardLayout c1 = (CardLayout)(cards.getLayout());
        c1.show(cards, (String)itemEvent.getItem());
    }
}
