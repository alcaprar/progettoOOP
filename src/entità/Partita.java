package entità;

/**
 * Classe per la gestione delle partite.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
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

    /**
     * Costruttore utilizzato nella selezione delle giornate dal database.
     * @param ID id partita
     * @param numeroPartita numero della partita nella giornata
     * @param casa squadra ospitante
     * @param ospite squadra ospite
     * @param golCasa gol segnati dalla squadra ospitante
     * @param golFuori gol segnati dalla squadra ospite
     * @param puntiCasa punteggio ottenuto dalla squadra ospitante
     * @param puntiFuori punteggio ottenuto dalla squadra ospite
     */
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

    /**
     * Costruttore utilizzato nel metodo per la creazione del campionato.
     * @param casa squadra ospitante
     * @param ospite squadra ospite
     * @param numeroPartita numero della partita nella giornata
     */
    public Partita(Squadra casa, Squadra ospite,int numeroPartita){
        this.formCasa = new Formazione(casa);
        this.formOspite = new Formazione(ospite);
        this.numeroPartita = numeroPartita;
    }

    /**
     * Metodo per il calcolo dei punteggi della partita.
     * @param primaFascia valore della prima fascia di punteggio
     * @param largFascia larghezza delle fasce di punteggio
     * @param bonusCasa bonus per la squadra di casa
     */
    public void calcolaPartita(int primaFascia, int largFascia, int bonusCasa){
        puntiCasa = formCasa.calcola()+bonusCasa;
        puntiFuori = formOspite.calcola();
        golCasa = numGol(puntiCasa, primaFascia, largFascia);
        golFuori = numGol(puntiFuori, primaFascia, largFascia);
    }

    /**
     * Metodo per la conversione del punteggio nel numero di gol segnati dalla squadra.
     * @param p punteggio ottenuto
     * @param primaFascia valore della prima fascia di punteggio
     * @param largFascia larghezza delle fasce di punteggio
     * @return numero di gol segnati dalla squadra
     */
    private int numGol(float p, int primaFascia, int largFascia){
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
