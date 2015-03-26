package interfacce;

import classi.Partita;
import classi.Squadra;
import utils.RenderTableAlternate;
import utils.TableNotEditableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Giacomo on 25/03/15.
 */
public class Partitagug extends JPanel{
    private JPanel partita;
    private JTable formCasa;
    private JTable formOspite;
    private JLabel bonusCasa;
    private JLabel puntiOspite;
    private JLabel puntiCasa;
    private JLabel squadraCasa;
    private JLabel utenteCasa;
    private JLabel squadraOspite;
    private JLabel utenteOspite;

    private Partita p;
    private int bc;

    public Partitagug(int bc) {
        this.bc=bc;
    }

    public void refresh(){
        //inserisce i campi voluti nei vari label
        utenteCasa.setText(p.getFormCasa().getSquadra().getProprietario().getNickname());
        utenteOspite.setText(p.getFormOspite().getSquadra().getProprietario().getNickname());
        squadraCasa.setText(p.getFormCasa().getSquadra().getNome());
        squadraOspite.setText(p.getFormOspite().getSquadra().getNome());
        puntiCasa.setText(String.valueOf(p.getPuntiCasa()));
        puntiOspite.setText(String.valueOf(p.getPuntiFuori()));
        bonusCasa.setText(String.valueOf(bc));
        //chiama la funzione che crea la tabella
        setTable();
    }

    private void setTable(){
        //definisce le intestazioni delle colonne
        Object[] nomeColonne = {"Giocatore", "Voto"};

        //prende il contenuto da inserire nelle due tabelle
        Object[][] righeFormCasa = p.getFormCasa().getSquadra().getFormazione().listaFormToArray();
        Object[][] righeFormOspite = p.getFormOspite().getSquadra().getFormazione().listaFormToArray();

        //chiama la funzione che crea una tabella non modificabile passandogli il contenuto e l'intestazione delle colonne
        TableNotEditableModel formCasaModel = new TableNotEditableModel(righeFormCasa, nomeColonne);
        TableNotEditableModel formOspiteModel = new TableNotEditableModel(righeFormOspite, nomeColonne);

        //assegna alla tabella formCasa il la tabella che ha creato
        formCasa.setModel(formCasaModel);
        formOspite.setModel(formOspiteModel);

        //chiama la funzione che setta il colore delle righe alternato
        formCasa.setDefaultRenderer(Object.class, new RenderTableAlternate());
        formOspite.setDefaultRenderer(Object.class, new RenderTableAlternate());

    }
}
