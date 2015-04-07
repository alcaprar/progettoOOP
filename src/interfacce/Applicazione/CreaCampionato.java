package interfacce.Applicazione;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.ArrayList;

import entità.*;
import db.Mysql;
import interfacce.Login.Login;


/**
 * Pagina per la creazione di un nuovo campionato.
 * Estende un JFrame.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class CreaCampionato extends JFrame {
    private JTextField nometxt;
    private JComboBox astaBox;
    private JLabel nomePresidente;
    private JSpinner inizioSpinner;
    private JSpinner limiteSpinner;
    private JSpinner fineSpinner;
    private JSpinner primafSpinner;
    private JSpinner fasceSpinner;
    private JComboBox numeroBox;
    private JPanel panel;
    private JButton creaCampionatoButton;
    private JLabel nomeInfo;
    private JLabel numeroInfo;
    private JLabel astaInfo;
    private JLabel inizioInfo;
    private JLabel fineInfo;
    private JLabel creditiInfo;
    private JLabel limiteInfo;
    private JLabel primafInfo;
    private JLabel fasceInfo;
    private JSpinner bonuscSpinner;
    private JLabel bcasaInfo;
    private JSpinner creditiSpinner;
    private JList partecipantiList;
    private JList utentiList;
    private JButton aggiungiButton;
    private JButton rimuoviButton;
    private JButton annullaButton;

    private ArrayList<Persona> listaUtenti = new ArrayList<Persona>();

    private Persona presidente;

    private DefaultListModel partecipantiModel, utentiModel;

    private Campionato campionato;

    final private Mysql db = new Mysql();

    /**
     * Costruttore dell'oggetto CreaCampionato.
     * @param utente utente loggato
     * @param loginForm login frame
     */
    public CreaCampionato(Persona utente, final Login loginForm) {
        //titolo del frame
        super("JFantacalcio - Crea Campionato");

        presidente = utente;

        //richiama i metodi per settare le liste degli utenti,
        //gli spinner, e le info icon
        refresh();

        nomePresidente.setText(utente.getNickname());

        setContentPane(panel);
        pack();
        //centra il frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numeroPartecipanti = Integer.parseInt((String) numeroBox.getSelectedItem());

                //se è stato selezionato qualcuno e se non si è già raggiunto
                //il numero max di partecipanti
                if (!utentiList.isSelectionEmpty() && partecipantiModel.getSize() < numeroPartecipanti) {
                    //trovo l'indice dell'utente selezionato
                    int indice = utentiList.getSelectedIndex();
                    //trovo i valori dell'utente selezionato
                    Object utente = utentiList.getSelectedValue();

                    //aggiungo l'utente ai partecipanti
                    partecipantiModel.addElement(utente);
                    //rimuovo l'utente dai disponibili
                    utentiModel.remove(indice);
                }
            }
        });

        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //se è stato selezionato qualcuno
                if (!partecipantiList.isSelectionEmpty()) {
                    //trovo l'indice dell'utente selezionato
                    int indice = partecipantiList.getSelectedIndex();
                    //trovo i valori dell'utente selezionato
                    Object utente = partecipantiList.getSelectedValue();

                    //non si può rimuovere la squadra del presidente
                    if(!utente.equals(presidente.getNickname())) {
                        //aggiungo l'utente ai disponibili
                        utentiModel.addElement(utente);
                        //rimuovo l'utente dai partecipanti
                        partecipantiModel.remove(indice);
                    }

                }
            }
        });

        //controlla se il numero di giornata di inizio
        //è minore di quella finale
        inizioSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int inizio = (Integer) inizioSpinner.getValue();
                int fine = (Integer) fineSpinner.getValue();
                int numeroPartecipanti = Integer.parseInt((String) numeroBox.getSelectedItem());
                if (inizio > fine) {
                    inizioSpinner.setValue(fine - numeroPartecipanti + 1);
                }
                if(numeroPartecipanti != 0){
                    int fineConsigliata = 38 - ((38 - ((Integer) inizioSpinner.getValue()-1)) % (Integer.parseInt((String)numeroBox.getSelectedItem())-1));
                    fineSpinner.setValue(fineConsigliata);
                }
            }
        });

        //controlla se il numero di giornata di fine
        //è maggiore di quella iniziale
        fineSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int inizio = (Integer) inizioSpinner.getValue();
                int fine = (Integer) fineSpinner.getValue();
                int numeroPartecipanti = Integer.parseInt((String) numeroBox.getSelectedItem());
                if (inizio > fine) {
                    fineSpinner.setValue(inizio + numeroPartecipanti - 1);
                }
            }
        });

        //quando viene cambiato il numero di partecipanti controlla se
        //il numero degli utenti già inserito è maggiore.
        //se è maggiore  toglie gli ultimi inseriti
        numeroBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //controlla se è stato cambiato nella combobox
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //cast del numero di parteciapanti dalla combobox
                    int numeroPartecipanti = Integer.parseInt((String) numeroBox.getSelectedItem());
                    //controlla se i parteciapanti già inseriti sono maggiori
                    //del numero max appena cambiato
                    if (partecipantiModel.getSize() > numeroPartecipanti) {
                        //toglie gli ultimi inseriti dai partecipanti
                        //e li riaggiunge ai disponibili
                        for (int i = partecipantiModel.getSize(); i > numeroPartecipanti; i--) {
                            utentiModel.addElement(partecipantiModel.getElementAt(i - 1));
                            partecipantiModel.remove(i - 1);
                        }
                    }
                }
            }
        });

        creaCampionatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("".equals(nometxt.getText())) {
                    JOptionPane.showMessageDialog(getContentPane(), "Inserire il nome del campionato.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if (partecipantiModel.size() != Integer.parseInt((String) numeroBox.getSelectedItem())) {
                    JOptionPane.showMessageDialog(getContentPane(), "Inserisci tutti i partecipanti", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if (nometxt.getText().length()>19) {
                    JOptionPane.showMessageDialog(getContentPane(),"Nome campionato troppo lungo.","Errore",JOptionPane.ERROR_MESSAGE);
                } else {
                    campionato = creaCampionato();
                    if (db.creaCampionato(campionato)) {
                        Object[] options = {"OK"};
                        int succesDialog = JOptionPane.showOptionDialog(getContentPane(), "Campionato creato con successo!",
                                "Risposta",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (succesDialog == 0 || succesDialog == -1) {
                            loginForm.refresh();
                            loginForm.setVisible(true);
                            dispose();
                        }

                    } else {
                        JOptionPane.showMessageDialog(getContentPane(), "Ci sono stati degli errori nella creazione del campionato!", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginForm.setVisible(true);
                getFrame().dispose();
            }
        });

        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginForm.setVisible(true);
                getFrame().dispose();
            }
        });

    }

    /**
     * Richiama i metodi che settano la lista degli utenti disponibili,
     * gli spinner e le info icon.
     */
    private void refresh(){
        setJlist();
        setInfoIcon();
        setSpinner();
    }

    //crea le liste dai modelli
    //il modello partecipanti è vuoto
    //il modello utenti disponibili viene scaricato dal db

    /**
     * Setta la lista degli utenti disponibili.
     * Popola il model degli utenti disponibili prendendo i nickname dal database.
     * Aggiunge il presidente di lega(colui che sta creando il campionato) al model dei partecipanti.
     */
    private void setJlist() {
        //db
        final Mysql db = new Mysql();
        //scarica la lista degli utenti e li metto in un array di stringhe
        listaUtenti = db.selectUtenti();

        //inizializzo i modelli per le jlist
        utentiModel = new DefaultListModel();
        partecipantiModel = new DefaultListModel();


        //aggiungo al modello utenti gli elementi dell'array
        for (Persona utenteLista : listaUtenti) {
            if(!utenteLista.getNickname().equals(presidente.getNickname())){
                utentiModel.addElement(utenteLista.getNickname());
            }
        }

        //aggiungo ai partecipanti il presidente
        partecipantiModel.addElement(presidente.getNickname());


        //creo le jlist dal modello
        utentiList.setModel(utentiModel);
        partecipantiList.setModel(partecipantiModel);

        //permette una selezione alla volta anche premendo CTRL
        utentiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partecipantiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Setta gli spinner. Vengono settati in base ai valori ammissibili per ogni regola.
     */
    private void setSpinner() {
        //costruttore spinnermodel-->(valore da visualizzare, min, max, incremento)
        SpinnerNumberModel inizioModel = new SpinnerNumberModel(1, 1, 37, 1);
        //creo il jspinner dal modello
        inizioSpinner.setModel(inizioModel);

        SpinnerNumberModel fineModel = new SpinnerNumberModel(38, 2, 38, 1);
        fineSpinner.setModel(fineModel);

        SpinnerNumberModel creditiModel = new SpinnerNumberModel(800, 250 , 2000, 10);
        creditiSpinner.setModel(creditiModel);

        SpinnerNumberModel limiteModel = new SpinnerNumberModel(30, 0, 360, 1);
        limiteSpinner.setModel(limiteModel);

        SpinnerNumberModel primafModel = new SpinnerNumberModel(66, 50, 80, 1);
        primafSpinner.setModel(primafModel);

        SpinnerNumberModel fasceModel = new SpinnerNumberModel(6, 1, 10, 1);
        fasceSpinner.setModel(fasceModel);

        SpinnerNumberModel bonuscModel = new SpinnerNumberModel(0, 0, 5, 1);
        bonuscSpinner.setModel(bonuscModel);
    }

    /**
     * Setta le icone in fondo ad ogni riga.
     * Sono dei label senza testo ma con un icon.
     * Inoltre viene aggiungo un tooltip text con le informazioni per la riga.
     */
    private void setInfoIcon() {
        //icona di info
        UIDefaults defaults = UIManager.getDefaults( );
        Icon icon = defaults.getIcon("OptionPane.informationIcon");
        //è un label senza testo, con solo l'icona di info
        nomeInfo.setIcon(icon);
        //quando il mouse è sopra l'icona spiega cosa bisogna fare
        nomeInfo.setToolTipText("Nome del campionato");


        numeroInfo.setIcon(icon);
        numeroInfo.setToolTipText("Numero dei partecipanti al campionato");

        astaInfo.setIcon(icon);
        astaInfo.setToolTipText("<html>Se si sceglie live l'asta verrà fatta tramite l'applicazione.<br> Se si sceglie offline bisogna inserire manualmente le singole rose</html>");

        inizioInfo.setIcon(icon);
        inizioInfo.setToolTipText("Giornata di inizio del fantacampionato rispetto alla giornata del campionato di Serie A");

        fineInfo.setIcon(icon);
        fineInfo.setToolTipText("<html>Giornata di fine consigliata per far giocare ad ogni giocatore<br> lo stesso numero di volte contro gli altri giocatori.<br> Se modifichi questo valore è possibile che questo non avvenga.</html>");

        creditiInfo.setIcon(icon);
        creditiInfo.setToolTipText("Crediti Iniziali");

        limiteInfo.setIcon(icon);
        limiteInfo.setToolTipText("<html>Numero di minuti prima della prima partita di <br>ogni giornata entro cui bisogna inviare la formazione.</html>");

        primafInfo.setIcon(icon);
        primafInfo.setToolTipText("Valore prima fascia gol. Consigliato: 66");

        fasceInfo.setIcon(icon);
        fasceInfo.setToolTipText("Valore larghezza fasce gol. Consigliato: 4 o 6");

        bcasaInfo.setIcon(icon);
        bcasaInfo.setToolTipText("Lasciare a zero se non si vuole il bonus casa.");

    }

    /**
     * Crea l'oggetto Campionato, che poi verrà passato al db,
     * dai valori inseriti nelle textfield, spinner e liste.
     * @return Campionato campionato appena creato
     * @see Campionato
     */
    private Campionato creaCampionato() {
        String nome = nometxt.getText();
        int numeroPartecipanti = Integer.parseInt((String) numeroBox.getSelectedItem());
        boolean asta = "Live".equals(astaBox.getSelectedItem());
        int inizio = (Integer) inizioSpinner.getValue();
        int fine = (Integer) fineSpinner.getValue();
        int crediti = (Integer) creditiSpinner.getValue();
        int orario = (Integer) limiteSpinner.getValue();
        int primaf = (Integer) primafSpinner.getValue();
        int fasce = (Integer) fasceSpinner.getValue();
        int bonusc = (Integer) bonuscSpinner.getValue();

        ArrayList<Squadra> listaSquadrePartecipanti = new ArrayList<Squadra>();

        for (int i = 0; i < partecipantiModel.getSize(); i++) {
            listaSquadrePartecipanti.add(new Squadra(new Persona((String) partecipantiModel.getElementAt(i))));
        }
        return new Campionato(nome, numeroPartecipanti, asta, inizio, fine, crediti, orario, primaf, fasce, bonusc, presidente,listaSquadrePartecipanti,true,1);
    }

    /**
     * Restituisce l'oggeto principale CreaCampionato
     * @return CreaCampionato creacampionato:this
     */
    private CreaCampionato getFrame() {
        return this;
    }

}
