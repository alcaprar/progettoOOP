package astaLive;

import entità.*;
import interfacce.gestore.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Classe per l'interfaccia grafica del server.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JTextArea ipArea;
    private JSpinner spinnerPorta;
    private JButton startServerButton;
    private JTextArea consoleArea;
    private JList listaConnessi;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private Server server;

    private DefaultListModel listModel;

    private Applicazione applicazione;
    private Campionato campionato;

    /**
     * Costruttore dell'interfaccia grafica
     * @param app gestore principale
     * @param camp campionato per cui si fa l'asta
     */
    public ServerGUI(Applicazione app, Campionato camp){
        super("Server");

        this.applicazione = app;
        this.campionato = camp;

        setListaConnessi();

        setSpinnerPorta();

        getIPaddress();


        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server = new Server((Integer)spinnerPorta.getValue(), getFrame(), campionato);
                startServerButton.setEnabled(false);
            }
        });


        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int risultato = JOptionPane.showConfirmDialog(null,"Sei sicuro di voler chiudere?","Exit",JOptionPane.OK_CANCEL_OPTION);
                if(risultato==JOptionPane.OK_OPTION){
                    server.stopServer();
                    close();
                }
            }
        });

        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Scrive nella console.
     * @param str stringa da stampare
     */
    public void appendConsole(String str){
        consoleArea.append(sdf.format(new Date())+">> "+str+"\n");
        consoleArea.setCaretPosition(consoleArea.getText().length());
    }

    public void addConnesso(Persona utente){
        listModel.addElement(utente.getNickname());
    }

    /**
     * Trova gli indirizzi IP sulle varie interfacce.
     * Stampa solo quelli che iniziano per 192.168, cioè quelli
     * usati dalle interfacce si rete WIFI e lan.
     */
    private void getIPaddress(){
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(i.getHostAddress().contains("192.168")) {
                        ipArea.append(i.getHostAddress() + "\n");
                        System.out.println(i.getHostAddress());
                    }
                }
            }
        } catch (SocketException se){
            appendConsole("Eccezione socket su getIPaddress" + se.getMessage());
            se.printStackTrace();
        }
    }

    /**
     * Setta lo spinner per la porta.
     * Utilizza un editor modificato per non far vedere il punto delle migliaia.
     */
    private void setSpinnerPorta(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1500,1000,2000,1);
        spinnerPorta.setModel(spinnerModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerPorta, "#");
        spinnerPorta.setEditor(editor);
    }

    /**
     * Setta il model per la lista degli utenti connessi.
     */
    private void setListaConnessi(){
        listModel = new DefaultListModel();
        listaConnessi.setModel(listModel);
    }

    /**
     * Ritorna l'oggetto principale
     * @return this
     */
    private ServerGUI getFrame(){
        return this;
    }

    /**
     * Chiude la finestra e rende visibile l'gestore principale.
     */
    public void close(){
        getFrame().dispose();
        applicazione.setVisible(true);
    }
}
