package classi;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Partita {
    private int IDcasa;
    private int IDospite;
    private Formazione formCasa;
    private Formazione formOspite;
    private int golCasa;
    private int golFuori;
    private float puntiCasa;
    private float puntiFuori;

    public Partita(int casa, int ospite){
        this.IDcasa = casa;
        this.IDospite = ospite;
    }

    public Partita(Formazione formCasa, Formazione formOspite) {
        this.formCasa = formCasa;
        this.formOspite = formOspite;
        this.golCasa = 0;
        this.golFuori = 0;
        this.puntiCasa = 0;
        this.puntiFuori = 0;
    }

    //calcola il risultato della partita e lo mette negli appositi attributi
    public void calcolaPartita (Campionato c) {
        puntiCasa=formCasa.calcola()+c.getBonusCasa(); //calcola il punteggio della squadra di casa aggiungendo bonus casa al punteggio dei giocatori
        puntiFuori=formOspite.calcola(); //calcola il punteggio della squadra ospite
        golCasa=numGol(puntiCasa, c); //calcola i gol che corrispondono al punteggio della squadra di casa
        golFuori=numGol(puntiFuori, c); //calcola i gol che corrispondono al punteggio della squadra ospite
    }

    //questo metodo calcola i gol dato il punteggio
    private int numGol (float p, Campionato c) {
        int g=0; //inizializza i gol a 0
        //si entra nel ciclo solo se il punteggio è superiore al punteggio del primo gol, ogni iterazione alza la condizione per rientrare della larghezza di una fascia
        for (int i=c.getPrimaFascia(); i>p; i+=c.getLargFascia())  g++;
        return g;
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

    public int getIDcasa() {
        return IDcasa;
    }

    public void setIDcasa(int IDcasa) {
        this.IDcasa = IDcasa;
    }

    public int getIDospite() {
        return IDospite;
    }

    public void setIDospite(int IDospite) {
        this.IDospite = IDospite;
    }
}
