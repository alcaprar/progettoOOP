package interfacce;


import classi.Squadra;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Giacomo on 12/03/15.
 */
public class Classifica {
    private JPanel panel1;
    private JTable tableClassifica;

    private Squadra squadra;

    Utils utils = new Utils();

    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
    }

    public void refresh(){
        setTableClassifica();
    }

    private void setTableClassifica(){
        Object[] nomeColonne = {"Pos", "Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArray(squadra.getCampionato().getClassifica());

        DefaultTableModel classificaModel = new DefaultTableModel(righeClassifica, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableClassifica.setModel(classificaModel);
    }

}


