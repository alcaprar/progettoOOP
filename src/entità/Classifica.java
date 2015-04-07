package entità;

/**
 * Classe per la gestione delle classifiche di campionato. Ad ogni squadra è associato un oggetto classifica che
 * contiene i parametri di quella squadra utili alla costruzione della reale classifica di campionato.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Classifica {
    private Squadra squadra;
    private int giocate;
    private int vinte;
    private int perse;
    private int pareggiate;
    private int golFatti;
    private int golSubiti;
    private int diffReti;
    private float punteggio;
    private int punti;

    /**
     * Costruttore.
     * @param squadra squadra a cui è associato quest'oggetto
     * @param giocate numero di incontri disputati
     * @param vinte numero di partite vinte
     * @param perse numero di partite perse
     * @param pareggiate numero di partite pareggiate
     * @param golFatti gol fatti dalla squadra
     * @param golSubiti gol subiti dalla squadra
     * @param diffReti differenza reti
     * @param punteggio somma dei punteggi ottenuti dalla squadra nelle partite di campionato disputate
     * @param punti punti della squadra in classifica
     */
    public Classifica(Squadra squadra, int giocate, int vinte, int perse, int pareggiate, int golFatti, int golSubiti, int diffReti, float punteggio, int punti) {
        this.squadra = squadra;
        this.giocate = giocate;
        this.vinte = vinte;
        this.perse = perse;
        this.pareggiate = pareggiate;
        this.golFatti = golFatti;
        this.golSubiti = golSubiti;
        this.diffReti = diffReti;
        this.punteggio = punteggio;
        this.punti = punti;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public int getGiocate() {
        return giocate;
    }

    public void setGiocate(int giocate) {
        this.giocate = giocate;
    }

    public int getVinte() {
        return vinte;
    }

    public void setVinte(int vinte) {
        this.vinte = vinte;
    }

    public int getPerse() {
        return perse;
    }

    public void setPerse(int perse) {
        this.perse = perse;
    }

    public int getPareggiate() {
        return pareggiate;
    }

    public void setPareggiate(int pareggiate) {
        this.pareggiate = pareggiate;
    }

    public int getGolFatti() {
        return golFatti;
    }

    public void setGolFatti(int golFatti) {
        this.golFatti = golFatti;
    }

    public int getGolSubiti() {
        return golSubiti;
    }

    public void setGolSubiti(int golSubiti) {
        this.golSubiti = golSubiti;
    }

    public int getDiffReti() {
        return diffReti;
    }

    public void setDiffReti(int diffReti) {
        this.diffReti = diffReti;
    }

    public float getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(float punteggio) {
        this.punteggio = punteggio;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }
}
