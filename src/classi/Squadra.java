package classi;

import java.util.ArrayList;

/**
 * Classe per la gestione delle squadre.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Squadra {
    private int ID;
    private String nome;
    private Persona proprietario;
    private ArrayList<Giocatore> giocatori;
    private Campionato campionato;
    private int soldiDisponibili;
    //formazione per il prossimo incontro
    private Formazione formazione;
    //se è stata inserita la formazione per il prossimo incontro
    private boolean formazioneInserita;

    /**
     * Costruttore utilizzato nella creazione del campionato
     * @param proprietario utente proprietario della squadra
     */
    public Squadra(Persona proprietario){
        this.proprietario = proprietario;
    }

    /**
     * Costruttore utilizzato per la selezione della classifica nel database e la creazione del calendario.
     * @param ID id squadra
     * @param nome nome della squadra
     */
    public Squadra(int ID, String nome){
        this.ID = ID;
        this.nome = nome;
    }

    /**
     * Costruttore utilizzato nella selezione delle squadre dal database.
     * @param ID id squadra
     * @param nome nome della squadra
     * @param proprietario utente proprietario della squadra
     */
    public Squadra(int ID,  String nome,Persona proprietario){
        this.ID = ID;
        this.proprietario = proprietario;
        this.nome = nome;
    }

    /**
     * Costruttore utilizzato nella selezione delle squadre associate ad un utente dal database.
     * @param ID id squadra
     * @param nome nome della squadra
     * @param proprietario utente proprietario della squadra
     * @param campionato campionato a cui partecipa la squadra
     * @param soldiDisponibili saldo dei crediti disponibili della squadra
     */
    public Squadra(int ID, String nome, Persona proprietario , Campionato campionato,int soldiDisponibili) {
        this.ID = ID;
        this.nome = nome;
        this.proprietario = proprietario;
        this.campionato = campionato;
        this.soldiDisponibili = soldiDisponibili;
    }

    /**
     * Metodo per comparare due squadre.
     * @param s squadra da confrontare
     * @return true se le due squadre sono uguali
     */
    public boolean equals(Squadra s){
        if(s.getNome().equals(this.getNome()))return true;
        else if(s.getID()==this.getID()) return true;
        else return false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProprietario(Persona proprietario) {
        this.proprietario = proprietario;
    }

    public void setGiocatori(ArrayList<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }

    public Campionato getCampionato() {
        return campionato;
    }

    public void setCampionato(Campionato campionato) {
        this.campionato = campionato;
    }

    public String getNome() {
        return this.nome;
    }

    public Persona getProprietario() {
        return this.proprietario;
    }


    public ArrayList<Giocatore> getGiocatori(){
        return giocatori;
    }

    public int getSoldiDisponibili() {
        return soldiDisponibili;
    }

    public Formazione getFormazione() {
        return formazione;
    }

    public void setFormazione(Formazione formazione) {
        this.formazione = formazione;
    }

    public void setSoldiDisponibili(int soldiDisponibili) {
        this.soldiDisponibili = soldiDisponibili;
    }

    public boolean isFormazioneInserita() {
        return formazioneInserita;
    }

    public void setFormazioneInserita(boolean formazioneInserita) {
        this.formazioneInserita = formazioneInserita;
    }

    public Partita prossimaPartita(){
        Partita partita=null;
        for(Partita part : this.getCampionato().prossimaGiornata().getPartite()){
            if(part.getFormCasa().getSquadra().equals(this)||part.getFormOspite().getSquadra().equals(this)) partita=part;
        }
        return partita;
    }

    public Object[][] listaGiocatoriRosaToArray(){
        Object[][] listaObject = new Object[this.getGiocatori().size()][6];

        for(int i=0;i<this.getGiocatori().size();i++){
            listaObject[i][0] = this.getGiocatori().get(i).getID();
            listaObject[i][1] = this.getGiocatori().get(i).getCognome();
            listaObject[i][2] = this.getGiocatori().get(i).getRuolo();
            listaObject[i][3] = this.getGiocatori().get(i).getSquadraReale();
            listaObject[i][4] = this.getGiocatori().get(i).getPrezzoBase();
            listaObject[i][5] = this.getGiocatori().get(i).getPrezzoAcquisto();

        }

        return listaObject;
    }
}
