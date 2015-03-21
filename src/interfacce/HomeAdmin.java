package interfacce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import db.*;

/**
 * Created by alessandro on 18/03/15.
 */
public class HomeAdmin extends JPanel {
    private JPanel homePanel;
    private JButton pulisciButton;
    private JLabel righeLabel;

    final Mysql db = new Mysql();

    int delete;

    public HomeAdmin() {

        pulisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete = db.deleteCampionati();
                righeLabel.setText(String.valueOf(delete));
            }
        });

    }


}
