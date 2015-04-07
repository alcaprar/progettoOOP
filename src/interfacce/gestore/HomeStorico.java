package interfacce.gestore;

import db.Mysql;
import entità.Storico;
import interfacce.login.CaricamentoDati;
import interfacce.login.Login;
import utils.RenderTableAlternate;
import utils.TableNotEditableModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe per la visualizzazione dello storico.
 * Estende un JFrame.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class HomeStorico extends JFrame {
    private JPanel mainPanel;
    private JPanel calendarioPanel;
    private JTable classificaTable;
    private JPanel topPanel;
    private JLabel nomeCampionato;
    private JLabel annoCampionato;
    private JLabel presidente;

    private Storico storico;

    /**
     * Costruttore della pagina per la visualizzazione dello storico.
     * @param storico oggetto storico da visualizzare
     * @param caricamento
     * @param loginForm
     */
    public HomeStorico(Storico storico,CaricamentoDati caricamento, final Login loginForm){
        super("JFantacalcio - Storico");
        this.storico=storico;

        storico.setCalendario(Mysql.selectGiornateStorico(storico));
        storico.setClassifica(Mysql.selectClassificaStorico(storico));

        nomeCampionato.setText(storico.getNome());
        annoCampionato.setText(String.valueOf(storico.getAnno()));
        presidente.setText(storico.getPresidente());

        setCalendario();
        setClassifica();

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        caricamento.dispose();
        setVisible(true);

        //quando viene chiuso il frame rivisualizza la schermata di login
        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginForm.setVisible(true);
                getFrame().dispose();
            }
        });
        }

    /**
     * Setta il pannello del calendario.
     * Per ogni giornata viene creato un nuovo oggetto TabellaGiornata.
     * @see interfacce.gestore.TabellaGiornata
     */
    private void setCalendario(){
        calendarioPanel.setLayout(new BoxLayout(calendarioPanel,BoxLayout.Y_AXIS));
        int j=0;
        while(j<storico.getCalendario().size()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            for(int i=0;i<2 && j<storico.getCalendario().size();i++){
                TabellaGiornata giornataIesima = new TabellaGiornata(storico.getCalendario().get(j));
                panel.add(giornataIesima);
                j++;
            }
            calendarioPanel.add(panel);
        }
    }

    /**
     * Setta la tabella che mostra la classifica.
     */
    private void setClassifica(){
        Object[] nomeColonne = {"Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"};
        Object[][] righeClassifica = storico.classificaToArray();
        TableNotEditableModel classificaModel = new TableNotEditableModel(righeClassifica, nomeColonne){
            //restituisce la classe della colonna
            //serve per il row sorter
            @Override
            public Class getColumnClass(int column) {
                return column==0 ? String.class : Integer.class;
            }
        };

        classificaTable.setModel(classificaModel);

        //ordina le righe
        classificaTable.setAutoCreateRowSorter(true);

        //setta il colore delle righe alternato
        classificaTable.setDefaultRenderer(Object.class, new RenderTableAlternate());

        //setta l'altezza delle righe
        classificaTable.setRowHeight(50);
    }

    /**
     * Ritorna l'oggetto principale.
     * @return this
     */
    private HomeStorico getFrame(){
        return this;
    }
}
