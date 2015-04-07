package interfacce.admin;

import db.Mysql;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 18/03/15.
 */
public class HomeAdmin extends JPanel {
    private JPanel homePanel;
    private JButton terminaButton;


    public HomeAdmin() {

        terminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mysql.terminaCampionatoAdmin();
            }
        });

    }


}
