package interfacce;

import classi.Giocatore;
import classi.Squadra;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Christian on 23/03/2015.
 */
public class Info extends JPanel{
    private JPanel mainPanel;
    private JPanel infoUtente;
    private JLabel nomeUtente;
    private JLabel nomeSquadra;
    private JLabel nomeL;
    private JLabel cognomeL;
    private JLabel mailL;
    private JTable tableRosa;
    private JButton modificaName;
    private JButton modificaNick;
    private JButton modificaCog;
    private JButton modificaMail;

    private Squadra squadra;
    private DefaultListModel<String> listModel;
    private Utils utils;
    private String[] colonne;

    public Info(){

        /*modificaNick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nick = JOptionPane.showInputDialog("Inserisci il nuovo nickname");
                squadra.getProprietario().setNickname(nick);
                miniRefresh();
            }
        });

        modificaName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nome = JOptionPane.showInputDialog("Inserisci il nuovo nome");
                squadra.getProprietario().setNome(nome);
                miniRefresh();
            }
        });

        modificaCog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String cognome = JOptionPane.showInputDialog("Inserisci il nuovo cognome");
                squadra.getProprietario().setCognome(cognome);
                miniRefresh();
            }
        });

        modificaMail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String mail = JOptionPane.showInputDialog("Inserisci la nuova mail");
                squadra.getProprietario().setEmail(mail);
                miniRefresh();
            }
        });*/

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
        setTableRosa();
    }

    public void miniRefresh(){
        nomeUtente.setText(squadra.getProprietario().getNickname());
        nomeL.setText(squadra.getProprietario().getNome());
        cognomeL.setText(squadra.getProprietario().getCognome());
        mailL.setText(squadra.getProprietario().getEmail());
    }

    private void setTableRosa(){
        colonne = new String[]{"ID", "Cognome", "Ruolo","Squadra Reale","Prezzo iniziale", "Prezzo d'Acquisto"};
        DefaultTableModel tableModel = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column){ return false;}
        };
        if(!squadra.getCampionato().isGiocatoriDaInserire()) {
            tableModel.setColumnIdentifiers(colonne);
            for (Giocatore g : squadra.getGiocatori()) {
                String id = String.valueOf(g.getID());
                String cog = g.getCognome();
                String ruolo = String.valueOf(g.getRuolo());
                String squadraR = g.getSquadraReale();
                String prezzoIn = String.valueOf(g.getPrezzoBase());
                String prezzoAcq = String.valueOf(g.getPrezzoAcquisto());
                tableModel.addRow(new String[]{id, cog, ruolo, squadraR, prezzoIn, prezzoAcq});
            }
        } else {
            tableModel.setColumnIdentifiers(new String[]{"Attenzione"});
            tableModel.addRow(new String[]{"Ancora non sono stati inseriti giocatori nella squadra."});
        }
        tableRosa.setModel(tableModel);
    }



}
