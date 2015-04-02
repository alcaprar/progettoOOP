package AstaLive;

import javax.swing.*;

/**
 * Created by Christian on 30/03/2015.
 */
public class ServerConsole extends JFrame{
    private JPanel mainPanel;
    private JTextArea serverConsole;

    public ServerConsole(){
        serverConsole.setEditable(false);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void eventoServer(String s){
        serverConsole.append(s);
        serverConsole.setCaretPosition(serverConsole.getText().length() - 1);
    }

}
