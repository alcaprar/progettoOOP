package interfacce.Admin;

import db.Mysql;
import interfacce.Admin.ApplicazioneAdmin;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by alessandro on 24/03/15.
 */
public class VotiAdmin extends JPanel {
    private JSpinner spinnerGiornata;
    private JButton brosweButton;
    private JTextField pathtxt;
    private JButton caricaButton;
    private JPanel mainPanel;

    final private Mysql db = new Mysql();

    private Utils utils = new Utils();

    private String pathFile;

    private int ultimaGiornataInserita;

    public VotiAdmin(final ApplicazioneAdmin frame){

        refresh();

        brosweButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fc = new FileDialog(frame, "Scegli un file", FileDialog.LOAD);
                String directory = System.getProperty("user.home");
                fc.setDirectory(directory);
                fc.setVisible(true);
                pathFile = fc.getDirectory() + fc.getFile();
                if (pathFile == null) {
                } else {
                    pathtxt.setText(pathFile);
                }
            }
        });

        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //catturo le eccezzioni IO
                //se il file non è presente mando un warning
                try {
                    if (utils.xlsvoti(pathFile, (Integer) spinnerGiornata.getValue())) {
                        JOptionPane.showMessageDialog(null, "Voti della giornata " + String.valueOf(ultimaGiornataInserita + 1) + " inseriti correttamente", "Success", JOptionPane.INFORMATION_MESSAGE);
                        ultimaGiornataInserita+=1;
                        SpinnerNumberModel giornataModel = new SpinnerNumberModel(ultimaGiornataInserita+1, ultimaGiornataInserita+1, 38, 1);

                        spinnerGiornata.setModel(giornataModel);
                    } else {
                        JOptionPane.showMessageDialog(null, "Voti della giornata " + String.valueOf(ultimaGiornataInserita + 1) + " non sono stati inseriti correttamente", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (IOException ioe){
                    ioe.printStackTrace();
                    JOptionPane.showMessageDialog(getPanel(),"File non trovato","Errore",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public void refresh(){
        ultimaGiornataInserita = db.selectGiornateVotiInseriti();

        //costruttore spinnermodel-->(valore da visualizzare, min, max, incremento)
        SpinnerNumberModel giornataModel = new SpinnerNumberModel(ultimaGiornataInserita+1, ultimaGiornataInserita+1, 38, 1);

        spinnerGiornata.setModel(giornataModel);
    }

    private VotiAdmin getPanel(){
        return this;
    }
}
