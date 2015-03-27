package interfacce;

import classi.Giocatore;
import classi.Squadra;
import db.Mysql;
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
    private JLabel nomeSquadra;
    private JTable tableRosa;
    private JButton modificaName;
    private JButton modificaCog;
    private JLabel nomeCampL;
    private JLabel numeroPartL;
    private JLabel numeroGiornL;
    private JLabel giornataAttL;
    private JTextField nickText;
    private JTextField mailText;
    private JTextField nomeText;
    private JTextField cognomeText;

    private Squadra squadra;
    private DefaultListModel<String> listModel;
    private String[] colonne;
    final private Mysql db = new Mysql();

    public Info(){

        modificaName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!nomeText.equals("")) {
                    squadra.getProprietario().setNome(nomeText.getText());
                    db.aggiornaNomeUtente(squadra.getProprietario());
                    miniRefresh();
                } else if(!squadra.getProprietario().getNome().equals("")){
                    nomeText.setText(squadra.getProprietario().getNome());
                } else nomeText.setText("Nome non inserito");
            }
        });

        modificaCog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!cognomeText.equals("")) {
                    squadra.getProprietario().setCognome(cognomeText.getText());
                    db.aggiornaCognomeUtente(squadra.getProprietario());
                    miniRefresh();
                } else if(!squadra.getProprietario().getNome().equals("")){
                    cognomeText.setText(squadra.getProprietario().getCognome());
                } else cognomeText.setText("Cognome non inserito");
            }
        });

    }

    public void setSquadra(Squadra squadra){
        this.squadra = squadra;
    }

    public void refresh(){
        nomeSquadra.setText(squadra.getNome());
        nickText.setEditable(false);
        nickText.setText(squadra.getProprietario().getNickname());
        if(!squadra.getProprietario().getNome().equals("")){
            nomeText.setText(squadra.getProprietario().getNome());
        } else nomeText.setText("Nome non inserito");
        if(!squadra.getProprietario().getCognome().equals("")) {
            cognomeText.setText(squadra.getProprietario().getCognome());
        } else cognomeText.setText("Cognome non inserito");
        mailText.setText(squadra.getProprietario().getEmail());
        mailText.setEditable(false);
        nomeCampL.setText(squadra.getCampionato().getNome());
        numeroPartL.setText(String.valueOf(squadra.getCampionato().getNumeroPartecipanti()));
        numeroGiornL.setText(String.valueOf(squadra.getCampionato().getGiornataFine()-squadra.getCampionato().getGiornataInizio() + 1));
        giornataAttL.setText(String.valueOf(squadra.getCampionato().getProssimaGiornata()));
        setTableRosa();
    }

    public void miniRefresh(){
        nomeText.setText(squadra.getProprietario().getNome());
        cognomeText.setText(squadra.getProprietario().getCognome());
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
        tableRosa.setDefaultRenderer(Object.class, new RenderTableAlternate());
        tableRosa.setModel(tableModel);
    }



}
