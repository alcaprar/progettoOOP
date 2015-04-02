package AstaLive;

import com.sun.org.apache.xalan.internal.xslt.*;
import com.sun.org.apache.xalan.internal.xslt.Process;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Christian on 30/03/2015.
 */
public class AstaLiveStarter extends JFrame{
    private JPanel mainPanel;
    private JButton buttonServer;
    private JButton buttonClient;
    private JSpinner spinnerPorta;

    Server server;

    public AstaLiveStarter() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        buttonServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int port;
                port = (Integer) spinnerPorta.getValue();
                if (port == 0) {
                    port = 1500;
                }
            }
        });

        buttonClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AstaLiveClient asta = new AstaLiveClient();
            }
        });
    }

    public static void main(String[] args){
        AstaLiveStarter a = new AstaLiveStarter();
    }
}
