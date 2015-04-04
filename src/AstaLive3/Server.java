package AstaLive3;

import classi.Giocatore;
import db.Mysql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by alessandro on 03/04/15.
 */
public class Server extends Thread {

    private ServerSocket server;
    private ServerGUI gui;
    private int porta;
    private int secondiTimer = 10;

    private boolean accettaConnessioni;

    private ArrayList<ClientConnesso> listaClient;

    private ArrayList<Giocatore> listaGiocatori;

    final Mysql db = new Mysql();

    public Server(int porta, ServerGUI serverGUI){
        this.porta = porta;
        this.gui = serverGUI;

        listaClient = new ArrayList<ClientConnesso>();

        accettaConnessioni=true;

        listaGiocatori = db.selectGiocatoriAdmin();

        try {
            //creo il server
            server = new ServerSocket(porta);
            //stampo che il server è stato creato ed è in attesa
            gui.appendConsole("Il server è in attesa sulla porta: "+this.porta);
            //metodo per far partire il thread, che richiama il metodo run
            this.start();
        } catch (IOException ioe){
            gui.appendConsole("Eccezione nella creazione del server>>"+ioe.getMessage());
            ioe.printStackTrace();
        }

    }


    public void run(){

        while(accettaConnessioni){
            try {
                gui.appendConsole("Server in attesa di connessioni.. ");
                Socket client = server.accept();
                if(!accettaConnessioni){
                    break;
                }
                gui.appendConsole("Connessione accettata da: "+client.getInetAddress());

                ClientConnesso clientConn = new ClientConnesso(client);
                listaClient.add(clientConn);
                clientConn.start();
            } catch (IOException ioe){
                gui.appendConsole("Eccezione nell'attesa dei client>>"+ioe.getMessage());
                ioe.printStackTrace();
            }
        }
        gui.appendConsole("Stop connessioni.");

        //TODO asta
        //comunico l'inizio dell'asta
        Messaggio mess = new Messaggio(Messaggio.INIZIO_ASTA);
        //invio la lista dei partecipanti
        ArrayList<String> listaPartecipanti = new ArrayList<String>();
        for(ClientConnesso client : listaClient){
            listaPartecipanti.add(client.username);
        }
        mess.setListaPartecipanti(listaPartecipanti);
        broadcast(mess);
        try{
            sleep(5000);
        } catch (Exception e){
            gui.appendConsole("Eccezione nello sleep del thread>> "+e.getMessage());
            e.printStackTrace();
        }

    }

    public void stopConnessioni(){
        this.accettaConnessioni=false;
        //server per fare l'accept e andare sull'if per fare il break
        try {
            new Socket("localhost", porta);
        }
        catch(Exception e) {
            // nothing I can really do
        }
    }

    private synchronized void broadcast(Messaggio msg){
        gui.appendConsole("Broadcast: " + msg.getTipo());

        for(ClientConnesso client: listaClient){
            client.writeMsg(msg);
        }
    }

