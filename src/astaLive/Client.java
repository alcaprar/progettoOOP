package astaLive;

import entità.Persona;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Classe che gestisce la comunicazione con il server.
 *
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Client {

    private Socket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Persona utente;
    //private Campionato campionato;
    private ClientGUI gui;

    private AscoltaServer ascoltaServer;

    /**
     * Costruttore del client.
     * @param indirizzo indirizzo IP del server
     * @param porta porta a cui connettersi
     * @param utente utente che partecipa all'asta
     * @param clientGUI interfaccia grafica del client
     */
    public Client(String indirizzo, int porta, Persona utente, ClientGUI clientGUI){
        this.gui = clientGUI;
        this.utente = utente;

        //provo a connettermi al server
        try {
            server = new Socket(indirizzo, porta);
        } catch (Exception e){
            gui.appendConsole("Eccezione durante la connessione al server>> "+e.getMessage());
            e.printStackTrace();
        }

        gui.appendConsole("Connessione accettata da: " + server.getInetAddress() + ":" + porta);

        //creo gli stream I/O
        gui.appendConsole("Creo gli stream I/O e invio l'username.");
        try{
            input = new ObjectInputStream(server.getInputStream());
            output = new ObjectOutputStream(server.getOutputStream());

            output.writeObject(this.utente);

            if((Boolean)input.readObject()){
                Messaggio listaGiocatorimsg = (Messaggio)input.readObject();
                gui.setListaGiocatoriDisponibili(listaGiocatorimsg.getListaGiocatori());

                ascoltaServer = new AscoltaServer();
                ascoltaServer.start();
                gui.appendConsole("Connesso!");
                JOptionPane.showMessageDialog(null, "Connessione effettuata con successo!", "Connesso", JOptionPane.INFORMATION_MESSAGE);
                gui.setConnettiNotEnabled();
            } else{
                gui.appendConsole("Connesione rifiutata.");
                JOptionPane.showMessageDialog(null, "Connessione rifiutata!\nLeggere i dettagli nella console.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e){
            gui.appendConsole("Eccezione nella creazione degli stream I/O>> "+e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Chiude il client. Ferma il thread che ascolta il server e libera tutte le risorse.
     */
    public void close(){
        ascoltaServer.stop();
        try{
            server.close();
            input.close();
            output.close();
        } catch (Exception e){
            //non ci posso far niente
        }
    }

    /**
     * Classe per ricevere i messaggi dal server.
     * Estende Thread.
     */
    class AscoltaServer extends Thread{
        private Messaggio messaggio;


        /**
         * Ciclo infinito che aspetta i messaggi dal server.
         * Se ci sono dei problemi nella lettura del messaggio (eccezione) vuol dire che il server ha chiuso
         * la connessione.
         */
        public void run(){
            while(true){
                gui.appendConsole("Aspetto un messaggio dal server..");
                try {
                    messaggio = (Messaggio) input.readObject();
                }catch (Exception e){
                    gui.appendConsole("Eccezione nella lettura di un messaggio dal server>> "+e.getMessage());
                    gui.appendConsole("Il server ha chiuso la connessione.");
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Il server ha chiuso la connessione.\nL'asta è da rifare da zero.\nErrore: "+e.getMessage(), "Errore connessione", JOptionPane.ERROR_MESSAGE);
                    gui.close();
                    break;
                }

                //se il messaggio è di inizio asta, comunico l'inizio dell'asta
                //e setto il combobox e le tabelle
                if(messaggio.getTipo()==Messaggio.INIZIO_ASTA){
                    gui.appendConsole("++++INIZIO ASTA TRA POCO++++");
                    gui.setComboBoxTable(messaggio.getListaPartecipanti());
                    JOptionPane.showMessageDialog(null, "Tutti i partecipanti si sono connessi.\nL'asta inizierà tra pochi secondi.", "Inizio Asta", JOptionPane.INFORMATION_MESSAGE);

                }
                //se il messaggio è di offerta, setto il panel per il rilancio
                else if(messaggio.getTipo()==Messaggio.OFFERTA){
                    gui.setGiocatoreAttuale(messaggio.getGiocatore(), messaggio.getOfferta(),messaggio.getUtente());
                    gui.setOffertaEnabled();
                }
                //se il messaggio è di tempo, cambio il tempo rimanente
                //se il tempo è zero invio l'offerta di risposta.
                //(offerta=0 se è stato rifiutato)
                else if(messaggio.getTipo()==Messaggio.TEMPO){
                    gui.setCountdown(messaggio.getSecondi());
                    gui.appendConsole("Secondi: "+messaggio.getSecondi());
                    if(messaggio.getSecondi()==0){
                        if(gui.haOfferto()){
                            Messaggio risposta = new Messaggio(Messaggio.RISPOSTA_OFFERTA);
                            risposta.setOfferta(gui.getValoreOfferta());
                            gui.appendConsole("Risposta offerta: "+gui.getValoreOfferta());
                            try{
                                output.writeObject(risposta);
                            } catch (Exception e){
                                gui.appendConsole("Eccezione nell'invio dell'offerta>> "+e.getMessage());
                                e.printStackTrace();
                            }
                        } else{
                            Messaggio risposta = new Messaggio(Messaggio.RISPOSTA_OFFERTA);
                            risposta.setOfferta(0);
                            gui.appendConsole("Rifiutato il giocatore.");

                            try{
                                output.writeObject(risposta);
                            } catch (Exception e){
                                gui.appendConsole("Eccezione nell'invio del rifiuto>> " + e.getMessage());
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Il server ha chiuso la connessione.\nL'asta è da rifare da zero.\nErrore: " + e.getMessage(), "Errore connessione", JOptionPane.ERROR_MESSAGE);
                                close();
                                gui.close();
                                break;
                            }
                        }
                        gui.setOffertaNotEnabled();
                    }
                }
                //se il messaggio è di fine offerta, vuol dire che il server comunica
                //da chi è stato acquistato il giocatore
                else if(messaggio.getTipo()==Messaggio.FINE_OFFERTA){
                    gui.appendConsole(messaggio.getGiocatore().getCognome()+" aggiudicato da " + messaggio.getUtente().getNickname() +" a "+messaggio.getOfferta());
                    gui.aggiungiGiocatore(messaggio.getGiocatore(),messaggio.getOfferta(),messaggio.getUtente());
                }
                //se il messaggio è di fine asta, comunico la fine e chiudo
                else if(messaggio.getTipo()==Messaggio.FINE_ASTA){
                    JOptionPane.showMessageDialog(null,"Asta completata.\nRiapri l'gestore per vedere le modifiche.","Asta completata",JOptionPane.INFORMATION_MESSAGE);
                    close();
                    gui.astaFinita();
                }
            }
        }
    }

}
