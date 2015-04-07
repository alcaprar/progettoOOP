package interfacce.gestore;

import astaLive.ClientGUI;
import astaLive.ServerGUI;
import entità.*;
import interfacce.login.Login;
import org.joda.time.*;
import utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

/**
 * Pagina home dell'gestore. Mostra le informazioni
 * sul campionato più importanti.
 * Estende un JPanel.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Mattioli
 */
public class Home extends JPanel {
    private JPanel panel1;
    private JButton inviaLaFormazioneButton;
    private JList listaAvvisi;
    private JLabel nomeSquadra;
    private JLabel nomeUtente;
    private JTable tableClassifica;
    private JScrollPane scrollpaneClassifica;
    private JLabel nomeCampionato;
    private JTable ultimaGiornataTable;
    private JTable prossimaGiornataTable;
    private JLabel campionatoFinitolbl;
    private JLabel campionatoIniziolbl;
    private JScrollPane ultimaGiornataScrollPane;
    private JScrollPane prossimaGiornataScrollPane;
    private JTextArea testoAvvisi;
    private JPanel panelClassifica;
    private JLabel dataProssimalbl;
    private JLabel giornataProssimalbl;
    private JLabel giornataUltimalbl;
    private JLabel dataUltimalbl;
    private JLabel giornilbl;
    private JLabel orelbl;
    private JLabel minutilbl;
    private JLabel secondilbl;
    private JPanel ultimaGiornataPanel;
    private JButton buttonAsta;
    private JButton buttonServer;
    private JButton buttonLogout;

    private Utils utils = new Utils();

    private DateTime prossimaGiornata;

    private Squadra squadra;

