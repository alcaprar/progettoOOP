package interfacce;

import classi.Squadra;
import db.Mysql;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 24/03/15.
 */
public class GestioneLega extends JPanel{
    private JTextArea testoAvviso;
    private JButton inviaButton;
    private JTextField titoloAvviso;
    private JPanel mainPanel;
    private JLabel abilitatolbl;
    private JLabel nonAbilitatolbl;
    private JButton calcolaButton;

    private Squadra squadra;

    private final Mysql db = new Mysql();

    private int giornataVotiInseriti;

    public void setSquadra(Squadra sqr){
        this.squadra = sqr;
    }



    public GestioneLega(){

        giornataVotiInseriti = db.selectGiornateVotiInseriti();


        inviaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(db.inserisciAvviso(squadra.getCampionato(), titoloAvviso.getText(), testoAvviso.getText())){
                    JOptionPane.showMessageDialog(null, "Avviso inserito con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    titoloAvviso.setText("");
                    testoAvviso.setText("");
                }

            }
        });
    }
}
