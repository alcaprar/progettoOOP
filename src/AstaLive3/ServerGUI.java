package AstaLive3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 02/04/15.
 */
public class ServerGUI extends JFrame{
    private JPanel mainPanel;
    private JSpinner spinnerPorta;
    private JButton startServerButton;
    private JTextArea consoleArea;
    private Server server;

    public static void main(String args[]){
        new ServerGUI();
    }

    public ServerGUI(){
        super("Server");

        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int porta = (Integer) spinnerPorta.getValue();
                server = new Server(porta, getFrame());
                startServerButton.setEnabled(false);
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private ServerGUI getFrame(){
        return this;
    }

    public void appendConsole(String str){
        consoleArea.append(str+"\n");
    }
}
