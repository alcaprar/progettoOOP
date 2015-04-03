package AstaLive3;

import classi.Giocatore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alessandro on 03/04/15.
 */
public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField indirizzotxt;
    private JSpinner spinnerPorta;
    private JButton connettiButton;
    private JTextArea consoleArea;
    private JTextField usernametxt;
    private JLabel campionatoL;
    private JLabel attualelbl;
    private JLabel cognomelbl;
    private JLabel ruololbl;
    private JLabel squadraRealelbl;
    private JLabel inizialelbl;
    private JSpinner spinnerOfferta;
    private JButton buttonOfferta;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private boolean offerto;

    public ClientGUI(){
        super("Client");
        setSpinnerPorta();

        connettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indirizzo = indirizzotxt.getText();
                int porta = (Integer) spinnerPorta.getValue();
                String username = usernametxt.getText();
                appendConsole("Tentativo di connessione a: " + indirizzo + ":" + porta + " Username: " + username);
                new Client(indirizzo, porta, username, getFrame());
            }
        });

        buttonOfferta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offerto=true;
                setOffertaNotEnabled();
            }
        });

        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
    }

    public static void main(String args[]){
        new ClientGUI();
    }

    /**
     * Scrive nella console.
     * @param str stringa da stampare
     */
    public void appendConsole(String str){
        consoleArea.append(sdf.format(new Date())+">> "+str+"\n");
    }

    public void setGiocatoreAttuale(Giocatore giocatore, int prezzo){
        cognomelbl.setText(giocatore.getCognome());
        ruololbl.setText(String.valueOf(giocatore.getRuolo()));
        squadraRealelbl.setText(giocatore.getSquadraReale());
        inizialelbl.setText(String.valueOf(giocatore.getPrezzoBase()));

        attualelbl.setText(String.valueOf(prezzo+1));
        int max = 100;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(prezzo+1,prezzo+1,max,1);
        spinnerOfferta.setModel(spinnerModel);
    }

    public void setOffertaEnabled(){
        spinnerOfferta.setEnabled(true);
        buttonOfferta.setEnabled(true);
        offerto = false;
    }

    public void setOffertaNotEnabled(){
        spinnerOfferta.setEnabled(false);
        buttonOfferta.setEnabled(false);
    }

    private void setSpinnerPorta(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1500,1000,2000,1);
        spinnerPorta.setModel(spinnerModel);
    }

    private ClientGUI getFrame(){
        return this;
    }

    public boolean getOfferto(){
        return offerto;
    }

    public int getValoreOfferta(){
        return (Integer)spinnerOfferta.getValue();
    }
}
