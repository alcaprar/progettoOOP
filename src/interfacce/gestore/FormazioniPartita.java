package interfacce.gestore;

import entità.Partita;
import utils.RenderTableAlternateFormazione;
import utils.TableNotEditableModel;

import javax.swing.*;

/**
 * Classe per mostrare le formazioni di una partita già giocata.
 * Estende un JFrame.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class FormazioniPartita extends JFrame{
    private JPanel mainPanel;
    private JTable formCasa;
    private JTable formOspite;
    private JLabel bonusCasalbl;
    private JLabel puntiOspite;
    private JLabel puntiCasa;
    private JLabel squadraCasa;
    private JLabel squadraOspite;
    private JPanel bonusCasaPanel;
    private JLabel golCasa;
    private JLabel golFuori;

    private Partita partita;
    private int bonusCasa;

    /**
     * Costruttore del JFrame.
     * @param bonusCasa valore del bonus casa del campionato. se è diverso da zero viene mostrato
     * @param partita partita di cui mostrare le formazioni
     */
    public FormazioniPartita(int bonusCasa, Partita partita) {
        super(partita.getFormCasa().getSquadra().getNome() +" "+String.valueOf(partita.getGolCasa())+"-"+String.valueOf(partita.getGolFuori())+" "+partita.getFormOspite().getSquadra().getNome());
        this.bonusCasa =bonusCasa;
        this.partita = partita;

        //inserisce i campi voluti nei vari label
        squadraCasa.setText(partita.getFormCasa().getSquadra().getNome());
        squadraOspite.setText(partita.getFormOspite().getSquadra().getNome());
        puntiCasa.setText(String.valueOf(partita.getPuntiCasa()));
        puntiOspite.setText(String.valueOf(partita.getPuntiFuori()));
        golCasa.setText(String.valueOf(partita.getGolCasa()));
        golFuori.setText(String.valueOf(partita.getGolFuori()));
        if(bonusCasa!=0){
            bonusCasalbl.setText(String.valueOf(bonusCasa));
        } else{
            bonusCasaPanel.setVisible(false);
        }

        //chiama la funzione che crea la tabella
        setTable();

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Setta le tabelle per mostrare la formazione.
     * Viene utilizzato un modello modificato per rendere non editabile la tabella.
     * Viene utilizzato un render modificato per cambiare il colore delle righe,
     * per differenziare i giocatori titolari dalla panchina.
     */
    private void setTable(){
        //definisce le intestazioni delle colonne
        Object[] nomeColonne = {"Giocatore", "Voto"};

        //prende il contenuto da inserire nelle due tabelle
        Object[][] righeFormCasa = partita.getFormCasa().listaFormToArray();
        Object[][] righeFormOspite = partita.getFormOspite().listaFormToArray();

        //chiama la funzione che crea una tabella non modificabile passandogli il contenuto e l'intestazione delle colonne
        TableNotEditableModel formCasaModel = new TableNotEditableModel(righeFormCasa, nomeColonne);
        TableNotEditableModel formOspiteModel = new TableNotEditableModel(righeFormOspite, nomeColonne);

        //assegna alla tabella formCasa il la tabella che ha creato
        formCasa.setModel(formCasaModel);
        formOspite.setModel(formOspiteModel);

        //chiama la funzione che setta il colore delle righe alternato
        formCasa.setDefaultRenderer(Object.class, new RenderTableAlternateFormazione());
        formOspite.setDefaultRenderer(Object.class, new RenderTableAlternateFormazione());

    }
}
