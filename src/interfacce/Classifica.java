package interfacce;


import classi.Squadra;
import utils.TableNotEditableModel;
import utils.RenderTableAlternate;
import utils.Utils;

import javax.swing.*;

/**
 * Created by Giacomo on 12/03/15.
 */
public class Classifica extends JPanel {
    private JPanel mainPanel;
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
        Object[] nomeColonne = {"Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArray(squadra.getCampionato().getClassifica());

        TableNotEditableModel classificaModel = new TableNotEditableModel(righeClassifica, nomeColonne){
            @Override
            public Class getColumnClass(int column) {
                return column==0 ? String.class : Integer.class;

            }
        };

        tableClassifica.setModel(classificaModel);

        tableClassifica.setAutoCreateRowSorter(true);

        //setta il colore delle righe alternato

        tableClassifica.setDefaultRenderer(Object.class, new RenderTableAlternate());

        tableClassifica.setRowHeight(50);
    }

}


