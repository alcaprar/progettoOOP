package interfacce.Applicazione;

import classi.Squadra;
import db.Mysql;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alessandro on 24/03/15.
 */
public class GestioneLega extends JPanel{
    private JTextArea testoAvviso;
    private JButton inviaButton;
    private JTextField titoloAvviso;
    private JPanel mainPanel;
    private JLabel abilitatolbl;
    private JButton calcolaButton;
    private JLabel nrGiolbl;
    private JLabel nonAbilitatolbl;
    private JPanel calcolaPanel;
    private JList listaMessaggi;
    private JTextArea testoMessaggi;

    private Squadra squadra;

    private Calendario calendarioPanel;

    private Home homePanel;

    private Classifica classificaPanel;

    private final Mysql db = new Mysql();

    private int giornataVotiInseriti;
    private int ultimaGiornataReale;

    public void setSquadra(Squadra sqr){
        this.squadra = sqr;
    }

    public void setCalendario(Calendario cal){
        this.calendarioPanel = cal;
    }

    public void setHome(Home hm){
        this.homePanel = hm;
    }

    public void setClassifica(Classifica clas){
        this.classificaPanel = clas;
    }

    public void refresh(){
        ultimaGiornataReale = squadra.getCampionato().prossimaGiornata().getGioReale().getNumeroGiornata();
        if(giornataVotiInseriti!=ultimaGiornataReale){
            calcolaPanel.setVisible(false);
            nonAbilitatolbl.setVisible(true);
        } else {
            nonAbilitatolbl.setVisible(false);
            nrGiolbl.setText(String.valueOf(squadra.getCampionato().prossimaGiornata().getNumGiornata()));
        }
    }

    private GestioneLega getPanel(){
        return this;
    }

    public GestioneLega(){

        giornataVotiInseriti = db.selectGiornateVotiInseriti();


        inviaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(db.inserisciAvviso(squadra.getCampionato(), titoloAvviso.getText(), testoAvviso.getText())){
                    JOptionPane.showMessageDialog(null, "Avviso inserito con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    titoloAvviso.setText("");
                    testoAvviso.setText("");
                }

            }
        });

        calcolaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(squadra.getCampionato().prossimaGiornata().getNumGiornata());
                int primaFascia = squadra.getCampionato().getPrimaFascia();
                int largFascia= squadra.getCampionato().getLargFascia();
                int bonusCasa = squadra.getCampionato().getBonusCasa();
                //calcolo i risultati mettendo i risultati nell'oggetto della giornata
                squadra.getCampionato().prossimaGiornata().calcolaGiornataNew(primaFascia,largFascia,bonusCasa);
                //aggiorno la classifica del campionato
                squadra.getCampionato().aggiornaClassifica(squadra.getCampionato().prossimaGiornata());
                //inserisco gli aggiornamenti nel db
                if(db.inserisciRisultatiGiornata(squadra.getCampionato())){
                    //aggiorno la prossima giornata
                    squadra.getCampionato().setProssimaGiornata(squadra.getCampionato().getProssimaGiornata() + 1);
                    //permetto l'inserimento per la prossima partita
                    squadra.setFormazioneInserita(false);
                    //aggiorno i vari pannelli con i nuovi dati
                    calendarioPanel.refresh();
                    classificaPanel.refresh();
                    homePanel.refresh();
                    getPanel().refresh();
                }

            }
        });

        listaMessaggi.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    JList source = (JList) e.getSource();
                    int numeroMessaggio = source.getSelectedIndex();
                    testoMessaggi.setText(squadra.getCampionato().getListaMessaggi().get(numeroMessaggio)[2]);

                }

            }
        });
    }

    private void setListaMessaggi(){
        DefaultListModel listaMessaggiModel = new DefaultListModel();
        for(String[] messaggio:squadra.getCampionato().getListaMessaggi()){
            listaMessaggiModel.addElement(messaggio[1] +" - "+messaggio[0]);
        }
        listaMessaggi.setModel(listaMessaggiModel);
    }
}
