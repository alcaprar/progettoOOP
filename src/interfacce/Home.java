package interfacce;

import classi.*;
import com.sun.org.apache.bcel.internal.classfile.LineNumberTable;
import utils.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by alessandro on 11/03/15.
 */
public class Home extends JPanel {
    private JPanel panel1;
    private JButton inviaLaFormazioneButton;
    private JList list1;
    private JLabel nomeSquadra;
    private JLabel nomeUtente;
    private JTable tableClassifica;
    private JScrollPane scrollpaneClassifica;
    private JLabel nomeCampionato;
    private JTable ultimaGiornataTable;
    private JTable prossimaGiornataTable;

    private Squadra squadra;

    private Applicazione applicazione;

    public void setSquadre(Squadra squadra){
        this.squadra = squadra;
    }

    private Utils utils = new Utils();

    public Home(Applicazione app){
        applicazione = app;

        inviaLaFormazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicazione.getTabbedPane().setSelectedIndex(1);
            }
        });
    }

    public void refresh(){
        nomeSquadra.setText(squadra.getNome());
        nomeUtente.setText(squadra.getProprietario().getNickname());
        nomeCampionato.setText(squadra.getCampionato().getNome());
        setTableClassifica();
        if(squadra.getCampionato().getProssimaGiornata()<squadra.getCampionato().getGiornataFine()){
            setTableProssimaG();
        }
        if(squadra.getCampionato().getProssimaGiornata()>squadra.getCampionato().getGiornataInizio()){
            setTableUltimaG();
        }
    }

    private void setTableProssimaG(){
        Object[] nomeColonne = {"Casa","Trasferta"};
        Object[][] righeProssimaGiornata = squadra.getCampionato().prossimaGiornata().prossimaGiornataToArray();

        DefaultTableModel prossimaGiornataModel = new DefaultTableModel(righeProssimaGiornata, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        prossimaGiornataTable.setModel(prossimaGiornataModel);
        //setta il colore delle righe alternato
        prossimaGiornataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });
    }

    private void setTableUltimaG(){
        Object[] nomeColonne = {"","","","","","",""};
        Object[][] righeUltimaGiornata = squadra.getCampionato().ultimaGiornata().partiteToArray();

        DefaultTableModel prossimaGiornataModel = new DefaultTableModel(righeUltimaGiornata, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ultimaGiornataTable.setModel(prossimaGiornataModel);
        //setta il colore delle righe alternato
        ultimaGiornataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });
    }

    private void setTableClassifica(){
        Object[] nomeColonne = {"Squadra", "Punti"};
        Object[][] righeClassifica = utils.listaClassificaToArrayPiccola(squadra.getCampionato().getClassifica());

        DefaultTableModel classificaModel = new DefaultTableModel(righeClassifica, nomeColonne) {
            //rende non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableClassifica.setModel(classificaModel);

        //setta il colore delle righe alternato
        tableClassifica.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.CYAN);
                return c;
            }
        });
    }



}
