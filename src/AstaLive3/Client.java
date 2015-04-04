package AstaLive3;

import classi.Persona;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Created by alessandro on 03/04/15.
 */
public class Client {

    private Socket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Persona utente;

    private String indirizzo;
    private int porta;
    private ClientGUI gui;

    public Client(String indirizzo, int porta, Persona utente, ClientGUI clientGUI){
        this.indirizzo = indirizzo;
        this.porta =porta;
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

            output.writeObject(this.utente.getNickname());

            if((Boolean)input.readObject()){
                Messaggio listaGiocatorimsg = (Messaggio)input.readObject();
                gui.setListaGiocatoriDisponibili(listaGiocatorimsg.getListaGiocatori());

                new AscoltaServer().start();
                gui.appendConsole("Connesso!");
            } else{
                gui.appendConsole("Connesione rifiutata.");
            }
        } catch (Exception e){
            gui.appendConsole("Eccezione nella creazione degli stream I/O>> "+e.getMessage());
            e.printStackTrace();
        }

    }

    class AscoltaServer extends Thread{
        private Messaggio messaggio;

        public void run(){
            while(true){
                gui.appendConsole("Aspetto un messaggio dal server..");
                try {
                    messaggio = (Messaggio) input.readObject();
                }catch (Exception e){
                    gui.appendConsole("Eccezione nella lettura di un messaggio dal server>> "+e.getMessage());
                    gui.appendConsole("Il server ha chiuso la connessione.");
                    e.printStackTrace();
                    break;
                }

                if(messaggio.getTipo()==Messaggio.INIZIO_ASTA){
                    gui.appendConsole("++++INIZIO ASTA TRA POCO++++");
                    gui.setComboBox(messaggio.getListaPartecipanti());
                } else if(messaggio.getTipo()==Messaggio.OFFERTA){
                    gui.setGiocatoreAttuale(messaggio.getGiocatore(), messaggio.getOfferta(),messaggio.getMessaggio());
                    gui.setOffertaEnabled();
                } else if(messaggio.getTipo()==Messaggio.TEMPO){
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
                                gui.appendConsole("Eccezione nell'invio del rifiuto>> "+e.getMessage());
                                e.printStackTrace();
                            }
                        }

                        gui.setOffertaNotEnabled();
                    }
                } else if(messaggio.getTipo()==Messaggio.FINE_OFFERTA){
                    gui.appendConsole(messaggio.getGiocatore().getCognome()+" aggiudicato da " + messaggio.getMessaggio() +" a "+messaggio.getOfferta());
                    gui.aggiungiGiocatore(messaggio.getGiocatore(),messaggio.getOfferta(),messaggio.getMessaggio());
                }

            }
        }
    }

}
