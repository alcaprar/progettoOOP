package entità;

/**
 * Classe per la gestione dei voti dei giocatori.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Voto {
    private Giocatore giocatore;
    private float voto;
    private int gol;
    private int golSubito;
    private int rigParato;
    private int rigSbagliato;
    private int rigSegnato;
    private int autogol;
    private int ammonizione;
    private int espulsione;
    private int assist;
    private int assistFermo;
    private float magicVoto;

    /**
     * Costruttore di default.
     * @param gioc giocatori associato
     */
    public Voto(Giocatore gioc){
        this.giocatore=gioc;
    }

    /**
     * Costruttore utilizzato nella selezione dal database dei voti dei giocatori schierati nell'incontro.
     * @param giocatore giocatore associato
     * @param gol gol segnati
     * @param voto voto ottenuto
     * @param golSubito gol subiti
     * @param rigParato rigori parati
     * @param rigSbagliato rigori sbagliati
     * @param rigSegnato rigori segnati
     * @param autogol autogol segnati
     * @param ammonizione ammonizioni
     * @param espulsione espulsioni
     * @param assist assist effettuati
     * @param assistFermo assist da fermo effettuati
     */
    public Voto(Giocatore giocatore, int gol, float voto, int golSubito, int rigParato, int rigSbagliato, int rigSegnato, int autogol, int ammonizione, int espulsione, int assist, int assistFermo) {
        this.giocatore = giocatore;
        this.voto = voto;
        this.gol = gol;
        this.golSubito = golSubito;
        this.rigParato = rigParato;
        this.rigSbagliato = rigSbagliato;
        this.rigSegnato = rigSegnato;
        this.autogol = autogol;
        this.ammonizione = ammonizione;
        this.espulsione = espulsione;
        this.assist = assist;
        this.assistFermo = assistFermo;
        this.magicVoto = calcolaMagicVoto(gol, voto, golSubito, rigParato, rigSbagliato, rigSegnato, autogol, ammonizione, espulsione, assist, assistFermo);
    }

    /**
     * Calcola il fantavoto ottenuto in base a voto, bonus e malus.
     * @param gol gol segnati
     * @param voto voto ottenuto
     * @param golSubito gol subiti
     * @param rigParato rigori parati
     * @param rigSbagliato rigori sbagliati
     * @param rigSegnato rigori segnati
     * @param autogol autogol segnati
     * @param ammonizione ammonizione
     * @param espulsione espulsione
     * @param assist assist effettuati
     * @param assistFermo assist da fermo effettuati
     * @return il fantavoto calcolato
     */
    private float calcolaMagicVoto(int gol, float voto, int golSubito, int rigParato, int rigSbagliato, int rigSegnato, int autogol, int ammonizione, int espulsione, int assist, int assistFermo) {
        if (voto==0) return 0; //serve a evitare che giocatori che non prendono il voto, ma qualche malus abbiano magic voto negativo
        else return voto+3*gol-golSubito+3*rigParato-3*rigSbagliato+3*rigSegnato-2*autogol-(float)0.5*ammonizione-espulsione+assist+assistFermo;
        //0.5 viene interpretato come double, è necessario un cast a float
    }

    public float getVoto() {
        return voto;
    }

    public void setVoto(float voto) {
        this.voto = voto;
    }

    public int getGol() {
        return gol;
    }

    public void setGol(int gol) {
        this.gol = gol;
    }

    public int getGolSubito() {
        return golSubito;
    }

    public void setGolSubito(int golSubito) {
        this.golSubito = golSubito;
    }

    public int getRigParato() {
        return rigParato;
    }

    public void setRigParato(int rigParato) {
        this.rigParato = rigParato;
    }

    public int getRigSbagliato() {
        return rigSbagliato;
    }

    public void setRigSbagliato(int rigSbagliato) {
        this.rigSbagliato = rigSbagliato;
    }

    public int getRigSegnato() {
        return rigSegnato;
    }

    public void setRigSegnato(int rigSegnato) {
        this.rigSegnato = rigSegnato;
    }

    public int getAutogol() {
        return autogol;
    }

    public void setAutogol(int autogol) {
        this.autogol = autogol;
    }

    public int getAmmonizione() {
        return ammonizione;
    }

    public void setAmmonizione(int ammonizione) {
        this.ammonizione = ammonizione;
    }

    public int getEspulsione() {
        return espulsione;
    }

    public void setEspulsione(int espulsione) {
        this.espulsione = espulsione;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getAssistFermo() {
        return assistFermo;
    }

    public void setAssistFermo(int assistFermo) {
        this.assistFermo = assistFermo;
    }

    public float getMagicVoto() {
        return magicVoto;
    }

    public void setMagicVoto(float magicVoto) {
        this.magicVoto = magicVoto;
    }

    public Giocatore getGiocatore(){
        return this.giocatore;
    }

    public void setGiocatore(Giocatore giocatore){
        this.giocatore = giocatore;
    }
}
