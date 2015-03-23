package interfacce;

import classi.Giocatore;
import classi.Squadra;

import javax.swing.*;

/**
 * Created by Christian on 23/03/2015.
 */
public class Info extends JPanel{
    private JPanel mainPanel;
    private JList listaRosa;
    private JPanel infoUtente;
    private JLabel nomeUtente;
    private JLabel nomeSquadra;
    private JLabel nomeL;
    private JLabel cognomeL;
    private JLabel mailL;

    private Squadra squadra;
    private DefaultListModel<String> listModel;

    public Info(){


    creaRosa();

    }

    public void setSquadra(Squadra squadra){
        this.squadra = squadra;
    }

    public void refresh(){
        nomeSquadra.setText(squadra.getNome());
        nomeUtente.setText(squadra.getProprietario().getNickname());
        nomeL.setText(squadra.getProprietario().getNome());
        cognomeL.setText(squadra.getProprietario().getCognome());
        mailL.setText(squadra.getProprietario().getEmail());
        System.out.print(squadra.getCampionato().isGiocatoriDaInserire());
    }

    private void creaRosa(){
        listModel = new DefaultListModel<String>();
        /*if(squadra.getCampionato().isGiocatoriDaInserire()) {
            for (Giocatore g : squadra.getGiocatori()) {
                listModel.addElement(g.getCognome());
            }
        }else listModel.addElement(new String("La squadra non contiene ancora giocatori."));*/
        listaRosa.setModel(listModel);
    }



}
