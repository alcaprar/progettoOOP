package interfacce;


import classi.Squadra;
import utils.TableNotEditableModel;
import utils.RenderTableAlternate;
import utils.Utils;

import javax.swing.*;

/**
 * Pagina della classifica. Mostra la classifica dettagliata del campionato.
 * Estende un JPanel.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Mattioli
 */
public class Classifica extends JPanel {
    private JPanel mainPanel;
    private JTable tableClassifica;

    private Squadra squadra;

    Utils utils = new Utils();
    /**
     * Setta il riferimento alla squadra loggato.
     * Viene utilizzato da Applicazione.
     * @param squadra
     */
    public void setSquadra(Squadra squadra){
        this.squadra = squadra;
    }
    /**
     * Aggiorna il panel con i dati della squadra e del campionato.
     * Viene chiamata da Applicazione dopo che è stato settato il riferimento
     * interno a squadra.
     */
    public void refresh(){
        setTableClassifica();
    }

    /**
     * Setta la tabella della classifica dettagliata.
     * Viene utilizzato un modello modificato per rendere le celle non modificabili.
     * Inoltre viene fatto l'override del metodo getColumnClass che serve per l'ordinamento delle righe.
     * Viene utilizzato un render modificato per far mostrare il colore delle righe alternato.
     */
    private void setTableClassifica(){
        Object[] nomeColonne = {"Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArray(squadra.getCampionato().getClassifica());

        TableNotEditableModel classificaModel = new TableNotEditableModel(righeClassifica, nomeColonne){
            //restituisce la classe della colonna
            //serve per il row sorter
            @Override
            public Class getColumnClass(int column) {
                return column==0 ? String.class : Integer.class;
            }
        };

        tableClassifica.setModel(classificaModel);

        //ordina le righe
        tableClassifica.setAutoCreateRowSorter(true);

        //setta il colore delle righe alternato
        tableClassifica.setDefaultRenderer(Object.class, new RenderTableAlternate());

        //setta l'altezza delle righe
        tableClassifica.setRowHeight(50);
    }

}


