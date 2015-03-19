package interfacce;

import classi.Giocatore;

import javax.swing.*;
import javax.swing.text.StringContent;
import java.util.ArrayList;

/**
 * Created by Christian on 19/03/2015.
 */
public class GestioneGiocatori extends JPanel{


    private JPanel mainPanel;
    private JList listaGiocatori;
    private JList list1;

    private DefaultListModel<String> listaG;
    private ArrayList<Giocatore> giocatori;

    public GestioneGiocatori(ArrayList<Giocatore> gioc){

        giocatori = gioc;

        creaListe();
    }


    private void createUIComponents() {
        listaG = new DefaultListModel<String>();
    }

    private void creaListe(){
        for(Giocatore g : giocatori) listaG.addElement(g.getCognome());
    }
}
