package classi;

import java.util.ArrayList;

/**
 * Created by alessandro on 14/03/15.
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

    public ArrayList<Giornata> getCalendario() {
        return calendario;
    }

    public void setCalendario(ArrayList<Giornata> calendario) {
        this.calendario = calendario;
    }

    public ArrayList<Squadra> getListaSquadrePartecipanti() {
        return listaSquadrePartecipanti;
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

    public Campionato(String nome){
        this.nome = nome;
    }

    public ArrayList<Classifica> getClassifica() {
        return classifica;
    }

    public void setClassifica(ArrayList<Classifica> classifica) {
        this.classifica = classifica;
    }

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

    public Campionato(String nome, int numerop, boolean asta, int inizio, int fine, int crediti, int orario, int primaf, int fasce, int bonusc,Persona presidente ){
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

    /*public ArrayList<Persona> getPartecipanti() {
        return listaPartecipanti;
    }

    public void setPartecipanti(ArrayList<Persona> partecipanti) {
        listaPartecipanti = partecipanti;
    }*/

    public Object[] squadreToArray(){
        System.out.println(this.listaSquadrePartecipanti.size());
        Object[] arrayObject = new Object[this.listaSquadrePartecipanti.size()];
        for(int i=0;i<this.listaSquadrePartecipanti.size();i++){
            arrayObject[i] = this.listaSquadrePartecipanti.get(i).getID()+" - "+this.listaSquadrePartecipanti.get(i).getNome()+" - " + listaSquadrePartecipanti.get(i).getProprietario().getNickname();
        }
        return arrayObject;
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

    public Giornata prossimaGiornata(){
        Giornata prossima = null;
        for(Giornata giorn:calendario){
            if(giorn.getNumGiornata()==this.prossimaGiornata) prossima = giorn;
        }
        return prossima;
    }

    public Giornata ultimaGiornata(){
        Giornata ultima = null;
        for(Giornata giorn:calendario){
            if(giorn.getNumGiornata()==(prossimaGiornata-1)) ultima =giorn;
        }
        return ultima;
    }
}
