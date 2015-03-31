package interfacce.Applicazione;

import classi.Storico;
import db.Mysql;
import interfacce.Applicazione.TabellaGiornata;
import interfacce.Login.CaricamentoDati;
import utils.RenderTableAlternate;
import utils.TableNotEditableModel;
import utils.Utils;

import javax.swing.*;

/**
 * Created by alessandro on 31/03/15.
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

    private Utils utils = new Utils();

    private Mysql db = new Mysql();

    public HomeStorico(Storico storico,CaricamentoDati caricamento){
        super("Storico");
        this.storico=storico;

        storico.setCalendario(db.selectGiornateStorico(storico));
        storico.setClassifica(db.selectClassificaStorico(storico));

        nomeCampionato.setText(storico.getNome());
        annoCampionato.setText(String.valueOf(storico.getAnno()));
        presidente.setText(storico.getPresidente());

        setCalendario();
        setClassifica();

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        caricamento.dispose();
        setVisible(true);
        }

    private void setCalendario(){
        calendarioPanel.setLayout(new BoxLayout(calendarioPanel,BoxLayout.Y_AXIS));
        int j=0;
        while(j<storico.getCalendario().size()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            for(int i=0;i<3 && j<storico.getCalendario().size();i++){
                TabellaGiornata giornataIesima = new TabellaGiornata(storico.getCalendario().get(j));
                panel.add(giornataIesima);
                j++;
            }
            calendarioPanel.add(panel);
        }
    }

    private void setClassifica(){
        Object[] nomeColonne = {"Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArray(storico.getClassifica());

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
}