    private Applicazione applicazione;

    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Home(Applicazione app){
        applicazione = app;

        inviaLaFormazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicazione.getTabbedPane().setSelectedIndex(1);
            }
        });

        listaAvvisi.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    JList source = (JList) e.getSource();
                    int numeroAvviso = source.getSelectedIndex();
                    testoAvvisi.setText(squadra.getCampionato().getListaAvvisi().get(numeroAvviso)[1]);

                }

            }
        });

        buttonAsta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                applicazione.dispose();
                ClientGUI clientGUI = new ClientGUI(applicazione, squadra.getCampionato(), squadra.getProprietario().duplicate());
            }
        });

        buttonServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonAsta.setEnabled(true);
                ServerGUI serverGUI = new ServerGUI(applicazione, squadra.getCampionato());
            }
        });

        buttonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Login login = new Login();
                applicazione.dispose();
            }
        });

    }

    /**
     * Aggiorna la pagina con i dati della squadra e del campionato.
     * Viene chiamata da gestore dopo che è stato settato il riferimento
     * interno a squadra.
     */
    public void refresh(){
        buttonAsta.setVisible(false);
        buttonServer.setVisible(false);
        nomeSquadra.setText(squadra.getNome());
        nomeUtente.setText(squadra.getProprietario().getNickname());
        nomeCampionato.setText(squadra.getCampionato().getNome());
        setTableClassifica();
        setListaAvvisi();
        if(squadra.getCampionato().isAstaLive() && squadra.getCampionato().isGiocatoriDaInserire()) {
            buttonAsta.setVisible(true);
            if (squadra.getProprietario().isPresidenteLega()){
                buttonServer.setVisible(true);
                buttonAsta.setEnabled(false);
            }
        }
        if(squadra.getCampionato().getProssimaGiornata()==1){
            ultimaGiornataScrollPane.setVisible(false);
            campionatoIniziolbl.setVisible(true);
            ultimaGiornataPanel.setVisible(false);
            dataUltimalbl.setVisible(false);
        } else{
            setTableUltimaG();
            campionatoIniziolbl.setVisible(false);
            giornataUltimalbl.setText(String.valueOf(squadra.getCampionato().getProssimaGiornata() - 1));
            dataUltimalbl.setText(df.format(squadra.getCampionato().getCalendario().get(squadra.getCampionato().getProssimaGiornata() - 2).getGioReale().getDataOraInizio()));
        }

        if(squadra.getCampionato().getCalendario().get(squadra.getCampionato().getProssimaGiornata()-1).getGioReale().getNumeroGiornata()==squadra.getCampionato().getGiornataFine()){
            prossimaGiornataScrollPane.setVisible(false);
            campionatoFinitolbl.setVisible(true);
            giornataProssimalbl.setVisible(false);
            dataProssimalbl.setVisible(false);
        } else {
            setTableProssimaG();
            campionatoFinitolbl.setVisible(false);
            giornataProssimalbl.setText(String.valueOf(squadra.getCampionato().getProssimaGiornata()));
            dataProssimalbl.setText(df.format(squadra.getCampionato().getCalendario().get(squadra.getCampionato().getProssimaGiornata()-1).getGioReale().getDataOraInizio()));
        }
    }

    /**
     * Setta il riferimento alla squadra loggato.
     * Viene utilizzato da gestore.
     * @param squadra
     */
    public void setSquadra(Squadra squadra){
        this.squadra = squadra;
        long prossima = squadra.getCampionato().prossimaGiornata().getGioReale().getDataOraInizio().getTime();
        long orarioConsegna = squadra.getCampionato().getOrarioConsegna() * 60 * 1000;
        this.prossimaGiornata = new DateTime(prossima-orarioConsegna);
    }

    /**
     * Fa partire il countdown per l'inserimento della prossima formazione.
     * Viene utilizzato da gestore.
     */
    public void startCountDown(){
        final Timer t = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (prossimaGiornata.isAfter(new DateTime())) {
                    //ora e tempo in questo momento
                    DateTime adesso = new DateTime();
                    //numero di secondi fino alla limite per l'inserimento della formazione
                    int secondiTotali = Seconds.secondsBetween(adesso,prossimaGiornata).getSeconds();
                    //calcolo dei giorni, ore, minuti e secondi
                    int d = secondiTotali/60/60/24;
                    int h = secondiTotali/60/60%24;
                    int m = secondiTotali/60%60;
                    int s = secondiTotali%60;
                    //aggiorno i label del countdown
                    aggiornaCountdown(d,h,m,s);
                } else{
                    return;
                }
            }
        });
        t.start();
    }

    /**
     * Restituisce il l'oggetto prossima giornata.
     * Serve in gestore per controllare se è ancora possibile inserire la formazione.
     * @return
     */
    public DateTime getProssimaGiornata(){
        return this.prossimaGiornata;
    }

    /**
     * Setta la tabella della prossima giornata.
     * Viene utilizzato un modello modificato per rendere le celle non modificabili.
     * Viene utilizzato un render modificato per far mostrare il colore delle righe alternato.
     */
    private void setTableProssimaG(){
        Object[] nomeColonne = {"Casa","Trasferta"};
        Object[][] righeProssimaGiornata = squadra.getCampionato().prossimaGiornata().prossimaGiornataToArray();

        TableNotEditableModel prossimaGiornataModel = new TableNotEditableModel(righeProssimaGiornata, nomeColonne);
        prossimaGiornataTable.setModel(prossimaGiornataModel);
        //setta il colore delle righe alternato
        prossimaGiornataTable.setDefaultRenderer(Object.class, new RenderTableAlternate());
    }

    /**
     * Setta la tabella della scorsa giornata.
     * Viene utilizzato un modello modificato per rendere le celle non modificabili.
     * Viene utilizzato un render modificato per far mostrare il colore delle righe alternato.
     */
    private void setTableUltimaG(){
        Object[] nomeColonne = {"Casa","PuntiCasa","GolCasa","-","GolTrasferta","PuntiTrasferta","Trasferta"};
        Object[][] righeUltimaGiornata = squadra.getCampionato().ultimaGiornata().partiteToArray();

        TableNotEditableModel prossimaGiornataModel = new TableNotEditableModel(righeUltimaGiornata, nomeColonne);
        ultimaGiornataTable.setModel(prossimaGiornataModel);
        //setta il colore delle righe alternato
        ultimaGiornataTable.setDefaultRenderer(Object.class, new RenderTableAlternate());

        ultimaGiornataTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int riga = ultimaGiornataTable.getSelectedRow();
                    new FormazioniPartita(squadra.getCampionato().getBonusCasa(), squadra.getCampionato().ultimaGiornata().getPartite().get(riga));
                }
            }
        });
        ultimaGiornataTable.setToolTipText("Doppio clic sulla partita per mostrare i dettagli.");

    }

    /**
     * Setta la tabella della classifica.
     * Nella classifica viene mostrato solo il nome della squadra e i punti.
     * Viene utilizzato un modello modificato per rendere le celle non modificabili.
     * Inoltre viene fatto l'override del metodo getColumnClass che serve per l'ordinamento delle righe.
     * Viene utilizzato un render modificato per far mostrare il colore delle righe alternato.
     */
    private void setTableClassifica(){
        Object[] nomeColonne = {"Squadra", "Punti"};
        Object[][] righeClassifica = squadra.getCampionato().classificaToArrayPiccola();
        //Object[][] righeClassifica = utils.listaClassificaToArrayPiccola(squadra.getCampionato().getClassifica());

        TableNotEditableModel classificaModel = new TableNotEditableModel(righeClassifica, nomeColonne){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        tableClassifica.setModel(classificaModel);

        tableClassifica.setAutoCreateRowSorter(true);

        //setta il colore delle righe alternato
        tableClassifica.setDefaultRenderer(Object.class, new RenderTableAlternate());
    }

    /**
     * Viene popolata la Jlist con il titolo degli avvisi del campionato.
     */
    private void setListaAvvisi(){
        DefaultListModel listaAvvisiModel = new DefaultListModel();
        for(String[] avviso:squadra.getCampionato().getListaAvvisi()){
            listaAvvisiModel.addElement(avviso[0]);
        }
        listaAvvisi.setModel(listaAvvisiModel);
    }

    /**
     * Aggiorna le label del Countdown in base ai parametri passati.
     * Viene utilizzato dal timer per aggiornare ogni secondo il countdown.
     * @param giorni
     * @param ore
     * @param minuti
     * @param secondi
     */
    public void aggiornaCountdown(long giorni, long ore, long minuti, long secondi){
        this.giornilbl.setText(String.valueOf(giorni));
        this.orelbl.setText(String.valueOf(ore));
        this.minutilbl.setText(String.valueOf(minuti));
        this.secondilbl.setText(String.valueOf(secondi));
    }
}
