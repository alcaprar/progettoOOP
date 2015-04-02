package AstaLive3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by alessandro on 02/04/15.
 */
public class Client extends JFrame {
    BufferedReader in = null;
    PrintStream out = null;
    Socket socket = null;
    String message;

    private JPanel mainPanel;
    private JTextField indirizzotxt;
    private JButton connettitiButton;
    private JTextArea consoleArea;
    private JSpinner spinnerPorta;
    private JTextField messaggiotxt;
    private JButton inviaButton;

    public static void main(String args[]){
        new Client();
    }

    public Client(){
        super("Client");

        connettitiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indirizzo = indirizzotxt.getText();
                int porta = (Integer) spinnerPorta.getValue();
                try {
                    socket = new Socket(indirizzo, porta);
                    //apre i canali di I/O
                    in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    out = new PrintStream(socket.getOutputStream(), true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        inviaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println(messaggiotxt.getText());
                out.flush();
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setSize(600,600);
        setVisible(true);
    }

    public void appendConsole(String str){
        consoleArea.append(str+"\n");
    }
}
