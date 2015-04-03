package AstaLive3;

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
    private String username;

    private String indirizzo;
    private int porta;
    private ClientGUI gui;

    public Client(String indirizzo, int porta,String username, ClientGUI clientGUI){
        this.indirizzo = indirizzo;
        this.porta =porta;
        this.gui = clientGUI;
        this.username = username;

        //provo a connettermi al server
        try {
            server = new Socket(indirizzo, porta);
        } catch (Exception e){
            gui.appendConsole("Eccezione durante la connessione al server>> "+e.getMessage());
            e.printStackTrace();
        }

        gui.appendConsole("Connessione accettata da: "+server.getInetAddress()+":"+porta);

        //creo gli stream I/O
        gui.appendConsole("Creo gli stream I/O e invio l'username.");
        try{
            input = new ObjectInputStream(server.getInputStream());
            output = new ObjectOutputStream(server.getOutputStream());

            output.writeObject(this.username);
        } catch (IOException ioe){
            gui.appendConsole("Eccezione nella creazione degli stream I/O>> "+ioe.getMessage());
            ioe.printStackTrace();
        }
        new AscoltaServer().start();
        gui.appendConsole("Connesso!");
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
                } else if(messaggio.getTipo()==Messaggio.OFFERTA){
                    gui.setGiocatoreAttuale(messaggio.getGiocatore(), messaggio.getOfferta());
                    gui.setOffertaEnabled();

                    final DateTime stop = new DateTime().plusSeconds(messaggio.getSecondi());

                    final Timer t = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            DateTime now = new DateTime();
                            if (now.isAfter(stop)) {
                                gui.appendConsole(String.valueOf(Seconds.secondsBetween(now,stop)));
                            } else{
                                gui.setOffertaNotEnabled();
                                Messaggio offerta = new Messaggio(Messaggio.RISPOSTA_OFFERTA);
                                if(gui.getOfferto()){
                                    int valoreOfferta = gui.getValoreOfferta();
                                    offerta.setOfferta(valoreOfferta);
                                    gui.appendConsole("Provo a inviare l'offerta: "+valoreOfferta);
                                } else{
                                    gui.appendConsole("Provo a inviare il rifiuto dell'offerta.");
                                    offerta.setOfferta(0);
                                }
                                try{
                                    output.writeObject(offerta);
                                } catch (IOException ioe){
                                    gui.appendConsole("Eccezione nell'invio dell'offerta>> " + ioe.getMessage());
                                    ioe.printStackTrace();
                                }

                                return;
                            }
                        }
                    });
                    t.start();
                    try{
                        this.sleep(messaggio.getSecondi()*1000);
                    } catch (Exception e){
                        gui.appendConsole("Eccezione nello sleep del thread>> "+e.getMessage());
                        e.printStackTrace();
                    }


                }

            }
        }
    }

}