    public void asta(){
        for(Giocatore portiere: listaGiocatori){
            if(portiere.getRuolo()=='P'){
                gui.appendConsole("Giocatore: "+portiere.getCognome());

                //setto tutti a true per questo giocatore
                offertaClientTrue();

                //offertaAttuale
                int offertaAttuale = portiere.getPrezzoBase();

                while (continuaAsta()) {
                    //scorro un client alla volta
                    for (ClientConnesso client : listaClient) {
                        //se il client è ancora in asta
                        if (client.offerta) {
                            gui.appendConsole("Client: " + client.username);

                            //invio il giocatore in palio
                            Messaggio offerta = new Messaggio(Messaggio.OFFERTA);
                            offerta.setGiocatore(portiere);
                            offerta.setOfferta(offertaAttuale);
                            offerta.setSecondi(secondiTimer);
                            client.writeMsg(offerta);

                            //countdown per l'offerta
                            for (int i = secondiTimer; i >= 0; i--) {
                                Messaggio timer = new Messaggio(Messaggio.TEMPO);
                                timer.setSecondi(i);
                                client.writeMsg(timer);

                                //aspetto un secondo
                                try {
                                    sleep(1000);
                                } catch (Exception e) {
                                    gui.appendConsole("Eccezione nello sleep del thread>> " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }

                            //prendo la risposta
                            Messaggio risposta = client.readObject();
                            if (risposta.getOfferta() == 0) {
                                gui.appendConsole(client.username + " ha rifiutato " + portiere.getCognome());
                                client.offerta = false;
                            } else {
                                gui.appendConsole(client.username + " ha offerto " + risposta.getOfferta() + " per " + portiere.getCognome());
                                offertaAttuale = risposta.getOfferta();
                            }

                        }
                    }
                }

                aggiudicaGiocatore(portiere,offertaAttuale);
            }
        }

    }

    private boolean continuaAsta(){
        int counter = 0;
        for(ClientConnesso client : listaClient){
            if(client.offerta){
                counter++;
            }
        }

        if(counter==0 || counter==1) return false;
        else return true;
    }

    private void offertaClientTrue(){
        for(ClientConnesso client : listaClient){
            client.offerta = true;
        }
    }

    private void aggiudicaGiocatore(Giocatore giocatore, int offerta){
        ClientConnesso clientAggiudicato = null;
        for(ClientConnesso client :listaClient){
            if(client.offerta){
                clientAggiudicato = client;
            }
        }

        if(clientAggiudicato!=null){
            giocatore.setPrezzoAcquisto(offerta);
            clientAggiudicato.listaGiocatoriSquadre.add(giocatore);

            //comunico a tutti che è stato aggiudicato
            Messaggio giocatoreAggiudicato = new Messaggio(Messaggio.FINE_OFFERTA);
            giocatoreAggiudicato.setGiocatore(giocatore);
            giocatoreAggiudicato.setOfferta(offerta);
            giocatoreAggiudicato.setMessaggio(clientAggiudicato.username);

            //invio il messaggio a tutti
            broadcast(giocatoreAggiudicato);
        }
    }

    class ClientConnesso extends Thread{

        private Socket client;
        ObjectInputStream input;
        ObjectOutputStream output;

        private boolean offerta;

        private String username;

        private ArrayList<Giocatore> listaGiocatoriSquadre;

        public ClientConnesso(Socket clientSock){
            this.client = clientSock;

            //creo gli stream di I/O
            gui.appendConsole("Thread sta creando gli I/O stream.");
            try{
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());

                gui.appendConsole("Creati gli stream, attendo l'username.");
                this.username = (String) input.readObject();
                gui.appendConsole("Connesso adesso: "+this.username);
                gui.addConnesso(this.username);

                //inizializzo la lista dei giocatori per questo client
                listaGiocatoriSquadre = new ArrayList<Giocatore>();
            } catch (Exception e){

                //TODO bisogna chiudere la connessione e fare return
                //client.close();
                //return;
                //TODO

                gui.appendConsole("Eccezione nella creazione degli strem I/O>> "+e.getMessage());
                e.printStackTrace();
            }

        }

        public void run(){
            while(true){
                //TODO attende i messagi dai client
                //??mi sa non serve tanto è il server che gestisce tutto??
            }
        }

        private boolean writeMsg(Messaggio msg){
            if(!client.isConnected()) {
                //close();
                return false;
            }
            // write the message to the stream
            try {
                output.writeObject(msg);
                gui.appendConsole("Messaggio inviato a: "+ this.username);
            }
            // if an error occurs, do not abort just inform the user
            catch(IOException ioe) {
                gui.appendConsole("Errore nell'invio del messaggio a: " + username);
                gui.appendConsole(ioe.getMessage());
                ioe.printStackTrace();
            }
            return true;
        }

        private Messaggio readObject(){
            Messaggio mess = null;
            try {
                mess = (Messaggio)input.readObject();
            }catch (Exception e){
                gui.appendConsole("Eccezione nella lettura dell'oggetto: " + e.getMessage());
                e.printStackTrace();
            }
            return mess;
        }
    }
}
