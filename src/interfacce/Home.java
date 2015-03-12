package interfacce;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by alessandro on 11/03/15.
 */
public class Home {
    private JPanel panel1;
    private JButton inviaLaFormazioneButton;
    private JPanel ultimaGiornata;
    private JList list1;

    private void createUIComponents() {
        //ultimaGiornata = new JPanel();
        ultimaGiornata.setBorder(new LineBorder(Color.GRAY, 10, true));
    }
}
