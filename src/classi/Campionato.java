package classi;

import java.util.ArrayList;

/**
 * Classe per la gestione dei dati del fantacampionato
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Campionato {
    private String nome;
    private int numeroPartecipanti;
    private boolean astaLive;
    private int giornataInizio;
    private int giornataFine;
    private int prossimaGiornata;
    private int creditiIniziali;
    private int orarioConsegna;
    private int primaFascia;
    private int largFascia;
    private int bonusCasa;
    private boolean giocatoriDaInserire;
    private ArrayList<Squadra> listaSquadrePartecipanti;
    private Persona presidente;
    private ArrayList<Classifica> classifica;
    private ArrayList<Giornata> calendario;
    private ArrayList<String[]> listaAvvisi;
    private ArrayList<String []> listaMessaggi;

    public Campionato(String nome){
        this.nome = nome;
    }

    /**
     * Costruttore della classe.
     * @param nome nome del campionato
     * @param numerop numero dei partecipanti del campionato
     * @param asta flag per stabilire se le squadre partecipanti sono popolate mediante asta oppure i giocatori vengono inseriti dal presidente di lega
     * @param inizio giornata reale corrispondente alla giornata d'inizio del fantacampionato
     * @param fine giornata reale corrispondente alla giornata di fine del fantacampionato
     * @param crediti entità del budget disponibile per ogni utente per l'acquisto dei giocatori
     * @param orario orario di consegna per l'inserimento della formazione per la giornata seguente
     * @param primaf fascia di partenza per il calcolo dei punteggi
     * @param fasce larghezza di ogni fascia, stabilisce il passaggio da una fascia all'altra
     * @param bonusc bonus per la squadra che gioca in casa
     * @param presidente presidente di lega del campionato
     * @param listaSquadrePartecipanti lista delle squadre partecipanti al campionato
     * @param giocatoriDaInserire true se devono ancora essere inseriti i giocatori nelle squadre
     * @param prossimaGiornata prossima giornata
     */
    public Campionato(String nome, int numerop, boolean asta, int inizio, int fine, int crediti, int orario, int primaf, int fasce, int bonusc,Persona presidente,ArrayList<Squadra> listaSquadrePartecipanti,boolean giocatoriDaInserire, int prossimaGiornata ){
        this.nome = nome;
        this.numeroPartecipanti = numerop;
        this.astaLive = asta;
        this.giornataInizio = inizio;
        this.giornataFine = fine;
        this.creditiIniziali = crediti;
        this.orarioConsegna = orario;
        this.primaFascia = primaf;
        this.largFascia = fasce;
        this.bonusCasa = bonusc;
        this.presidente = presidente;
        this.listaSquadrePartecipanti = listaSquadrePartecipanti;
        this.giocatoriDaInserire = giocatoriDaInserire;
        this.prossimaGiornata = prossimaGiornata;
    }

    public ArrayList<Squadra> getListaSquadrePartecipanti() {
        return listaSquadrePartecipanti;
    }

    public ArrayList<Giornata> getCalendario() {
        return calendario;
    }

    public void setListaSquadrePartecipanti(ArrayList<Squadra> listaSquadrePartecipanti) {
        this.listaSquadrePartecipanti = listaSquadrePartecipanti;

    }

    public Persona getPresidente() {
        return presidente;
    }

    public void setPresidente(Persona presidente) {
        presidente = presidente;
    }

    public ArrayList<Classifica> getClassifica() {
        return classifica;
    }

    public void setClassifica(ArrayList<Classifica> classifica) {
        this.classifica = classifica;
    }

    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        numeroPartecipanti = numeroPartecipanti;
    }

    public boolean isAstaLive() {
        return astaLive;
    }

    public void setAstaLive(boolean astaLive) {
        astaLive = astaLive;
    }

    public int getGiornataInizio() {
        return giornataInizio;
    }

    public void setGiornataInizio(int giornataInizio) {
        giornataInizio = giornataInizio;
    }

    public int getGiornataFine() {
        return giornataFine;
    }

    public void setGiornataFine(int giornataFine) {
        giornataFine = giornataFine;
    }

    public int getCreditiIniziali() {
        return creditiIniziali;
    }

    public void setCreditiIniziali(int creditiIniziali) {
        creditiIniziali = creditiIniziali;
    }

    public int getOrarioConsegna() {
        return orarioConsegna;
    }

    public void setOrarioConsegna(int orarioConsegna) {
        orarioConsegna = orarioConsegna;
    }

    public int getPrimaFascia() {
        return primaFascia;
    }

    public void setPrimaFascia(int primaFascia) {
        primaFascia = primaFascia;
    }

    public int getLargFascia() {
        return largFascia;
    }

    public void setLargFascia(int largFascia) {
        largFascia = largFascia;
    }

    public int getBonusCasa() {
        return bonusCasa;
    }

    public void setBonusCasa(int bonusCasa) {
        bonusCasa = bonusCasa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        nome = nome;
    }

    public ArrayList<String[]> getListaAvvisi() {
        return listaAvvisi;
    }

    public void setListaAvvisi(ArrayList<String[]> listaAvvisi) {
        this.listaAvvisi = listaAvvisi;
    }

    public boolean isGiocatoriDaInserire() {
        return giocatoriDaInserire;
    }

    public void setGiocatoriDaInserire(boolean giocatoriDaInserire) {
        this.giocatoriDaInserire = giocatoriDaInserire;
    }

    public int getProssimaGiornata() {
        return prossimaGiornata;
    }

    public void setProssimaGiornata(int prossimaGiornata) {
        this.prossimaGiornata = prossimaGiornata;
    }

    public void setCalendario(ArrayList<Giornata> calendario) {
        this.calendario = calendario;
    }

    public ArrayList<String[]> getListaMessaggi() {
        return listaMessaggi;
    }

    public void setListaMessaggi(ArrayList<String[]> listaMessaggi) {
        this.listaMessaggi = listaMessaggi;
    }

    /**
     * Imposta la prossima giornata del campionato da disputare una volta completata quella attuale.
     * @return prossima giornata
     */
    public Giornata prossimaGiornata(){
        Giornata prossima = null;
        for(Giornata giorn:calendario){
            if(giorn.getNumGiornata()==this.prossimaGiornata) prossima = giorn;
        }
        return prossima;
    }

    /**
     * Individua l'ultima giornata disputata.
     * @return ultima giornata disputata
     */
    public Giornata ultimaGiornata(){
        Giornata ultima = null;
        for(Giornata giorn:calendario){
            if(giorn.getNumGiornata()==(prossimaGiornata-1)) ultima =giorn;
        }
        return ultima;
    }

    /**
     * Aggiorna la classifica al termine di una giornata di campionato.
     * @param giornata giornata di fantacampionato disputata
     */
    public void aggiornaClassifica(Giornata giornata){
        //scorre l'elenco delle partite
        for(Partita partita : giornata.getPartite()){
            boolean vittoriaCasa=false,  vittoriaOspite=false;
            boolean formNonInserite = false;// formCasaNonInserita=false, formFuoriNonInserita=false;
            if(partita.getPuntiCasa()==0 && partita.getPuntiFuori()==0){
                formNonInserite=true;//controlla se sono state inserite le formazioni
            } else if(partita.getPuntiCasa()==0){
                //formCasaNonInserita=true;//controlla se la formazione di casa è stata inserita
                vittoriaOspite=true;//assegna la vittoria a tavolino alla squadra ospite
            } else if(partita.getPuntiFuori()==0){
                //formFuoriNonInserita=true;//controlla se la formazione ospite è stata inserita
                vittoriaCasa=true;//assegna la vittoria a tavolino alla squadra in casa
            }else  if(partita.getGolCasa()>partita.getGolFuori()){
                vittoriaCasa=true;//assegna la vittoria alla squadra in casa
            } else if( partita.getGolCasa()<partita.getGolFuori()){
                vittoriaOspite=true;//assegna la vittoria alla squadra ospite
            }
            //scorre le righe della classifica del campionato
            for(classi.Classifica rigaClassifica : this.classifica){
                //cerca la riga corrispondente alla squadra in casa
                if(rigaClassifica.getSquadra().equals(partita.getFormCasa().getSquadra())){
                    rigaClassifica.setGiocate(rigaClassifica.getGiocate()+1);
                    if(vittoriaCasa){ //controlla se ha vinto
                        rigaClassifica.setVinte(rigaClassifica.getVinte()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+3);
                    } else if(vittoriaOspite || formNonInserite){//controlla se ha perso (vittoriaOspite) o non ha dato la formazione (formNonInserite)
                        rigaClassifica.setPerse(rigaClassifica.getPerse()+1);
                    } else{//se non ha ne vinto ne perso allora ha pareggiato
                        rigaClassifica.setPareggiate(rigaClassifica.getPareggiate()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+1);
                    }
                    rigaClassifica.setGolFatti(rigaClassifica.getGolFatti()+partita.getGolCasa());
                    rigaClassifica.setGolSubiti(rigaClassifica.getGolSubiti()+partita.getGolFuori());
                    rigaClassifica.setPunteggio(rigaClassifica.getPunteggio()+partita.getPuntiCasa());
                    rigaClassifica.setDiffReti(rigaClassifica.getGolFatti()-rigaClassifica.getGolSubiti());
                } else if(rigaClassifica.getSquadra().equals(partita.getFormOspite().getSquadra())){
                    rigaClassifica.setGiocate(rigaClassifica.getGiocate()+1);
                    if(vittoriaCasa || formNonInserite ){
                        rigaClassifica.setPerse(rigaClassifica.getPerse()+1);
                    } else if(vittoriaOspite){
                        rigaClassifica.setVinte(rigaClassifica.getVinte()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+3);
                    } else{
                        rigaClassifica.setPareggiate(rigaClassifica.getPareggiate()+1);
                        rigaClassifica.setPunti(rigaClassifica.getPunti()+1);
                    }
                    rigaClassifica.setGolFatti(rigaClassifica.getGolFatti()+partita.getGolFuori());
                    rigaClassifica.setGolSubiti(rigaClassifica.getGolSubiti()+partita.getGolCasa());
                    rigaClassifica.setPunteggio(rigaClassifica.getPunteggio()+partita.getPuntiFuori());
                    rigaClassifica.setDiffReti(rigaClassifica.getGolFatti()-rigaClassifica.getGolSubiti());
                }
            }
        }

    }

    /**
     * Metodo utilizzato per creare un array di oggetti a partire dalla lista delle squadre partecipanti ad un campionato.
     * Viene usato per popolare i selettori delle squadre nei vari elementi dell'interfaccia del programma.
     * Negli oggetti dell'array sono presenti i nomi delle squadre ed i rispettivi proprietari.
     * @return array di oggetti
     */
    public Object[] squadreToArray(){
        Object[] arrayObject = new Object[this.listaSquadrePartecipanti.size()];
        for(int i=0;i<this.listaSquadrePartecipanti.size();i++){
            arrayObject[i] =this.listaSquadrePartecipanti.get(i).getNome()+" - " + listaSquadrePartecipanti.get(i).getProprietario().getNickname();
        }
        return arrayObject;
    }

    /**
     * Serve per la creazione della classifica nella home.
     * Ritorna un array di object prendendo i valori dall'oggetto classifica.
     * Nell'array ci sono solo i nomi delle squadre e i punti.
     * @see #classifica
     * @see interfacce.Applicazione.Home
     * @return classifica come array di objet
     */
    public Object[][] classificaToArrayPiccola(){
        Object[][] listaObject = new Object[this.classifica.size()][2];

        for(int i=0;i<this.classifica.size();i++){
            listaObject[i][0] = this.classifica.get(i).getSquadra().getNome();
            listaObject[i][1] = this.classifica.get(i).getPunti();
        }
        return listaObject;
    }

    /**
     * Serve per la creazione della classifica dettagliata.
     * Ritorna un array di object prendendo i valori dall'oggetto classifica.
     * Nell'array ci sono tutti i valori presenti in classifica.
     * @see #classifica
     * @see interfacce.Applicazione.Classifica
     * @return array di object
     */
    public Object[][] classificaToArray(){
        Object[][] listaObject = new Object[this.classifica.size()][10];

        for(int i=0;i<this.classifica.size();i++){
            listaObject[i][0] = this.classifica.get(i).getSquadra().getNome();
            listaObject[i][1] = this.classifica.get(i).getGiocate();
            listaObject[i][2] = this.classifica.get(i).getVinte();
            listaObject[i][3] = this.classifica.get(i).getPareggiate();
            listaObject[i][4] = this.classifica.get(i).getPerse();
            listaObject[i][5] = this.classifica.get(i).getDiffReti();
            listaObject[i][6] = this.classifica.get(i).getGolFatti();
            listaObject[i][7] = this.classifica.get(i).getGolSubiti();
            listaObject[i][8] = this.classifica.get(i).getPunteggio();
            listaObject[i][9] = this.classifica.get(i).getPunti();
        }
        return listaObject;
    }
}
