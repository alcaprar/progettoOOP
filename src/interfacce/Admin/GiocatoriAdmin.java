package interfacce.Admin;

import db.Mysql;
import entità.Giocatore;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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

    final private Mysql db = new Mysql();

    private String pathFile;


    public GiocatoriAdmin(final ApplicazioneAdmin frame) {

        listaGiocatori = db.selectGiocatoriAdmin();

        //se la lista di giocatori non è vuota, cioè
        //è già stato caricato il csv una volta
        if (!listaGiocatori.isEmpty()) {
            //popolo la tabella
            setTabella();

            CardLayout c1 = (CardLayout) (panelGiocatori.getLayout());
            c1.show(panelGiocatori, "tabella");
        } else{
            CardLayout c1 = (CardLayout) (panelGiocatori.getLayout());
            c1.show(panelGiocatori, "csv");
        }

        quotazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fc = new FileDialog(frame, "Scegli un file", FileDialog.LOAD);
                String directory = System.getProperty("user.home");
                fc.setDirectory(directory);
                fc.setVisible(true);
                pathFile = fc.getDirectory() + fc.getFile();
                if (pathFile == null) {
                } else {
                    quotazioniPath.setText(pathFile);
                }
            }
        });
        inviaQuotazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(Utils.xlsQuotazioni(pathFile)){
                        JOptionPane.showMessageDialog(getPanel(),"Inserimento giocatori effettuato con successo.","Ok",JOptionPane.INFORMATION_MESSAGE);

                        listaGiocatori = db.selectGiocatoriAdmin();

                        setTabella();

                        CardLayout c1 = (CardLayout) (panelGiocatori.getLayout());
                        c1.show(panelGiocatori, "tabella");
                    } else{
                        JOptionPane.showMessageDialog(getPanel(),"Inserimento giocatori non riuscito.","Fallito",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ioe){
                    ioe.printStackTrace();
                    JOptionPane.showMessageDialog(getPanel(),"File non trovato.","Errore",JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }

    private void setTabella() {
        Object[] nomeColonne = {"ID", "Cognome", "Ruolo", "Squadra Reale", "Costo"};
        //listaToArray ritorna un array di obkect che serve per il model
        Object[][] righeGiocatori = Utils.listaGiocatoriToArray(listaGiocatori);
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

    private GiocatoriAdmin getPanel(){
        return this;
    }

}
