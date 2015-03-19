package interfacce;

import classi.Giocatore;
import classi.Persona;
import classi.Squadra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by Christian on 19/03/2015.
 */
public class InserimentoGiocatori extends JPanel implements ItemListener{
    private JPanel mainPanel;
    private JList listaGiocatori;
    private JButton removeButton;
    private JButton addButton;
    private JComboBox comboBox;
    private JPanel pListaSquadra;
    private JList listaSquadra;

    private DefaultComboBoxModel<Squadra> comboBoxModel;
    private DefaultListModel<Giocatore> listaSquadraModel;
    private DefaultListModel<Giocatore> listaGiocatoriModel;
    private Persona utente;


    public InserimentoGiocatori(Persona ut, ArrayList<Giocatore> giocatori){

        utente = ut;


        listaGiocatoriModel = new DefaultListModel<Giocatore>();
        listaSquadraModel = new DefaultListModel<Giocatore>();

        creaListaGiocatori(giocatori);

        creaComboBox(utente);
        comboBox.setModel(comboBoxModel);
        comboBox.addItemListener(this);

        listaSquadra.setModel(listaSquadraModel);
        listaGiocatori.setModel(listaGiocatoriModel);

    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        creaListaSquadra((Squadra) itemEvent.getItem());
    }

    private void creaComboBox(Persona utente){
        for(Squadra i : utente.getPresidenza()) comboBoxModel.addElement(i);
    }

    private void creaListaSquadra(Squadra squadra){
        for(Giocatore i : squadra.getGiocatori()) listaSquadraModel.addElement(i);
    }

    private void creaListaGiocatori(ArrayList<Giocatore> giocatori){
        for(Giocatore i : giocatori) listaGiocatoriModel.addElement(i);
    }
}
