package classi;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Partita {
    private Formazione formCasa;
    private Formazione formOspite;
    private int golCasa;
    private int golFuori;
    private float puntiCasa;
    private float puntiFuori;

    public Partita(Formazione formCasa, Formazione formOspite) {
        this.formCasa = formCasa;
        this.formOspite = formOspite;
        this.golCasa = 0;
        this.golFuori = 0;
        this.puntiCasa = 0;
        this.puntiFuori = 0;
    }

    public void calcolaPartita () {
        puntiCasa=formCasa.calcola();
        puntiFuori=formOspite.calcola();
        golCasa=numGol(puntiCasa);
        golFuori=numGol(puntiFuori);
    }

    //questo metodo calcola i gol dato il punteggio
    private int numGol (float p) {
        int g=0;
        for (int i=66; i>p; i+=4)  g++; //66 è l'inizio della prima fascia, 4 è la larghezza della fascia
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
}
