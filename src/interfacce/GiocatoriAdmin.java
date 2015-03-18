package interfacce;

import classi.*;
import db.Mysql;
import sun.rmi.runtime.Log;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alessandro on 18/03/15.
 */
public class GiocatoriAdmin extends JPanel {
    private JPanel panelGiocatori;
    private JButton quotazioniButton;
    private JTextField quotazioniPath;
    private JButton inviaQuotazioni;
    private JPanel panelCSV;
    private JPanel panelTabella;
    private JTable tabellaGiocatori;

    private ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();

    final Mysql db = new Mysql();

    String pathFile;

    Utils utils = new Utils();



    public GiocatoriAdmin(final ApplicazioneAdmin frame){
        final Logger log = Logger.getLogger(GiocatoriAdmin.class.getName());


        listaGiocatori = db.selectGiocatoriAdmin();

        //se la lista di giocatori non è vuota, cioè
        //è già stato caricato il csv una volta
        if (!listaGiocatori.isEmpty()) {
            //popolo la tabella
            setTabella();

            CardLayout c1 = (CardLayout) (panelGiocatori.getLayout());
            c1.show(panelGiocatori, "tabella");
        }

        quotazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fc = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
                fc.setDirectory("/");
                fc.setVisible(true);
                String filename = fc.getFile();
                String path = fc.getDirectory();
                pathFile = fc.getDirectory() + fc.getFile();
                if (pathFile == null) {
                    System.out.println("You cancelled the choice");
                } else {
                    quotazioniPath.setText(pathFile);
                }
            }
        });
        inviaQuotazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.csvQuotazioni(pathFile, ",");

            }
        });


    }

    private void setTabella(){
        Object[] nomeColonne = {"ID", "Cognome", "Ruolo", "Squadra Reale", "Costo"};
        //listaToArray ritorna un array di obkect che serve per il model
        Object[][] righeGiocatori = utils.listaToArray(listaGiocatori);
        //creo il modello con le colonne e i giocatori
        DefaultTableModel giocatoriModel = new DefaultTableModel(righeGiocatori, nomeColonne) {
            //rende non modificabili le colonne dell'ID,cognome e ruolo
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 ? false : true;
            }
        };

        tabellaGiocatori.setModel(giocatoriModel);

    }
}
