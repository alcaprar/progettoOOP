package interfacce.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import db.*;

/**
 * Created by alessandro on 18/03/15.
 */
public class HomeAdmin extends JPanel {
    private JPanel homePanel;
    private JButton terminaButton;

    final private  Mysql db = new Mysql();

    public HomeAdmin() {

        terminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.terminaCampionatoAdmin();
            }
        });

    }


}
