package interfacce;

import classi.*;
import db.Mysql;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by alessandro on 18/03/15.
 */
public class GiornateAdmin extends JPanel {
    private JTable tabellaGiornate;
    private JPanel giornatePanel;

    private ArrayList<GiornataReale> listaGiornate = new ArrayList<GiornataReale>();

    final private Mysql db = new Mysql();

    private Utils utils = new Utils();

    public GiornateAdmin() {

        listaGiornate = db.selectGiornateAdmin();

        //se non sono mai state inserite creo le 38 giornate
        //che poi verranno mostrate nella tabella
        if (listaGiornate.isEmpty()) {
            for (int i = 1; i <= 38; i++) {
                listaGiornate.add(new GiornataReale(i));
            }
        }

        setTabella();

    }

    private void setTabella() {
        Object[] nomeColonne = {"Numero Giornata", "Data Inizio", "Ora Inizio", "Data fine", "Ora Fine"};
        //listaToArray ritorna un array di obkect che serve per il model
        Object[][] righeGiornate = utils.listaGiornateToArray(listaGiornate);
        //creo il modello con le colonne e i giocatori
        DefaultTableModel giocatoriModel = new DefaultTableModel(righeGiornate, nomeColonne) {
            //rende non modificabili le colonne dell'ID,cognome e ruolo
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 ? false : true;
            }
        };

        tabellaGiornate.setModel(giocatoriModel);

    }

}
