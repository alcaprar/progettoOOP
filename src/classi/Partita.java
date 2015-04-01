package classi;

import java.util.ArrayList;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Partita {
    private int ID;
    private int numeroPartita;
    private Formazione formCasa;
    private Formazione formOspite;
    private int golCasa;
    private int golFuori;
    private float puntiCasa;
    private float puntiFuori;

    public Partita(int ID, int numeroPartita,Squadra casa,Squadra ospite,int golCasa,int golFuori,float puntiCasa,float puntiFuori){
        this.ID = ID;
        this.numeroPartita = numeroPartita;
        this.formCasa = new Formazione(casa);
        this.formOspite = new Formazione(ospite);
        this.golCasa = golCasa;
        this.golFuori = golFuori;
        this.puntiCasa = puntiCasa;
        this.puntiFuori = puntiFuori;
    }

    public Partita(Squadra casa, Squadra ospite,int numeroPartita){
        this.formCasa = new Formazione(casa);
        this.formOspite = new Formazione(ospite);
        this.numeroPartita = numeroPartita;
    }


    public void calcolaPartitaNew(int primaFascia, int largFascia, int bonusCasa){
        puntiCasa = formCasa.calcolaNew()+bonusCasa;
        puntiFuori = formOspite.calcolaNew();
        System.out.println("Casa: "+puntiCasa+" Ospite: "+puntiFuori);
        golCasa = numGolNew(puntiCasa,primaFascia,largFascia);
        golFuori = numGolNew(puntiFuori,primaFascia,largFascia);
        System.out.println("Casa: "+golCasa+" Ospite: "+golFuori);
    }

    private int numGolNew(float p, int primaFascia, int largFascia){
        int punti = (int)p;
        int g=0;
        while(punti>primaFascia){
            g++;
            punti-=largFascia;
        }
        return g;
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

    public Formazione getFormCasa() {
        return formCasa;
    }

    public void setFormCasa(Formazione formCasa) {
        this.formCasa = formCasa;
    }

    public Formazione getFormOspite() {
        return formOspite;
    }

    public void setFormOspite(Formazione formOspite) {
        this.formOspite = formOspite;
    }
}
