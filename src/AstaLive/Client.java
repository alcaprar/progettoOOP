package AstaLive;

/**
 * Created by Christian on 27/03/2015.
 */

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Questa classe contiene gli elementi necessari al funzionamento dei client
 */
public class Client  {

    private AstaLiveClient asta;

    // per gestire l'I/O
    private ObjectInputStream sInput;		// leggi dal socket
    private ObjectOutputStream sOutput;		// scrivi mediante il socket
    private Socket socket;

    // Indirizzo server, porta e nome utente
    private String server, username;
    private int port;

    /*
     * Costruttore
     */
    Client(String server, int port, String username, AstaLiveClient asta) {
        this.asta = asta;
        this.server = server;
        this.port = port;
        this.username = username;
    }

    /*
     * Fa partire il dialogo
     */
    public boolean start() {
        // tentativo di connessione al server
        try {
            socket = new Socket(server, port);
        }
        // se fallisce ritorna
        catch(Exception ec) {
            display("Errore nella connessione al server:" + ec);
            return false;
        }

        String msg = "Connessione accettata su " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

		/* Creating both Data Stream */
        try
        {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO) {
            display("Eccezione nella creazione degli stream di I/O: " + eIO);
            return false;
        }

        // crea il thread che ascolta comunicazione dal server
        new ListenFromServer().start();
        // Invia al server lo username del client, unico messaggio con sola stringa che verràò inviato
        try
        {
            sOutput.writeObject(username);
        }
        catch (IOException eIO) {
            display("Eccezione durante il login : " + eIO);
            disconnect();
            return false;
        }
        // Se non ci sono stati problemi informa il chiamante che è andato tutto bene
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

    /*
     * Per inviare un messaggio al server
     */
    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        }
        catch(IOException e) {
            display("Eccezione nell'invio del messaggio al server: " + e);
        }
    }

    /*
     * Quando qualcosa non va tenta di chiudere gli stream e la connessione
     */
    private void disconnect() {
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {} // not much else I can do
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {} // not much else I can do
        try{
            if(socket != null) socket.close();
        }
        catch(Exception e) {} // not much else I can do
    }

    public void run(){
        // wait for messages from user
        Scanner scan = new Scanner(System.in);
        // loop forever for message from the user
        while(true) {

                        /*
                        *
                        * Loop di attività del client
                        *
                        *
                        * */

            break;
        }
        // done disconnect
        disconnect();
    }


    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {

        public void run() {
            while(true) {
                /*try {
                    //TODO gestione dei messaggi dal server
                }
                catch(IOException e) {
                    display("Server has close the connection: " + e);
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch(ClassNotFoundException e2) {
                }*/
            }
        }
    }
}

