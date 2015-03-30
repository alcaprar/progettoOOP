package classi;

import java.util.ArrayList;

/**
 * Created by Christian on 03/03/2015.
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

    public Squadra(Persona proprietario){
        this.proprietario = proprietario;
    }

    public Squadra(int ID, String nome){
        this.ID = ID;
        this.nome = nome;
    }

    public Squadra(int ID,  String nome,Persona proprietario){
        this.ID = ID;
        this.proprietario = proprietario;
        this.nome = nome;
    }

    public Squadra(int ID, String nome, Persona proprietario , Campionato campionato,int soldiDisponibili){
        this.ID = ID;
        this.nome = nome;
        this.proprietario = proprietario;
        this.campionato = campionato;
        this.soldiDisponibili = soldiDisponibili;
    }

    public Squadra(String nome, Persona prop, ArrayList<Giocatore> gioc) {
        this.nome = nome;
        this.proprietario = prop;
        for (Giocatore i : gioc) this.giocatori = gioc;
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

    public Partita prossimaPartita(){
        Partita partita=null;
        for(Partita part : this.getCampionato().prossimaGiornata().getPartite()){
            if(part.getFormCasa().getSquadra().equals(this)||part.getFormOspite().getSquadra().equals(this)) partita=part;
        }
        return partita;
    }

    public boolean isFormazioneInserita() {
        return formazioneInserita;
    }

    public void setFormazioneInserita(boolean formazioneInserita) {
        this.formazioneInserita = formazioneInserita;
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
