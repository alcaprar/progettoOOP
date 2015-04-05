package AstaLive3;

import classi.Campionato;
import classi.Giocatore;
import classi.Persona;
import classi.Squadra;
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

    private boolean primoGiroAsta;

    private ArrayList<ClientConnesso> listaClient;

    private ArrayList<Giocatore> listaGiocatori;

    final Mysql db = new Mysql();

    private Campionato campionato;

    public Server(int porta, ServerGUI serverGUI, Campionato camp){
        this.porta = porta;
        this.gui = serverGUI;
        this.campionato = camp;

        //lista dei client connessi
        listaClient = new ArrayList<ClientConnesso>();

        //listaConsentiti.add(new Persona("ale"));
        //listaConsentiti.add(new Persona("cri"));

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
        //accetta connessioni finche non si raggiunge il numero dei partecipanti
        //TODO rimettere campionato.listapar.size
        //while(listaClient.size()<campionato.getListaSquadrePartecipanti().size()){
        while(listaClient.size()<2){
            try {
                gui.appendConsole("Server in attesa di connessioni.. ");
                //aspetto una connessione
                Socket client = server.accept();
                //se accetta connesioni è false chiudo il while
                if(!accettaConnessioni){
                    break;
                }
                gui.appendConsole("Connessione accettata da: "+client.getInetAddress() +". Controllo se può partecipare..");

                //creo il thread del client e controllo che possa partecipare
                ClientConnesso clientConn = new ClientConnesso(client);
                if(clientConn.controlloUsername()) {
                    listaClient.add(clientConn);
                }
            } catch (IOException ioe){
                gui.appendConsole("Eccezione nell'attesa dei client>>"+ioe.getMessage());
                ioe.printStackTrace();
            }
        }
        gui.appendConsole("Stop connessioni.");

        //comunico l'inizio dell'asta
        Messaggio mess = new Messaggio(Messaggio.INIZIO_ASTA);
        //invio la lista dei partecipanti
        ArrayList<Persona> listaPartecipanti = new ArrayList<Persona>();
        for(ClientConnesso client : listaClient){
            listaPartecipanti.add(client.utente);
        }
        mess.setListaPartecipanti(listaPartecipanti);
        broadcast(mess);

        try{
            sleep(5000);
        } catch (Exception e){
            gui.appendConsole("Eccezione nello sleep del thread>> "+e.getMessage());
            e.printStackTrace();
        }
        asta();
    }

    public void stopConnessioni(){
        this.accettaConnessioni=false;
        //server per fare l'accept e andare sull'if per fare il break
        try {
            new Socket("localhost", porta);
        }
        catch(Exception e) {
            gui.appendConsole("Eccezione nello stop delle connessioni>> "+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Invia il messaggio passato per parametro a tutti i client connessi.
     * @param msg messaggio da inviare
     */
    private void broadcast(Messaggio msg){
        gui.appendConsole("Broadcast");

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

    /**
     * Metodo che gestisce l'asta dei portieri.
     */
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
                    primoGiroAsta=true;

                    //offertaAttuale
                    int offertaAttuale = portiere.getPrezzoBase() - 1;
                    String utenteOfferta = null;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta && !client.finitiPortieri()) {
                                gui.appendConsole("Client: " + client.utente.getNickname());

                                //invio il giocatore in palio
                                Messaggio offerta = new Messaggio(Messaggio.OFFERTA);
                                offerta.setGiocatore(portiere);
                                offerta.setOfferta(offertaAttuale);
                                offerta.setMessaggio(utenteOfferta);
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
                                    gui.appendConsole(client.utente.getNickname() + " ha rifiutato " + portiere.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.utente.getNickname() + " ha offerto " + risposta.getOfferta() + " per " + portiere.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                    utenteOfferta = client.utente.getNickname();
                                }
                            }
                        }
                        primoGiroAsta=false;
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
                    primoGiroAsta=true;

                    //offertaAttuale
                    int offertaAttuale = difensore.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta && !client.finitiDifensori()) {
                                gui.appendConsole("Client: " + client.utente.getNickname());

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
                                    gui.appendConsole(client.utente.getNickname() + " ha rifiutato " + difensore.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.utente.getNickname() + " ha offerto " + risposta.getOfferta() + " per " + difensore.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                }
                            }
                        }
                        primoGiroAsta=false;
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
                    primoGiroAsta = true;

                    //offertaAttuale
                    int offertaAttuale = centrocampista.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta && !client.finitiCentrocampisti()) {
                                gui.appendConsole("Client: " + client.utente.getNickname());

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
                                    gui.appendConsole(client.utente.getNickname() + " ha rifiutato " + centrocampista.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.utente.getNickname() + " ha offerto " + risposta.getOfferta() + " per " + centrocampista.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                }
                            }
                        }
                        primoGiroAsta=false;
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
                    primoGiroAsta = true;

                    //offertaAttuale
                    int offertaAttuale = attaccante.getPrezzoBase() - 1;

                    //asta per il giocatore i-esimo
                    while (continuaAsta()) {
                        //scorro un client alla volta
                        for(int j=0;j<listaClient.size() && continuaAsta();j++){
                            ClientConnesso client = listaClient.get(j);

                            //se il client è ancora in asta
                            if (client.offerta && !client.finitiAttaccanti()) {
                                gui.appendConsole("Client: " + client.utente.getNickname());

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
                                    gui.appendConsole(client.utente.getNickname() + " ha rifiutato " + attaccante.getCognome());
                                    client.offerta = false;
                                } else {
                                    gui.appendConsole(client.utente.getNickname() + " ha offerto " + risposta.getOfferta() + " per " + attaccante.getCognome());
                                    offertaAttuale = risposta.getOfferta();
                                }
                            }
                        }
                        primoGiroAsta=false;
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
        if(primoGiroAsta) {
            return true;
        } else return !(counter==0 || counter==1);
    }

    private void offertaClientTrue(){
        for(ClientConnesso client : listaClient){
            if(!client.finitiPortieri()){
                client.offerta = true;
            }
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
        clientAggiudicato.listaGiocatoriSquadra.add(giocatore);

        gui.appendConsole(giocatore.getCognome()+" aggiudicato da " + clientAggiudicato.utente.getNickname() + " a "+giocatore.getPrezzoAcquisto());

        //comunico a tutti che è stato aggiudicato
        Messaggio giocatoreAggiudicato = new Messaggio(Messaggio.FINE_OFFERTA);
        giocatoreAggiudicato.setGiocatore(giocatore);
        giocatoreAggiudicato.setOfferta(offerta);
        giocatoreAggiudicato.setUtente(clientAggiudicato.utente);

        //invio il messaggio a tutti
        broadcast(giocatoreAggiudicato);

        //rimuovo il giocatore dalla lista dei disponibili
        listaGiocatori.remove(giocatore);
    }

    private boolean finitiPortieri(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadra.size();i++){
                if(client.listaGiocatoriSquadra.get(i).getRuolo()=='P') numPor++;
            }
            if(numPor<3) flag=false;
        }
        return flag;
    }

    private boolean finitiDifensori(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadra.size();i++){
                if(client.listaGiocatoriSquadra.get(i).getRuolo()=='D') numPor++;
            }
            if(numPor<8) flag=false;
        }
        return flag;
    }

    private boolean finitiCentrocampisti(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadra.size();i++){
                if(client.listaGiocatoriSquadra.get(i).getRuolo()=='C') numPor++;
            }
            if(numPor<8) flag=false;
        }
        return flag;
    }

    private boolean finitiAttaccanti(){
        boolean flag = true;
        for(ClientConnesso client : listaClient){
            int numPor =0;
            for(int i=0; i<client.listaGiocatoriSquadra.size();i++){
                if(client.listaGiocatoriSquadra.get(i).getRuolo()=='A') numPor++;
            }
            if(numPor<6) flag=false;
        }
        return flag;
    }

    private boolean utenteConsentito(Persona utente){
        boolean flag = false;
        for(Squadra squadra : campionato.getListaSquadrePartecipanti()){
            if(squadra.getProprietario().equals(utente)){
                flag=true;
                break;
            }
        }
        return flag;
    }

    class ClientConnesso{

        private Socket client;
        ObjectInputStream input;
        ObjectOutputStream output;

        private boolean offerta;

        private Persona utente;

        private ArrayList<Giocatore> listaGiocatoriSquadra;

        public ClientConnesso(Socket clientSock){
            this.client = clientSock;

            //creo gli stream di I/O
            gui.appendConsole("Thread sta creando gli I/O stream.");
            try{
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());

                gui.appendConsole("Creati gli stream, attendo l'username.");
            } catch (Exception e){

                //TODO bisogna chiudere la connessione e fare return
                //client.close();
                //return;
                //TODO

                gui.appendConsole("Eccezione nella creazione degli strem I/O>> "+e.getMessage());
                e.printStackTrace();
            }

        }

        public boolean controlloUsername(){
            try{
                this.utente = (Persona) input.readObject();

                if(utenteConsentito(this.utente)) {
                    gui.addConnesso(this.utente);
                    gui.appendConsole("Connesso adesso: " + this.utente.getNickname());

                    //invio la notifica di connessione
                    this.output.writeObject(true);

                    //invio la lista dei giocatori disponibili
                    Messaggio listaGiocatorimsg = new Messaggio(Messaggio.LISTA_GIOCATORI);
                    listaGiocatorimsg.setListaGiocatori(listaGiocatori);
                    this.writeMsg(listaGiocatorimsg);

                    //inizializzo la lista dei giocatori per questo client
                    listaGiocatoriSquadra = new ArrayList<Giocatore>();

                    return true;
                } else{
                    gui.appendConsole("Non consentina la connesione a "+this.utente.getNickname());
                    this.output.writeObject(false);
                    return false;
                }
            } catch (Exception e){
                gui.appendConsole("Eccezione nel controllo username>> " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        private boolean finitiPortieri(){
            int i=0;
            for(Giocatore giocatore:listaGiocatoriSquadra){
                if(giocatore.getRuolo()=='P') i++;
            }
            return(i==3);
        }

        private boolean finitiDifensori(){
            int i=0;
            for(Giocatore giocatore:listaGiocatoriSquadra){
                if(giocatore.getRuolo()=='C') i++;
            }
            return(i==8);
        }

        private boolean finitiCentrocampisti(){
            int i=0;
            for(Giocatore giocatore:listaGiocatoriSquadra){
                if(giocatore.getRuolo()=='C') i++;
            }
            return(i==8);
        }

        private boolean finitiAttaccanti(){
            int i=0;
            for(Giocatore giocatore:listaGiocatoriSquadra){
                if(giocatore.getRuolo()=='A') i++;
            }
            return(i==6);
        }


        private boolean writeMsg(Messaggio msg){
            if(!client.isConnected()) {
                //close();
                return false;
            }
            // write the message to the stream
            try {
                output.writeObject(msg);
                gui.appendConsole("Messaggio inviato a: "+ this.utente.getNickname());
            }
            // if an error occurs, do not abort just inform the user
            catch(IOException ioe) {
                gui.appendConsole("Errore nell'invio del messaggio a: " + this.utente.getNickname());
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
