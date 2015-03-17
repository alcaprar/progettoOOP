package interfacce;



import classi.*;
import com.toedter.calendar.JDateChooser;
import db.Mysql;
import utils.*;


import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Created by alessandro on 16/03/15.
 */
public class AdminApp extends JFrame {
    private JButton giornateButton;
    private JPanel panel;
    private JTextField pathtxt;
    private JTabbedPane tabbedPane;
    private JButton quotazioniButton;
    private JTextField quotazionipath;
    private JButton votiButton;
    private JTextField votitxt;
    private JButton giornateInvia;
    private JButton quotazioniInvia;
    private JButton votiInvia;
    private JPanel panelCsv;
    private JPanel panelTabella;
    private JTable tabellaGiocatori;


    JDateChooser chooserDataInizio;
    JDateChooser chooserDataFine;

    Utils utils = new Utils();

    GiornataReale[] calendario;

    Giocatore[] giocatori;

    ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();

    String pathFile;

    final Mysql db ;

    public AdminApp() {
        //titolo del frame
        super("Admin - Gestore fantacalcio");

        db = new Mysql();

        setContentPane(panel);

        pack();

        //centra il frame
        setLocationRelativeTo(null);

        setVisible(true);

        setResizable(false);

        listaGiocatori = db.selectGiocatoriAdmin();

        //se la lista di giocatori non è vuota, cioè
        //è già stato caricato il csv una volta
        if(!listaGiocatori.isEmpty()){
            panelCsv.setVisible(false);
            panelTabella.setVisible(true);

        }
        else{
            panelCsv.setVisible(true);
            panelTabella.setVisible(false);
        }



        //creo i data chooser
        chooserDataInizio = new JDateChooser();
        chooserDataFine = new JDateChooser();

        //setto il formato della data
        chooserDataInizio.setDateFormatString("dd-mm-yy");
        chooserDataFine.setDateFormatString("dd-mm-yy");





        quotazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fc = new FileDialog(getFrame(), "Choose a file", FileDialog.LOAD);
                fc.setDirectory("/");
                fc.setVisible(true);
                String filename = fc.getFile();
                String path = fc.getDirectory();
                pathFile = fc.getDirectory()+fc.getFile();
                if (pathFile == null) {
                    System.out.println("You cancelled the choice");
                }
                else {
                    quotazionipath.setText(pathFile);
                    pack();
                }
            }
        });

        quotazioniInvia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.csvQuotazioni(pathFile,",");

            }
        });




    }

    public AdminApp getFrame(){
        return this;
    }

    public static void main(String args[]){
        AdminApp app = new AdminApp();
    }

    private void createUIComponents() {
        setTabellaGiocatori();

    }

    private void setTabellaGiocatori(){
        tabellaGiocatori = new JTable();

        Object[] nomeColonne= {"ID","Cognome","Ruolo","Squadra Reale","Costo"};
        Object[][] righeGiocatori;

        DefaultTableModel giocatoriModel = new DefaultTableModel();
    }


}
