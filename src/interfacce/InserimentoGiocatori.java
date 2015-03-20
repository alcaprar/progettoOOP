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
    private DefaultListModel<Giocatore> listaGiocatoriModel;
    private ArrayList<DefaultListModel<Giocatore>> listeSquadreModel;
    private Persona utente;


    public InserimentoGiocatori(Persona ut, ArrayList<Giocatore> giocatori){

        utente = ut;


        listeSquadreModel = new ArrayList<DefaultListModel<Giocatore>>(10);
        listaGiocatoriModel = new DefaultListModel<Giocatore>();

        creaListaGiocatori(giocatori);
        creaListeSquadra(utente.getPresidenza());

        creaComboBox(utente);
        comboBox.setModel(comboBoxModel);
        comboBox.addItemListener(this);


    }

    //override del metodo itemStateChanged
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        listaSquadra.setModel((DefaultListModel<Giocatore>) itemEvent.getItem());
    }

    private void creaComboBox(Persona utente){
        for(Squadra i : utente.getPresidenza()) comboBoxModel.addElement(i);
    }

    private void creaListeSquadra(ArrayList<Squadra> squadre){
        int i = 0;
        for(Squadra s : squadre) {
            listeSquadreModel.set(i, new DefaultListModel<Giocatore>());
            for (Giocatore g : s.getGiocatori()) listeSquadreModel.get(i).addElement(g);
            i++;
        }
    }

    private void creaListaGiocatori(ArrayList<Giocatore> giocatori){
        for(Giocatore i : giocatori) listaGiocatoriModel.addElement(i);
    }
}
