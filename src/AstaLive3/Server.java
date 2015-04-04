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

    public void asta() {
        astaPortieri();
        astaDifensori();
        astaCentrocampisti();
        astaAttaccanti();
    }

    public void astaPortieri(){
        //finche tutte le squadre non hanno tre portieri gira
        while (!finitiPortieri()) {
            //scorre la lista dei giocatori finche non è finita e tutti non hanno completato
            for(int i =0; i<listaGiocatori.size() && !finitiPortieri();i++){
                Giocatore portiere = listaGiocatori.get(i);

                //vedo se è un portiere
                if (portiere.getRuolo() == 'P') {
                    gui.appendConsole("Giocatore: " + portiere.getCognome());

                    //setto tutti a true per questo giocatore
                    offertaClientTrue();

                    //offertaAttuale
                    int offertaAttuale = portiere.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

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
                                for (int k = secondiTimer; k >= 0; k--) {
                                    Messaggio timer = new Messaggio(Messaggio.TEMPO);
                                    timer.setSecondi(k);
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
                    if (offertaAttuale >= portiere.getPrezzoBase()) {
                        aggiudicaGiocatore(portiere, offertaAttuale);
                    }
                }
            }
        }
        gui.appendConsole("Finiti i portieri.");
    }

    public void astaDifensori(){
        //finche tutte le squadre non hanno tre portieri gira
        while (!finitiDifensori()) {
            //scorre la lista dei giocatori finche non è finita e tutti non hanno completato
            for(int i =0; i<listaGiocatori.size() && !finitiDifensori();i++){
                Giocatore difensore = listaGiocatori.get(i);

                //vedo se è un portiere
                if (difensore.getRuolo() == 'D') {
                    gui.appendConsole("Giocatore: " + difensore.getCognome());

                    //setto tutti a true per questo giocatore
                    offertaClientTrue();

                    //offertaAttuale
                    int offertaAttuale = difensore.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta) {
                                gui.appendConsole("Client: " + client.username);

                                //invio il giocatore in palio
                                Messaggio offerta = new Messaggio(Messaggio.OFFERTA);
                                offerta.setGiocatore(difensore);
                                offerta.setOfferta(offertaAttuale);
                                offerta.setSecondi(secondiTimer);
                                client.writeMsg(offerta);

                                //countdown per l'offerta
                                for (int k = secondiTimer; k >= 0; k--) {
                                    Messaggio timer = new Messaggio(Messaggio.TEMPO);
                                    timer.setSecondi(k);
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
                                    gui.appendConsole(client.username + " ha rifiutato " + difensore.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.username + " ha offerto " + risposta.getOfferta() + " per " + difensore.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                }
                            }
                        }
                    }
                    if (offertaAttuale >= difensore.getPrezzoBase()) {
                        aggiudicaGiocatore(difensore, offertaAttuale);
                    }
                }
            }
        }
        gui.appendConsole("Finiti i difensori.");
    }

    public void astaCentrocampisti(){
        //finche tutte le squadre non hanno tre portieri gira
        while (!finitiCentrocampisti()) {
            //scorre la lista dei giocatori finche non è finita e tutti non hanno completato
            for(int i =0; i<listaGiocatori.size() && !finitiCentrocampisti();i++){
                Giocatore centrocampista = listaGiocatori.get(i);

                //vedo se è un portiere
                if (centrocampista.getRuolo() == 'P') {
                    gui.appendConsole("Giocatore: " + centrocampista.getCognome());

                    //setto tutti a true per questo giocatore
                    offertaClientTrue();

                    //offertaAttuale
                    int offertaAttuale = centrocampista.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta) {
                                gui.appendConsole("Client: " + client.username);

                                //invio il giocatore in palio
                                Messaggio offerta = new Messaggio(Messaggio.OFFERTA);
                                offerta.setGiocatore(centrocampista);
                                offerta.setOfferta(offertaAttuale);
                                offerta.setSecondi(secondiTimer);
                                client.writeMsg(offerta);

                                //countdown per l'offerta
                                for (int k = secondiTimer; k >= 0; k--) {
                                    Messaggio timer = new Messaggio(Messaggio.TEMPO);
                                    timer.setSecondi(k);
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
                                    gui.appendConsole(client.username + " ha rifiutato " + centrocampista.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.username + " ha offerto " + risposta.getOfferta() + " per " + centrocampista.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                }
                            }
                        }
                    }
                    if (offertaAttuale >= centrocampista.getPrezzoBase()) {
                        aggiudicaGiocatore(centrocampista, offertaAttuale);
                    }
                }
            }
        }
        gui.appendConsole("Finiti i centrocampisti.");
    }

    public void astaAttaccanti(){
        //finche tutte le squadre non hanno tre portieri gira
        while (!finitiAttaccanti()) {
            //scorre la lista dei giocatori finche non è finita e tutti non hanno completato
            for(int i =0; i<listaGiocatori.size() && !finitiAttaccanti();i++){
                Giocatore attaccante = listaGiocatori.get(i);

                //vedo se è un portiere
                if (attaccante.getRuolo() == 'P') {
                    gui.appendConsole("Giocatore: " + attaccante.getCognome());

                    //setto tutti a true per questo giocatore
                    offertaClientTrue();

                    //offertaAttuale
                    int offertaAttuale = attaccante.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta) {
                                gui.appendConsole("Client: " + client.username);

                                //invio il giocatore in palio
                                Messaggio offerta = new Messaggio(Messaggio.OFFERTA);
                                offerta.setGiocatore(attaccante);
                                offerta.setOfferta(offertaAttuale);
                                offerta.setSecondi(secondiTimer);
                                client.writeMsg(offerta);

                                //countdown per l'offerta
                                for (int k = secondiTimer; k >= 0; k--) {
                                    Messaggio timer = new Messaggio(Messaggio.TEMPO);
                                    timer.setSecondi(k);
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
                                    gui.appendConsole(client.username + " ha rifiutato " + attaccante.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.username + " ha offerto " + risposta.getOfferta() + " per " + attaccante.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                }
                            }
                        }
                    }
                    if (offertaAttuale >= attaccante.getPrezzoBase()) {
                        aggiudicaGiocatore(attaccante, offertaAttuale);
                    }
                }
            }
        }
        gui.appendConsole("Finiti gli attaccanti.");
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
        //cerco l'unico client che era rimasto in gioco
        for(ClientConnesso client :listaClient){
            if(client.offerta){
                clientAggiudicato = client;
            }
        }

        giocatore.setPrezzoAcquisto(offerta);
        clientAggiudicato.listaGiocatoriSquadre.add(giocatore);

        gui.appendConsole(giocatore.getCognome()+" aggiudicato da " + clientAggiudicato.username + " a "+giocatore.getPrezzoAcquisto());

        //comunico a tutti che è stato aggiudicato
        Messaggio giocatoreAggiudicato = new Messaggio(Messaggio.FINE_OFFERTA);
        giocatoreAggiudicato.setGiocatore(giocatore);
        giocatoreAggiudicato.setOfferta(offerta);
        giocatoreAggiudicato.setMessaggio(clientAggiudicato.username);

        //invio il messaggio a tutti
        broadcast(giocatoreAggiudicato);

        //rimuovo il giocatore dalla lista dei disponibili
        listaGiocatori.remove(giocatore);
    }

    private boolean finitiPortieri(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadre.size();i++){
                if(client.listaGiocatoriSquadre.get(i).getRuolo()=='P') numPor++;
            }
            if(numPor<3) flag=false;
        }
        return flag;
    }

    private boolean finitiDifensori(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadre.size();i++){
                if(client.listaGiocatoriSquadre.get(i).getRuolo()=='D') numPor++;
            }
            if(numPor<8) flag=false;
        }
        return flag;
    }

    private boolean finitiCentrocampisti(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadre.size();i++){
                if(client.listaGiocatoriSquadre.get(i).getRuolo()=='C') numPor++;
            }
            if(numPor<8) flag=false;
        }
        return flag;
    }

    private boolean finitiAttaccanti(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadre.size();i++){
                if(client.listaGiocatoriSquadre.get(i).getRuolo()=='A') numPor++;
            }
            if(numPor<6) flag=false;
        }
        return flag;
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
