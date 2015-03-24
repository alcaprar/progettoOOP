package classi;

import java.util.ArrayList;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Partita {
    private int ID;
    private int numeroPartita;
    private Squadra casa;
    private Squadra ospite;
    private int golCasa;
    private int golFuori;
    private float puntiCasa;
    private float puntiFuori;

    public Partita(int ID, int numeroPartita,Squadra casa,Squadra ospite,int golCasa,int golFuori,float puntiCasa,float puntiFuori){
        this.ID = ID;
        this.numeroPartita = numeroPartita;
        this.casa = casa;
        this.ospite = ospite;
        this.golCasa = golCasa;
        this.golFuori = golFuori;
        this.puntiCasa = puntiCasa;
        this.puntiFuori = puntiFuori;
    }

    public Partita(Squadra casa, Squadra ospite,int numeroPartita){
        this.casa = casa;
        this.ospite = ospite;
        this.numeroPartita = numeroPartita;
    }

    //calcola il risultato della partita e lo mette negli appositi attributi
    public void calcolaPartita (int primaFascia, int largFascia, int bonusCasa) {
        puntiCasa=casa.getFormazione().calcola()+bonusCasa; //calcola il punteggio della squadra di casa aggiungendo bonus casa al punteggio dei giocatori
        puntiFuori=ospite.getFormazione().calcola(); //calcola il punteggio della squadra ospite
        golCasa=numGol(puntiCasa, primaFascia, largFascia); //calcola i gol che corrispondono al punteggio della squadra di casa
        golFuori=numGol(puntiFuori, primaFascia, largFascia); //calcola i gol che corrispondono al punteggio della squadra ospite
    }

    //questo metodo calcola i gol dato il punteggio
    private int numGol (float p, int primaFascia, int largFascia) {
        int g=0; //inizializza i gol a 0
        //si entra nel ciclo solo se il punteggio è superiore al punteggio del primo gol,
        // ogni iterazione alza la condizione per rientrare della larghezza di una fascia
        for (int i=primaFascia; i>p; i+=largFascia)  g++;
        return g;
    }

    public void aggClassifica(Classifica c) {
        c.aggiornaClassifica(casa, golCasa, golFuori, puntiCasa);
        c.aggiornaClassifica(ospite, golFuori, golCasa, puntiFuori);
    }

    public int getGolCasa() {
        return golCasa;
    }

    public void setGolCasa(int golCasa) {
        this.golCasa = golCasa;
    }

    public int getGolFuori() {
        return golFuori;
    }

    public void setGolFuori(int golFuori) {
        this.golFuori = golFuori;
    }

    public float getPuntiCasa() {
        return puntiCasa;
    }

    public void setPuntiCasa(float puntiCasa) {
        this.puntiCasa = puntiCasa;
    }

    public float getPuntiFuori() {
        return puntiFuori;
    }

    public void setPuntiFuori(float puntiFuori) {
        this.puntiFuori = puntiFuori;
    }

    public int getNumeroPartita() {
        return numeroPartita;
    }

    public void setNumeroPartita(int numeroPartita) {
        this.numeroPartita = numeroPartita;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Squadra getCasa() {
        return casa;
    }

    public void setCasa(Squadra casa) {
        this.casa = casa;
    }

    public Squadra getOspite() {
        return ospite;
    }

    public void setOspite(Squadra ospite) {
        this.ospite = ospite;
    }
}
