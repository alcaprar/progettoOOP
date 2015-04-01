package interfacce.Applicazione;

import classi.Squadra;
import utils.RenderTableAlternate;
import utils.TableNotEditableModel;
import utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Classe per la visualizzazione delle squadre del campionato.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Squadre extends JPanel{
    private JComboBox selSqua;
    private JLabel proprietario;
    private JTable tableRosa;
    private JPanel mainPanel;

    private Squadra squadra;

    private ArrayList<DefaultTableModel> listaTableModel = new ArrayList<DefaultTableModel>();

    Utils utils = new Utils();

    /**
     * Costruttore della pagina.
     */
    public Squadre(){
        selSqua.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int i = selSqua.getSelectedIndex();
                tableRosa.setModel(listaTableModel.get(i));
            }
        });
    }

    /**
     * Setta il riferimento interno alla squadra.
     * @param squadra
     */
    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
    }

    /**
     * Aggiorna la pagina dopo che è stato settato il riferimento interno.
     * Richiama dei metodi per aggiornare la combobox e la tabella.
     * @see #setTabellaGiocatori()
     * @see #setComboBox()
     */
    public void refresh(){
        setComboBox();
        proprietario.setText(squadra.getProprietario().getNickname());
        setTabellaGiocatori();
    }

    /**
     * Setta il combobox con la lista delle squadre.
     */
    public void setComboBox(){
        DefaultComboBoxModel comboBoxModel;
        comboBoxModel = new DefaultComboBoxModel(squadra.getCampionato().squadreToArray());
        selSqua.setModel(comboBoxModel);
        selSqua.setToolTipText("Scegli la squadra da visualizzare");
    }

    /**
     * Setta la tabella che mostra la lista di giocatori.
     * Viene creato un table model per ogni squadra.
     */
    private void setTabellaGiocatori() {
        for(Squadra squadre : squadra.getCampionato().getListaSquadrePartecipanti()){
            Object[][] righeGiocatori = squadre.listaGiocatoriRosaToArray();
            //Object[][] righeGiocatori = utils.listaGiocatoriToArraySquadre(squadre.getGiocatori());
            Object[] nomeColonne = {"Cognome", "Ruolo", "Squadra Reale", "Prezzo Pagato"};
            TableNotEditableModel giocatoriModel = new TableNotEditableModel(righeGiocatori, nomeColonne);
            listaTableModel.add(giocatoriModel);
        }

        tableRosa.setModel(listaTableModel.get(0));

        //setta il colore delle righe alternato
        tableRosa.setDefaultRenderer(Object.class, new RenderTableAlternate());
    }

}
