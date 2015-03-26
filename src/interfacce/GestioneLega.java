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
    private JButton calcolaButton;
    private JLabel nrGiolbl;
    private JLabel nonAbilitatolbl;
    private JPanel calcolaPanel;

    private Squadra squadra;

    private final Mysql db = new Mysql();

    private int giornataVotiInseriti;
    private int ultimaGiornataReale;

    public void setSquadra(Squadra sqr){
        this.squadra = sqr;
    }

    public void refresh(){
        ultimaGiornataReale = squadra.getCampionato().prossimaGiornata().getGioReale().getNumeroGiornata();
        if(giornataVotiInseriti!=ultimaGiornataReale){
            calcolaPanel.setVisible(false);
        } else {
            nonAbilitatolbl.setVisible(false);
            nrGiolbl.setText(String.valueOf(squadra.getCampionato().prossimaGiornata().getNumGiornata()));
        }
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
