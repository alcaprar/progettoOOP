package interfacce.Applicazione;

import classi.Giocatore;
import classi.Squadra;
import db.Mysql;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe per la visualizzazione delle info personali e del campionato.
 * Estende un JPanel.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
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
    private JTextField titoloMessaggio;
    private JTextArea testoMessaggio;
    private JButton inviaButton;

    private Squadra squadra;
    private DefaultListModel<String> listModel;
    final private Mysql db = new Mysql();

    /**
     * Costruttore della pagina per la visualizzazione delle info.
     */
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

        inviaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(db.inserisciMessaggio(squadra, titoloMessaggio.getText(), testoMessaggio.getText())){
                    JOptionPane.showMessageDialog(null, "Avviso inserito con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    titoloMessaggio.setText("");
                    testoMessaggio.setText("");
                }

            }
        });

    }

    /**
     * Setta il riferimento interno a squadra.
     * @param squadra
     */
    public void setSquadra(Squadra squadra){
        this.squadra = squadra;
    }

    /**
     * Aggiorna la pagina dopo che è stato settato il riferimento a squadra.
     */
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

    /**
     * Aggiorna solo i label del nome e cognome se vengono modificati.
     */
    public void miniRefresh(){
        nomeText.setText(squadra.getProprietario().getNome());
        cognomeText.setText(squadra.getProprietario().getCognome());
    }

    /**
     * Setta la tabella per mostrare la lista dei giocatori della squadra.
     */
    private void setTableRosa(){
        TableNotEditableModel giocatoriModel;
        Object[] colonne = {"ID", "Cognome", "Ruolo","Squadra Reale","Prezzo iniziale", "Prezzo d'Acquisto"};
        Object[][] righeGiocatori = squadra.listaGiocatoriRosaToArray();
        giocatoriModel = new TableNotEditableModel(righeGiocatori,colonne);
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){ return false;}
        };

        tableRosa.setDefaultRenderer(Object.class, new RenderTableAlternate());
        tableRosa.setModel(giocatoriModel);
    }



}
