package AstaLive;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Christian on 26/03/2015.
 * Il server è eseguito esclusivamente in background, tutti gli utenti utilizzano il client
 */
public class Server {

        // Ogni connessiona ha un ID univoco
        private int ID;
        // Arraylist che contiene la lista degli utenti collegati
        private ArrayList<ClientThread> al;
        // Per visualizzare l'ora nei messaggi
        private SimpleDateFormat sdf;
        // La porta su cui il server rimane in ascolto
        private int port;
        // Variabile booleana utilizzata per stabilire se il server deve essere fermato oppure no
        private boolean continua;

        private AstaLiveClient asta;


        /*
         *  Costruttore che avvia il server in background
         */

        public Server(int port, AstaLiveClient asta) {
            this.asta = asta;
            // inizializza la porta
            this.port = port;
            // visualizza l'ora in formato hh:mm:ss
            sdf = new SimpleDateFormat("HH:mm:ss");
            // inizializza l'arraylist di utenti
            al = new ArrayList<ClientThread>();
        }

        public void start() {
            continua = true;
		/* crea un socket per il server e lo imposta per ascoltare sulla porta designata */
            try
            {
                ServerSocket serverSocket = new ServerSocket(port);

                // loop infinito in cui il server aspetta chiamate dai client, si esce solo se è necessario terminare il server
                while(continua)
                {
                    // Visualizza un messaggio in console per rendere noto che sta ascoltando
                    display("Server attivo all'indirizzo 192.168.1.50, in ascolto sulla porta: " + port + ".");

                    Socket socket = serverSocket.accept();  	// accept connection
                    // se la connessione ha richiesto lo stop del server
                    if(!continua)
                        break;
                    ClientThread t = new ClientThread(socket);  // crea un nuovo thread per il client
                    al.add(t);									// lo aggiunge all'arraylist
                    t.run();                                  //avvia il tentativo di connessione da parte del client
                }
                // se è stato richiesto lo stop del server esegue:
                try {
                    serverSocket.close();
                    //per ogni utente che è presente in lista cerca di chiudere la connessione
                    for(ClientThread c : al) {
                        ClientThread tc = c;
                        try {
                            tc.sInput.close();
                            tc.sOutput.close();
                            tc.socket.close();
                        }
                        catch(IOException ioE) {
                            // poco da fare
                        }
                    }
                }
                catch(Exception e) {
                    display("Eccezione nel tentativo di chiusura del server: " + e);
                }
            }

            catch (IOException e) {
                String msg = sdf.format(new Date()) + " Eccezione nella creazione del socketServer: " + e + "\n";
                display(msg);
            }
        }
        /*
         * Funziona per terminare il server dalla GUI
         */
        protected void stop() {
            continua = false;
            // connessione dal server a se stesso come client per terminare
            // Socket socket = serverSocket.accept();
            try {
                new Socket("localhost", port);
            }
            catch(Exception e) {
            }
        }
        /*
         * Visualizza un evento sulla console
         */
        private void display(String msg) {
            String time = sdf.format(new Date()) + " " + msg;
            asta.eventoServer(time + "\n");
        }

    /** Classe per Thread dei client. Ad ogni client che si connette viene associato un nuovo Thread. */
    class ClientThread extends Thread {
        // il Socket dove ascoltare o inviare
        private Socket socket;
        private ObjectInputStream sInput;
        private ObjectOutputStream sOutput;
        // id univoco della connessione
        int id;
        String username;
        // Unico tipo di messaggio che è possibile inviare
        ChatMessage cm;
        // Momento della connessione
        String date;

        ClientThread(Socket socket) {
            //incrementa ID e lo assegna al client
            id = ++ID;
            this.socket = socket;
			/* Creazione stream dei dati */
            System.out.println("Thread sta cercando di creare i data stream");
            try
            {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput  = new ObjectInputStream(socket.getInputStream());
                // Leggi lo username
                username = (String) sInput.readObject();
                display(username + " connesso.");
            }
            catch (IOException e) {
                display("Eccezione nella creazione degli input/output stream: " + e);
                return;
            }
            catch (ClassNotFoundException e) {
            }
            date = new Date().toString() + "\n";
        }

        // è sempre in esecuzione
        public void run() {
            // loop fino a quando non viene effettuato il logout
            boolean continua = true;
            while(continua) {
                try {
                    cm = (ChatMessage) sInput.readObject();
                }
                catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;
                }
                catch(ClassNotFoundException e2) {
                    break;
                }
                //TODO gestire il chat message letto da input
            }
            close();
        }

        // try to close everything
        private void close() {
            // try to close the connection
            try {
                if(sOutput != null) sOutput.close();
            }
            catch(Exception e) {}
            try {
                if(sInput != null) sInput.close();
            }
            catch(Exception e) {};
            try {
                if(socket != null) socket.close();
            }
            catch (Exception e) {}
        }

        /*
         *
         */
        private boolean inviaGiocatore(ChatMessage cm) {
            // if Client is still connected send the message to it
            if(!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {
                sOutput.writeObject(cm);
            }
            // if an error occurs, do not abort just inform the user
            catch(IOException e) {
                display("C'è stato un errore nell'invio del messaggio al client" + username + "poichè disconnesso.");
                display(e.toString());
            }
            return true;
        }
        /*
             * Visualizza un evento sulla console
             */
        private void display(String msg) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date()) + " " + msg;
            asta.eventoServer(time + "\n");
        }
    }

}
